package com.library.backend.Controller;

import com.library.backend.DTOs.UserResponseDto;
import com.library.backend.Service.AdminService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@NonNull @PathVariable Long id){
        return ResponseEntity.ok(adminService.getUserById(id));
    }


}
