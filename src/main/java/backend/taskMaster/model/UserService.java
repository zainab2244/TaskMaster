package backend.taskMaster.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private List<User> userList = new ArrayList<>();

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
            User newUser = new User(username, email, password, verificationCode, firstName, lastName,
                    phoneNumber, dateOfBirth, address, defaultSettings);

            userList.add(newUser);

            System.out.println("User created successfully!");

            System.out.println("Current users:");
            for (User user : userList) {
                System.out.println(user);
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

    public List<User> getUsers() {
        return userList;
    }
}
