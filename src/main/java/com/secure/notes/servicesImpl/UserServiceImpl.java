package com.secure.notes.servicesImpl;

import com.secure.notes.dtos.UserDTO;
import com.secure.notes.model.AppRole;
import com.secure.notes.model.Role;
import com.secure.notes.model.User;
import com.secure.notes.repository.RoleRepository;
import com.secure.notes.repository.UserRepository;
import com.secure.notes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void updateUserRole(Long userId, String roleName) {
        User user= userRepository.findById(userId).orElseThrow(()
        -> new RuntimeException("User not found"));

        AppRole appRole = AppRole.valueOf(roleName);

        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow();
        return convertToDto(user);
    }

    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isEnabled(),
                user.isCredentialsNonExpired(),
                user.isTwoFactorEnabled(),
                user.getTwoFactorSecret(),
                user.getSignUpMethod(),
                user.getRole(),
                user.getCreateDate(),
                user.getUpdatedDate()
        );
    }
}
