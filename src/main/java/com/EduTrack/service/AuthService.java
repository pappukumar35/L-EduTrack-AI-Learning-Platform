package com.EduTrack.service;

import com.EduTrack.model.User;
import com.EduTrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User saveGoogleUser(String email, String name, String googleId) {
        User user = userRepository.findByEmail(email)
                .orElse(new User());
        
        user.setEmail(email);
        user.setFullName(name);
        user.setGoogleId(googleId);
        user.setUsername(email.split("@")[0]);
        user.setPassword(passwordEncoder.encode(googleId));
        
        return userRepository.save(user);
    }
} 