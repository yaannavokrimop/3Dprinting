package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        model.put("customer", Role.CUSTOMER);
        model.put("executor", Role.EXECUTOR);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        boolean result = userService.createUser(user);
        if (result) {
            model.put("message", " Регистрация прошла успешно!");
        } else {
            model.put("message", "Ошибка при регистрации!!!");
        }
        return "redirect:/login";
    }
}
