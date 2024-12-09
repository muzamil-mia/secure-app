package com.secure.notes.dtos;

import com.secure.notes.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean twoFactorEnabled;
    private String twoFactorSecret;
    private String signUpMethod;
    private Role role;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;
}
