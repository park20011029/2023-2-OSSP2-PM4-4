package project.manager.server.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class init {
    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
