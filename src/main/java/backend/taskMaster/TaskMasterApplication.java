package backend.taskMaster;

import backend.taskMaster.model.User;
import backend.taskMaster.model.Settings;
import backend.taskMaster.model.UserService;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskMasterApplication.class, args);

    }
}
