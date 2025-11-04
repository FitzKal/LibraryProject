package com.library.backend.Controller;

import com.library.backend.DTOs.BookResponseDTO;
import com.library.backend.DTOs.UserRequestDTO;
import com.library.backend.DTOs.UserResponseDto;
import com.library.backend.Service.AdminService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@NonNull @PathVariable Long id){
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(adminService.getAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@NonNull @PathVariable Long id){
        adminService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<UserResponseDto> updateUser(@NonNull @PathVariable Long id,
                                                        @NonNull @RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(adminService.updateUser(id,userRequestDTO));
    }
}
