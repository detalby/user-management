package com.medvedev.usermanagement.controller;

import com.medvedev.usermanagement.model.Role;
import com.medvedev.usermanagement.model.UserEntity;
import com.medvedev.usermanagement.service.UserService;
import com.medvedev.usermanagement.utile.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


import static com.medvedev.usermanagement.utile.ConstantsError.PASSWORD_ERROR;
import static com.medvedev.usermanagement.utile.ConstantsError.USER_EXIST_ERROR;

/**
 * created by Vladimir Medvedev 15.08.2019
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "userList";
    }


    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(name = "username") String username,
                         @RequestParam(name = "role") String role) {
        if (username.trim().isEmpty() && role.trim().isEmpty()) {
            model.addAttribute("users", userService.getAll());
        } else if (!username.trim().isEmpty() && role.trim().isEmpty()) {
            model.addAttribute("users", userService.filterByName(username));
        } else if (username.trim().isEmpty() && !role.trim().isEmpty()) {
            model.addAttribute("users", userService.getAllByRole(role.equals("ADMIN") ? Role.ADMIN : Role.USER));
        } else {
            model.addAttribute(userService.filterByNameAndRole(username, role.equals("ADMIN") ? Role.ADMIN : Role.USER));
        }
        return "userList";
    }


    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable int id) {
        UserEntity user = userService.getById(id);
        user.setPassword("");
        model.addAttribute("userEntity", user);
        return "user-form";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "user-form";
    }

    @PostMapping("/save")
    public String newUser(@Valid UserEntity user, BindingResult bindingResult) {
        if (user.getId() == 0 && !ValidationUtil.validatePassword(user.getPassword())) {
            bindingResult.addError(PASSWORD_ERROR);
        }
        if (user.getId() > 0 && !user.getPassword().isEmpty()) {
            if (!ValidationUtil.validatePassword(user.getPassword())) {
                bindingResult.addError(PASSWORD_ERROR);
            }
        }
        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        if (user.getId() == 0 && userService.getByName(user.getUsername()) != null) {
            bindingResult.addError(USER_EXIST_ERROR);
            return "user-form";
        }
        if (!user.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        user.setCreated(LocalDateTime.now());
        userService.save(user);
        return "redirect:/user";
    }
}
