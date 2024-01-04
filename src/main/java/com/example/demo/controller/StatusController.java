package com.example.demo.controller;

import com.example.demo.entity.MessageModel;
import com.example.demo.entity.Status;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
@CrossOrigin(origins = "http://localhost:4200")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/getAllStatus")
    public ResponseEntity<List<Status>> getAllStatus() {
        try {
            return new ResponseEntity<>(statusService.getAllStatus(), HttpStatus.OK);

        } catch (MessageModelNotFoundException e) {

            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
