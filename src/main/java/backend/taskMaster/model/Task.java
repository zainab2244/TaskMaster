package backend.taskMaster.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Task {

    // Attributes
    private String name;
    private String description;
    private String category;
    private String location;
    private String priority;
    private LocalDate dueDate;
    private double estimatedTime;
    private boolean completed;
    private double weight;
    private String status;
    private double percentage;

    // Constants
    static final int WORK_HOURS_PER_DAY = 8;
    static final double BETA = 0.1;

    public Task(String name, String description, String category, String location,
                LocalDate dueDate, double estimatedTime, double percentage) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.location = location;
        this.dueDate = dueDate;
        this.estimatedTime = estimatedTime;
        this.completed = false;
        this.weight = calculateWeight();
        this.status = "TO DO";
        this.percentage = percentage;
    }

    // no-arg constructor
    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateStatus() {
        if (completed) {
            status = "DONE";
        } else {
            status = "IN PROGRESS";
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getpercentage() {
        return percentage;
    }

    public void setpercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getPriority() {
        return priority;
    }

    public String canBeScheduled(List<Task> scheduledTasks, LocalDate dueDate, double estimatedTime,
                                 Settings settings) {
        StringBuilder message = new StringBuilder();
        LocalDate currentDate = LocalDate.now();

        // Check if due date is in the past
        long daysLeft = ChronoUnit.DAYS.between(currentDate, dueDate);
        if (daysLeft < 0) {
            return "The due date is in the past, please choose a future due date.";
        }

        // Retrieve work hours threshold and break duration from settings
        int workHoursPerDay = settings.getWorkHoursThreshold(); // Total work hours per day
        int breakDuration = settings.getBreakDuration(); // Break duration in minutes

        // Calculate available work hours per day after accounting for breaks
        double breakDurationInHours = breakDuration / 60.0;
        double availableWorkHoursPerDay = workHoursPerDay - breakDurationInHours;

        // Calculate total available hours
        double totalAvailableHours = daysLeft * availableWorkHoursPerDay;

        // Calculate total scheduled hours
        double totalScheduledHours = 0;
        for (Task scheduledTask : scheduledTasks) {
            // Filter tasks that are on or before due date
            if (!scheduledTask.getDueDate().isBefore(currentDate)) {
                totalScheduledHours += scheduledTask.getEstimatedTime();
            }
        }

        // Check if adding the current task exceeds available hours
        if (totalScheduledHours + estimatedTime > totalAvailableHours) {
            message.append("Not enough available hours to accommodate this task.\n");
            return message.toString();
        }

        return "Task can be scheduled.";
    }

    public String getDayOfWeek() {
        DayOfWeek dayOfWeek = dueDate.getDayOfWeek();
        return dayOfWeek.toString();
    }

    // YYYY-MM-DD
    public String getDate() {
        return dueDate.toString();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double calculateWeight() {
        LocalDate currentDate = LocalDate.now();
        long daysLeft = ChronoUnit.DAYS.between(currentDate, dueDate);
        daysLeft = Math.max(daysLeft, 1); // Avoid zero division

        double weight = (1.0 / daysLeft) + (estimatedTime * BETA) + ((percentage + 100) / 100); // Formula

        return round(weight, 2);
    }

    // helper
    private double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isTaskScheduled(Task task, List<Task> scheduledTasks) {
        return scheduledTasks.contains(task);
    }

    public void logHours(double hoursLogged) {
        if (hoursLogged < 0) {
            System.out.println("Cannot log negative hours.");
            return;
        }

        estimatedTime -= hoursLogged;
        if (estimatedTime < 0) {
            estimatedTime = 0; // Prevent negative value
            System.out.println("Task completed and removed from schedule.");
        }

        System.out.println("Logged " + hoursLogged + " hours. Remaining estimated time: " + estimatedTime + " hours.");
    }

    @Override
    public String toString() {
        return "\nname = " + name +
                "\ndescription = " + description +
                "\ncategory = " + category +
                "\nlocation = " + location +
                "\npriority = " + priority +
                "\npercentage = " + percentage +
                "\nweight = " + weight +
                "\nstatus = " + status +
                "\ndueDate = " + dueDate +
                "\nestimatedTime = " + estimatedTime +
                "\ncompleted = " + completed;
    }

    public String toCSV() {
        return name + "," +
               description + "," +
               category + "," +
               location + "," +
               dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "," +
               estimatedTime + "," +
               completed + "," +  // Include the completed field
               weight + "," +  // Include the weight field
               status + "," +  // Include the status field
               percentage;
    }

    public static Task fromCSV(String csv) {
        String[] parts = csv.split(",");
        Task task = new Task();
        task.setName(parts[0]);
        task.setDescription(parts[1]);
        task.setCategory(parts[2]);
        task.setLocation(parts[3]);
        task.setDueDate(LocalDate.parse(parts[4], DateTimeFormatter.ISO_LOCAL_DATE));
        task.setEstimatedTime(Double.parseDouble(parts[5]));
        task.setCompleted(Boolean.parseBoolean(parts[6]));  // Parse the completed field
        task.setWeight(Double.parseDouble(parts[7]));  // Parse the weight field
        task.setStatus(parts[8]);  // Parse the status field
        task.setpercentage(Double.parseDouble(parts[9]));  // Parse the percentage field
        return task;
    }
}