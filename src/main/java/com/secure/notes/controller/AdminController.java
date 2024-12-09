package com.secure.notes.controller;

import com.secure.notes.dtos.UserDTO;
import com.secure.notes.model.User;
import com.secure.notes.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/update-role")
    public ResponseEntity<String> updateUserROle(@RequestParam Long userId, @RequestParam String roleName){
        userService.updateUserRole(userId, roleName);
        return ResponseEntity.ok("user role updated");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUSer(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
}
