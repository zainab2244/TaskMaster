package backend.taskMaster.controller;

import backend.taskMaster.model.User;
import backend.taskMaster.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/firstnames")
    public List<String> getUserFirstNames() {
        List<User> users = userService.readUsersFromFile();
        return users.stream().map(User::getFirstName).collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User loginRequest) {
        User verifiedUser = userService.verifyUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (verifiedUser != null) {
            return ResponseEntity.ok(verifiedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/user/data")
    public User getUserData(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }

    @PutMapping("/user/data")
    public void updateUserData(@RequestBody User updatedUser) {
        userService.updateUser(updatedUser);
    }

}
