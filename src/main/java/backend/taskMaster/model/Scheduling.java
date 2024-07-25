package backend.taskMaster.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduling {
    // Attributes
    private List<Task> tasks;
    private List<Event> events;
    private Settings settings;
    private double timePerTask;
    private String listView;
    private String weekView;
    private String calendarView;
    private double productivityThreshold;
    private double hoursLeft;
    // (use settings.workhours)
    static final int WORK_HOURS_PER_DAY = 8;

    public Scheduling(Settings settings) {
        this.tasks = new ArrayList<>();
        this.events = new ArrayList<>();
        this.settings = settings;
        this.listView = "";
        this.weekView = "";
        this.calendarView = "";
        this.productivityThreshold = 2.0;
        this.hoursLeft = 0.0;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Event> getEvents() {
        return events;
    }

    public double getHoursLeft() {
        return hoursLeft;
    }

    public void setHoursLeft(double hoursLeft) {
        this.hoursLeft = hoursLeft;
    }

    public void addTask(Task task) {
        String conflictMessage = task.canBeScheduled(tasks, task.getDueDate(), task.getEstimatedTime(),
                settings);

        if (conflictMessage.equals("Task can be scheduled.")) {
            tasks.add(task);
            System.out.println("Task added successfully.");
        } else {
            System.out.println("Task cannot be scheduled due to conflicts: \n" + conflictMessage);
        }
        updateViews();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        updateViews();
    }

    public void addEvent(Event event) {
        if (event.canBeScheduled(events)) {
            events.add(event);
        } else {
            System.out.println("Event cannot be scheduled due to conflicts.");
        }
        updateViews();
    }

    public void removeEvent(Event event) {
        events.remove(event);
        updateViews();
    }

    public void notifyBreak() {
        System.out.println("Time to take a break!");
    }

    public String getListView() {
        return listView;
    }

    public String getWeekView() {
        return weekView;
    }

    public String getCalendarView() {
        return calendarView;
    }

    public void updateViews() {
        updateListView();
        updateWeekView();
        updateCalendarView();
    }

    private void updateListView() {
        StringBuilder temp = new StringBuilder("List View:\n");
        for (Task task : tasks) {
            temp.append(task.toString()).append("\n");
        }
        this.listView = temp.toString();
    }

    private void updateWeekView() {
        StringBuilder temp = new StringBuilder("Week View:\n");
        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

        for (String day : days) {
            temp.append(day).append(":\n");
            for (Task task : tasks) {
                if (task.getDayOfWeek().equalsIgnoreCase(day)) {
                    temp.append(task.toString()).append("\n");
                }
            }
        }

        this.weekView = temp.toString();
    }

    private void updateCalendarView() {
        StringBuilder calendarView = new StringBuilder("Calendar View:\n");
        // yyyy-mm-dd
        for (Task task : tasks) {
            calendarView.append(task.getDate()).append(": ").append(task.toString()).append("\n");
        }
        this.calendarView = calendarView.toString();
    }

    void notifyUser(Task task) {
        System.out.println("Task " + task.getName() + " cannot be scheduled due to conflicts.");
    }

    boolean isTaskScheduled(Task task, List<Task> scheduledTasks) {
        return scheduledTasks.contains(task);
    }

    public void sortTasksByPriority() {
        tasks.sort(Comparator.comparingDouble(Task::calculateWeight).reversed());
    }

    // by weighting
    public String getTaskPriorityList() {
        sortTasksByPriority();
        StringBuilder sb = new StringBuilder("Task Priority List:\n");
        for (Task task : tasks) {
            sb.append(task.getName()).append(" - Weight: ").append(task.calculateWeight()).append("\n");
        }
        return sb.toString();
    }

    public String todaySchedule(int WORK_HOURS_PER_DAY, double productivityThreshold) {
        StringBuilder sb = new StringBuilder("Here is today's work schedule:\n");
        double remainingHours = WORK_HOURS_PER_DAY;
        double breakDuration = settings.getBreakDuration();
        double timeWorkedToday = 0; // Total time worked so far

        // go through events first
        List<Event> todaysEvents = events.stream()
                .filter(event -> event.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());

        if (todaysEvents.isEmpty()) {
            sb.append("No events are scheduled for today.\n");
        } else {
            sb.append("Scheduled events for today:\n");
            for (Event event : todaysEvents) {
                sb.append("- ").append(event.getNameOfEvent()).append(" at ").append(event.getStartTime())
                        .append(" to ").append(event.getEndTime())
                        .append(" (").append(event.getCategory()).append(")\n");
            }
        }

        // Sort tasks based on weight using getter
        List<Task> sortedTasks = tasks.stream()
                .sorted((t1, t2) -> Double.compare(t2.getWeight(), t1.getWeight()))
                .collect(Collectors.toList());

        if (sortedTasks.isEmpty()) {
            sb.append("No tasks are scheduled for today.\n");
        } else {
            int taskIndex = 0;
            while (remainingHours > 0 && taskIndex < sortedTasks.size()) {
                Task currentTask = sortedTasks.get(taskIndex);
                double timeRemainingForTask = currentTask.getEstimatedTime(); // temp variable to track remaining time

                while (timeRemainingForTask > 0 && remainingHours > 0) {
                    // Determine how much time to work on specific task
                    double timeToWork = Math.min(productivityThreshold - timeWorkedToday, remainingHours);
                    timeToWork = Math.min(timeToWork, timeRemainingForTask); // Don't exceed estimated time

                    sb.append("- Work on task '").append(currentTask.getName()).append("' for ").append(timeToWork)
                            .append(" hours.\n");

                    // Update if needed
                    remainingHours -= timeToWork;
                    timeWorkedToday += timeToWork;
                    timeRemainingForTask -= timeToWork;

                    // Check if user reached productivity threshold
                    if (timeWorkedToday >= productivityThreshold) {
                        sb.append("- Take a 15-minute break.\n"); // can use notifyBreak
                        remainingHours -= breakDuration; // Update break time
                        timeWorkedToday = 0; // Reset
                    }
                }

                // Move to the next task
                if (timeRemainingForTask <= 0) {
                    sb.append("- Task '").append(currentTask.getName()).append("' is completed.\n");
                    taskIndex++;
                    if (timeWorkedToday >= productivityThreshold && remainingHours > 0) {
                        sb.append("- Take a 15-minute break.\n");
                        remainingHours -= breakDuration;
                        timeWorkedToday = 0;
                    }
                }
            }
            // Only if all tasks are completed
            if (remainingHours > 0) {
                sb.append("You have ").append(remainingHours).append(" free hours left today.\n");
            } else if (taskIndex == sortedTasks.size()) {
                sb.append("All tasks are complete!\n");
            }
        }

        return sb.toString(); // sb to string?
    }

    @Override
    public String toString() {
        return "Scheduling:\n" +
                "tasks = " + tasks +
                "\ntimePerTask = " + timePerTask +
                "\nproductivityThreshold = " + productivityThreshold +
                "\nhoursLeft = " + hoursLeft;
    }
}