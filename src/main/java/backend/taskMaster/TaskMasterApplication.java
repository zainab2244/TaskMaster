package backend.taskMaster;

import backend.taskMaster.model.User;
import backend.taskMaster.model.Settings;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskMasterApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Enter Verirfication Code:");
        String verificationCode = scanner.nextLine();

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter date of birth (YYYY-MM-DD):");
        String dateOfBirthString = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, DateTimeFormatter.ISO_LOCAL_DATE);

        System.out.println("Enter address:");
        String address = scanner.nextLine();

        Settings settings = new Settings(8, LocalTime.of(9, 0), LocalTime.of(17, 0), 15); // default settings

        User user = new User(username, email, password, verificationCode, firstName, lastName, phoneNumber, dateOfBirth, address, settings);

        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(user.toCSV() + "\n");
            writer.close();
            System.out.println("User saved to users.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
