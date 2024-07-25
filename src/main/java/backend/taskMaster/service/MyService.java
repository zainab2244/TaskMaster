package backend.taskMaster.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    public String process(String input) {
        return "Processed: " + input;
    }
}