package com.EduTrack.controller;

import com.EduTrack.model.User;
import com.EduTrack.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, 
                             BindingResult result, 
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            // Validate password length
            if (user.getPassword().length() < 8) {
                model.addAttribute("error", "Password must be at least 8 characters long");
                return "register";
            }

            // Validate email format
            if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                model.addAttribute("error", "Invalid email format");
                return "register";
            }

            authService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/oauth2/callback/google")
    public String googleCallback(@RequestParam String code, RedirectAttributes redirectAttributes) {
        try {
            // Handle Google OAuth callback
            // This will be implemented with Google OAuth client
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Google login failed: " + e.getMessage());
            return "redirect:/login";
        }
    }
} 