package backend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import backend.taskMaster.model.Event;
import backend.taskMaster.model.EventService;
import backend.taskMaster.model.Scheduling;
import backend.taskMaster.model.Settings;
import backend.taskMaster.model.Task;
import backend.taskMaster.model.User;
import backend.taskMaster.model.UserService;

public class Main {
    private static UserService userService = new UserService();
    private static EventService eventService = new EventService();
    private static Settings settings;

    public static void main(String[] args) {
        settings = new Settings(8, LocalTime.of(9, 0), LocalTime.of(17, 0), 15);
        Scheduling scheduling = new Scheduling(settings);
        List<Task> currentlyScheduledTasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. View All Tasks");
            System.out.println("4. View Task List View");
            System.out.println("5. View Task Week View");
            System.out.println("6. View Task Calendar View");
            System.out.println("7. Create Account");
            System.out.println("8. Login");
            System.out.println("9. Create Event");
            System.out.println("10. Update Event");
            System.out.println("11. List Users");
            System.out.println("12. List Events");
            System.out.println("13. Update Settings");
            System.out.println("14. View Settings");
            System.out.println("15. View Task Priority List");
            System.out.println("16. Log Hours for Task");
            System.out.println("17. View Today's Schedule");
            System.out.println("18. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter task category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter task location: ");
                    String location = scanner.nextLine();
                    System.out.print("Enter task due date (yyyy-MM-dd): ");
                    String dueDateString = scanner.nextLine();
                    LocalDate dueDate = LocalDate.parse(dueDateString, formatter);
                    System.out.print("Enter estimated time (hours): ");
                    double estimatedTime = scanner.nextDouble();
                    System.out.print("Enter percentage weighting (0-100): ");
                    double percentage = scanner.nextDouble();
                    scanner.nextLine(); // Clear if needed

                    Task newTask = new Task(name, description, category, location, dueDate, estimatedTime, percentage);
                    scheduling.addTask(newTask);
                    break;

                case 2:
                    System.out.print("Enter task name to remove: ");
                    String taskNameToRemove = scanner.nextLine();
                    Task taskToRemove = null;
                    for (Task task : scheduling.getTasks()) {
                        if (task.getName().equalsIgnoreCase(taskNameToRemove)) {
                            taskToRemove = task;
                            break;
                        }
                    }
                    if (taskToRemove != null) {
                        scheduling.removeTask(taskToRemove);
                        currentlyScheduledTasks.remove(taskToRemove);
                        System.out.println("Task removed successfully.");
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;

                case 3:
                    System.out.println("All Tasks:");
                    for (Task task : scheduling.getTasks()) {
                        System.out.println(task.toString());
                    }
                    break;

                case 4:
                    scheduling.updateViews();
                    System.out.println(scheduling.getListView());
                    break;

                case 5:
                    scheduling.updateViews();
                    System.out.println(scheduling.getWeekView());
                    break;

                case 6:
                    scheduling.updateViews();
                    System.out.println(scheduling.getCalendarView());
                    break;

                case 7:
                    userService.createUser(scanner);
                    break;

                case 8:
                    userService.loginUser(scanner);
                    break;

                case 9:
                    Event newEvent = eventService.createEvent(scanner, scheduling);
                    if (newEvent != null) {
                        scheduling.addEvent(newEvent);
                        System.out.println("Event added to schedule.");
                    }
                    break;

                case 10:
                    System.out.println("Enter the name of the event you want to update: ");
                    String eventNameToUpdate = scanner.nextLine();

                    Event eventToUpdate = null;
                    for (Event event : eventService.getEvents()) {
                        if (event.getNameOfEvent().equalsIgnoreCase(eventNameToUpdate)) {
                            eventToUpdate = event;
                            break;
                        }
                    }

                    if (eventToUpdate != null) {
                        System.out.println("Event found. Please re-enter all details.");

                        Event updatedEvent = eventService.createEvent(scanner, scheduling);

                        eventService.getEvents().remove(eventToUpdate);
                        eventService.getEvents().add(updatedEvent);
                        System.out.println("Old event removed and new event created.");
                    } else {
                        System.out.println("Event with the name " + eventNameToUpdate + " not found.");
                    }
                    break;

                case 11:
                    listUsers();
                    break;

                case 12:
                    System.out.println("All Events:");
                    for (Event event : eventService.getEvents()) {
                        System.out.println(event.toString());
                    }
                    break;
                case 13:
                    updateSettings(scanner);
                    break;

                case 14:
                    if (settings != null) {
                        System.out.println(settings);
                    } else {
                        System.out.println(
                                "No settings available. Please ensure that you have created an account first.");
                    }
                    break;

                case 15:
                    System.out.println(scheduling.getTaskPriorityList());
                    break;

                case 16:
                    System.out.print("Enter the name of the task you want to log hours for: ");
                    String taskNameToLog = scanner.nextLine();
                    Task taskToLog = null;
                    for (Task task : scheduling.getTasks()) {
                        if (task.getName().equalsIgnoreCase(taskNameToLog)) {
                            taskToLog = task;
                            break;
                        }
                    }

                    if (taskToLog != null) {
                        System.out.print("Enter the number of hours to log: ");
                        double hoursToLog = scanner.nextDouble();
                        scanner.nextLine(); // clear!

                        taskToLog.logHours(hoursToLog);
                        taskToLog.calculateWeight(); // recalculate
                        if (taskToLog.getEstimatedTime() <= 0) {
                            scheduling.removeTask(taskToLog);
                            System.out.println("Task has been removed from the schedule.");
                        }
                    } else {
                        System.out.println("Task with the name " + taskNameToLog + " not found.");
                    }
                    break;

                case 17:
                    System.out.println(scheduling.todaySchedule(settings.getWorkHoursThreshold(), 2));
                    break;

                case 18:
                    System.out.println("Exiting TaskMaster. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            // Delay for 1 second!!! (2?)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }

    private static void listUsers() {
        List<User> users = userService.getUsers();
        System.out.println("List of Users:");
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void updateSettings(Scanner scanner) {
        System.out.print("Enter username to update settings: ");
        String username = scanner.nextLine();

        User currentUser = null;
        List<User> users = userService.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            System.out.println("User not found.");
            return;
        }

        userService.updateSettings(scanner, users.indexOf(currentUser));
    }
}
