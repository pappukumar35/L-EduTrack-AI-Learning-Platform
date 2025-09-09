package com.EduTrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EduTrack.entity.User;
import com.EduTrack.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    // Constructor injection for UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Home page handler (if needed)
    @GetMapping("/index")
    public String getHome() {
        return "index"; 
    }

    // Show the registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; 
    }

    // Process the registration form and save the user
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/login";  
    }

    // Show the login page
    @GetMapping("/login")
    public String openLogin() {
        return "login";
    }
    
    @GetMapping("/Courses")
    public String coursesPage() {
        return "Courses"; // no .html, Thymeleaf adds it
    }

    
    //courses  controller in this section for selling the course
    @GetMapping("/courses/html")
    public  String getHtml() {
    	return "html";
    }
    
    @GetMapping("/courses/java")
    public String openJava() {
    	return "java";
    }
    
    @GetMapping("/courses/mysql")
    public String openMysql() {
    	return "mysql";
    }
    
    @GetMapping("/course/jdbc")
    public String openJdbc() {
    	return"jdbc";
    }
    
    @GetMapping("/courses/command")
    public String openGitBase() {
    	return "command";
    }
    
   
    @GetMapping("/progress")
    public String getProgress(Model model) {
    	return "progress";
    }
    
    
    
    
    
    
    
    
    @GetMapping("/login2")
    public String loginPage() {
        return "login2";
    }

    @GetMapping("/register2")
    public String registerPage() {
        return "register2";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String email, @RequestParam String password) {
        // check from database, for now just redirect
        return "redirect:/dashboard";
    }

    @PostMapping("/saveUser")
    public String saveUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        // save to DB logic
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    
    
    @GetMapping("/gamifiedzone")
    public String showGamifiedZone() {
        return "gamifiedzone"; // matches file name in templates/ or static/
    }
    
    
    @GetMapping("/career")
    public String showCareerPage() {
        return "Career"; // yeh templates/Career.html file ko render karega (case-sensitive ho sakta hai)
    }


    @GetMapping("/challenge")
    public String showChallengePage() {
        return "Challenge"; // yeh templates/Challenge.html file ko render karega
    }
    
    @GetMapping("/community")
    public String showCommunityPage() {
        return "community"; // yeh templates/community.html file ko render karega
    }

    @GetMapping("/forums")
    public String showForumsPage() {
        return "forums"; // yeh templates/forums.html file ko render karega
    }
    @GetMapping("/doubt-corner")
    public String showdoubt() {
        return "doubt-corner"; // yeh templates/forums.html file ko render karega
    }
    @GetMapping("/mentor-connect")
    public String showmentor() {
        return "mentor-connect"; // yeh templates/forums.html file ko render karega
    }
    
    @GetMapping("/study-groups")
    public String showstudy() {
        return "study-groups"; // yeh templates/forums.html file ko render karega
    }
    
    
    @GetMapping("/Gamified")
    public String openGamifiedZone() {
    	return "gamifiedzone";
    }
    
    @GetMapping("/course_community")
    public String openCoursesCommunity() {
         return "community";
    }
    
    @GetMapping("/CoursesCareer")
    public String CoursesCareer() {
    	return "Career";
    }
    
}
