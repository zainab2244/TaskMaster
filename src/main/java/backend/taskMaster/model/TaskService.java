package backend.taskMaster.model;

import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TaskService {

    private static final Logger LOGGER = Logger.getLogger(TaskService.class.getName());
    private static final String TASKS_FILE = "tasks.txt";
    private List<Task> taskList;
    private Scheduling scheduling;

    public TaskService() {
        taskList = new ArrayList<>();
        scheduling = new Scheduling(new Settings(8, LocalTime.of(9, 0), LocalTime.of(17, 0), 15));
        loadTasksFromFile();
    }

    public void addTask(Task task) {
        taskList.add(task);
        scheduling.addTask(task);
        LOGGER.info("Task added: " + task.toCSV());
        saveTasksToFile();
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public int getTaskCount() {
        return taskList.size();
    }

    private void loadTasksFromFile() {
        taskList.clear();
        File file = new File(TASKS_FILE);
        if (!file.exists()) {
            LOGGER.info("Tasks file not found, creating a new one.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.severe("Error creating tasks file: " + e.getMessage());
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Task.fromCSV(line);
                    taskList.add(task);
                    scheduling.addTask(task);
                    LOGGER.info("Task loaded: " + task.toCSV());
                } catch (IllegalArgumentException e) {
                    LOGGER.severe("Error parsing task from CSV: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error reading tasks file: " + e.getMessage());
        }
    }

    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FILE))) {
            for (Task task : taskList) {
                writer.write(task.toCSV());
                writer.newLine();
                LOGGER.info("Task saved: " + task.toCSV());
            }
        } catch (IOException e) {
            LOGGER.severe("Error writing tasks to file: " + e.getMessage());
        }
    }

    public String getScheduleView() {
        return scheduling.getCalendarView();
    }
}
