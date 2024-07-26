package backend.taskMaster.model;

import java.time.Duration;
import java.util.logging.Logger;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private List<User> userList = new ArrayList<>();

    public UserService() {
        // Load users from file when the service is initialized
        this.userList = readUsersFromFile();
    }

    public void createUser(Scanner scanner) {
        try {
            System.out.println("Welcome to TaskMaster, let's create an account!");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            System.out.print("Enter verification code: ");
            String verificationCode = scanner.nextLine();

            System.out.print("Please re-enter verification code: ");
            String verificationCodeCopy1 = scanner.nextLine();

            while (!verificationCodeCopy1.equals(verificationCode)) {
                System.out.print("Please re-enter verification code: ");
                verificationCodeCopy1 = scanner.nextLine();
            }

            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter phone number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Enter date of birth (YYYY-MM-DD): ");
            String dateOfBirthInput = scanner.nextLine();
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthInput);

            System.out.print("Enter address: ");
            String address = scanner.nextLine();

            Settings defaultSettings = new Settings(8, LocalTime.of(9, 0), LocalTime.of(17, 0), 15); // default settings
            User newUser = new User(username, email, password, verificationCode, firstName, lastName, phoneNumber, dateOfBirth, address, defaultSettings);

            userList.add(newUser);

            System.out.println("User created successfully!");

            System.out.println("Current users:");
            for (User user : userList) {
                System.out.println(user);
            }

            try {
                FileWriter writer = new FileWriter("users.txt", true); // Adjusted path
                writer.write(newUser.toCSV() + "\n");
                writer.close();
                System.out.println("User saved to users.txt");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Error occurred while creating user: " + e.getMessage());
        }
    }
    public void loginUser(Scanner scanner) {
        try {
            System.out.println("Logging in...");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.println("Searching for user: " + username);

            User foundUser = null;
            for (User user : userList) {
                if (user.getUsername().equals(username)) {
                    foundUser = user;
                    break;
                }
            }

            if (foundUser == null) {
                System.out.println("User not found.");
                return;
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (!foundUser.getPassword().equals(password)) {
                System.out.println("Incorrect password.");

                // Ask for verification code
                System.out.print("Enter verification code: ");
                String verificationCode = scanner.nextLine();

                if (foundUser.getVerificationCode().equals(verificationCode)) {
                    System.out.println("User logged in successfully!");
                } else {
                    System.out.println("Incorrect verification code.");
                    System.out.println("Make sure your information is correct.");
                }

                return;
            }

            System.out.println("User logged in successfully!");

        } catch (Exception e) {
            System.out.println("Error occurred while logging in: " + e.getMessage());
        }
    }

    public void updateSettings(Scanner scanner, int userIndex) {
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                User currentUser = userList.get(userIndex);
                System.out.println("Current settings: " + currentUser.getSettings());

                System.out.print("Enter new work hours threshold: ");
                int newWorkHoursThreshold = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter new start time (HH:mm): ");
                LocalTime newStartTime = LocalTime.parse(scanner.nextLine());

                System.out.print("Enter new end time (HH:mm): ");
                LocalTime newEndTime = LocalTime.parse(scanner.nextLine());

                System.out.print("Enter new break duration (in minutes): ");
                int newBreakDuration = Integer.parseInt(scanner.nextLine());

                long availableWorkHours = Duration.between(newStartTime, newEndTime).toHours();

                if (newWorkHoursThreshold > availableWorkHours) {
                    System.out.println(
                            "The work hours threshold exceeds the available work hours between the specified start and end times.");
                    System.out.println("Please enter the settings again.");
                } else {
                    // Update the settings.....
                    Settings newSettings = new Settings(newWorkHoursThreshold, newStartTime, newEndTime,
                            newBreakDuration);
                    currentUser.setSettings(newSettings);
                    System.out.println("Settings updated successfully!");
                    isValidInput = true; // ask
                }

            } catch (NumberFormatException e) {
                System.out
                        .println("Error: Please enter a valid number for the work hours threshold and break duration.");
                System.out.println("Please try again.");
            } catch (Exception e) {
                System.out.println("Error occurred while updating settings: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    public List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LOGGER.info("Reading line: " + line);
                String[] parts = line.split(",");
                if (parts.length >= 9) {
                    try {
                        String username = parts[0].trim();
                        String email = parts[1].trim();
                        String password = parts[2].trim();
                        String verificationCode = parts[3].trim();
                        String firstName = parts[4].trim();
                        String lastName = parts[5].trim();
                        String phoneNumber = parts[6].trim();
                        LocalDate dateOfBirth = LocalDate.parse(parts[7].trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                        String address = parts[8].trim();
                        Settings settings = new Settings(8, LocalTime.of(9, 0), LocalTime.of(17, 0), 15); // default settings

                        User user = new User(username, email, password, verificationCode, firstName, lastName, phoneNumber, dateOfBirth, address, settings);
                        users.add(user);
                        LOGGER.info("Parsed user: " + user);
                    } catch (Exception e) {
                        LOGGER.warning("Failed to parse line: " + line + " - " + e.getMessage());
                    }
                } else {
                    LOGGER.warning("Incorrect format for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User verifyUser(String usernameOrEmail, String password) {
        for (User user : userList) {
            LOGGER.info("Checking user: " + user.getUsername() + ", " + user.getEmail());
            if ((user.getUsername().trim().equalsIgnoreCase(usernameOrEmail.trim()) || user.getEmail().trim().equalsIgnoreCase(usernameOrEmail.trim())) && user.getPassword().trim().equals(password.trim())) {
                LOGGER.info("User verified: " + user.getUsername());
                return user;
            }
        }
        LOGGER.warning("User not verified: " + usernameOrEmail);
        return null;
    }

    public User findUserByUsername(String username) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User updatedUser) {
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getUsername().equals(updatedUser.getUsername())) {
                userList.set(i, updatedUser);
                saveUsersToFile();
                return;
            }
        }
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", false))) {
            for (User user : userList) {
                writer.write(user.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        LOGGER.info("Adding user: " + user);
        userList.add(user);
        saveUsersToFile();
    }
    

    public List<User> getUsers() {
        return userList;
    }
}