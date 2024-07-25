package backend.taskMaster.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user/firstname")
    public String getUserFirstName() {
        // Return a hardcoded first name
        return "John";
    }
}
