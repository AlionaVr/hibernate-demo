package org.hibernatedemo.controller;

import jakarta.annotation.security.RolesAllowed;
import org.hibernatedemo.exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureDataController {

    @GetMapping("/read-data")
    @Secured(("ROLE_READ"))
    public ResponseEntity<ApiResponse<String>> readData() {
        return ResponseEntity.ok(
                new ApiResponse<>("success", "You can read this READ-protected data!")
        );
    }

    @GetMapping("/write-data")
    @RolesAllowed("WRITE")
    public ResponseEntity<ApiResponse<String>> readDataIfYourRoleWrite() {
        return ResponseEntity.ok(
                new ApiResponse<>("success", "You can read this WRITE-protected data!")
        );
    }

    @GetMapping("/modify-data")
    @PreAuthorize("hasAnyRole('WRITE', 'DELETE')")
    public ResponseEntity<ApiResponse<String>> modifyData() {
        return ResponseEntity.ok(
                new ApiResponse<>("success", "You can modify data (WRITE or DELETE role)")
        );
    }

    @GetMapping("/user-data")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<ApiResponse<String>> userData(@RequestParam String username) {
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Welcome, " + username + "! This is your private data.")
        );
    }
}