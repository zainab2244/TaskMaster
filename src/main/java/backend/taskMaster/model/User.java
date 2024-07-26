package backend.taskMaster.model;

import java.time.LocalDate;

public class User {
    private String username;
    private String email;
    private String password;
    private String verificationCode;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private Settings settings;

    public User(String username, String email, String password, String verificationCode, String firstName,
                String lastName, String phoneNumber, LocalDate dateOfBirth, String address, Settings settings) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.settings = settings; // Initialize settings
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public Settings getSettings() {
        return settings;
    }

    // Setter method for settings
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", settings=" + settings +
                '}';
    }


    public String toCSV() {
        return username + "," + email + "," + password + "," + verificationCode + "," + firstName + "," + lastName + "," + phoneNumber + "," + dateOfBirth + "," + address;
    }
    
}
