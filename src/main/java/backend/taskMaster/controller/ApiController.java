package backend.taskMaster.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from the backend!";
    }

    @PostMapping("/data")
    public String receiveData(@RequestBody MyData data) {
        // Process the received data
        return "Data received: " + data.getSomeField();
    }

    public static class MyData {
        private String someField;

        public String getSomeField() {
            return someField;
        }

        public void setSomeField(String someField) {
            this.someField = someField;
        }
    }
}
