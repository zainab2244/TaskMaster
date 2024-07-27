package backend.taskMaster.model;

import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TaskService {

    private static final Logger LOGGER = Logger.getLogger(TaskService.class.getName());
    private static final String TASKS_FILE = "tasks.txt";
    private List<Task> taskList;

    public TaskService() {
        taskList = new ArrayList<>();
        loadTasksFromFile();
    }

    public void addTask(Task task) {
        taskList.add(task);
        LOGGER.info("Task added: " + task.toCSV()); // Logging added task
        saveTasksToFile();
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public int getTaskCount() {
        return taskList.size();
    }

    private void loadTasksFromFile() {
        taskList.clear(); // Clear existing tasks
        File file = new File(TASKS_FILE);
        if (!file.exists()) {
            LOGGER.info("Tasks file not found, creating a new one.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.severe("Error creating tasks file: " + e.getMessage());
                return; // Exit the method if file creation fails
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromCSV(line);
                taskList.add(task);
                LOGGER.info("Task loaded: " + task.toCSV()); // Logging loaded tasks
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
                LOGGER.info("Task saved: " + task.toCSV()); // Logging saved tasks
            }
        } catch (IOException e) {
            LOGGER.severe("Error writing tasks to file: " + e.getMessage());
        }
    }
}