package se2.fit.tut08.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se2.fit.tut08.model.User;
import se2.fit.tut08.model.UserDto;
import se2.fit.tut08.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto, 
                              BindingResult bindingResult, 
                              Model model) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // Check if username already exists
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username", "Username already exists");
            return "register";
        }

        // Create and save the User
        User user = new User(userDto, passwordEncoder);
        user.setRoles(List.of()); // Set empty roles or assign a default role if needed
        userRepository.save(user);

        // Redirect to a success page or login
        return "registration-success";
    }
}