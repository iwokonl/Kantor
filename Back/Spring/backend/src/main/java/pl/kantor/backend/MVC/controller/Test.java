package pl.kantor.backend.MVC.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class Test {

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody Map<String,String> tekst) {
        return ResponseEntity.ok(tekst.get("tekst"));
    }
}
