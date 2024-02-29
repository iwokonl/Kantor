package pl.zeto.backend.VMC.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class Test {
    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages() {
        return ResponseEntity.ok(Arrays.asList("First message", "Second message"));
    }
}
