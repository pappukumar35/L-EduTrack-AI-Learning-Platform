package com.EduTrack.service;

import com.EduTrack.model.User;
import com.EduTrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,20}$");

    @Transactional
    public User registerUser(User user) {
        // Validate username
        if (!USERNAME_PATTERN.matcher(user.getUsername()).matches()) {
            throw new RuntimeException("Username must be 3-20 characters long and can only contain letters, numbers, underscores, and hyphens");
        }

        // Validate email
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new RuntimeException("Invalid email format");
        }

        // Check if username exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Validate password
        if (user.getPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long");
        }

        // Set default values
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Transactional
    public User saveGoogleUser(String email, String name, String googleId) {
        User user = userRepository.findByEmail(email)
                .orElse(new User());
        
        // Set user details
        user.setEmail(email);
        user.setFullName(name);
        user.setGoogleId(googleId);
        user.setUsername(email.split("@")[0]);
        user.setPassword(passwordEncoder.encode(googleId));
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        
        return userRepository.save(user);
    }
} 