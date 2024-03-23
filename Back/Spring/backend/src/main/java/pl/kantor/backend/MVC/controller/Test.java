package pl.kantor.backend.MVC.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class Test {
    @GetMapping("/test")
    public ResponseEntity<List<String>> test() {
        return ResponseEntity.ok(Arrays.asList("Hello", "World"));
    }
}