package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> profile(
            @AuthenticationPrincipal User user, Map<String, Object> model
    ) {

        model.put("name", user.getName());
        model.put("surname", user.getSurname());
        model.put("email", user.getEmail());
        model.put("information", user.getInformation());
        model.put("role", user.getRole());
        model.put("phone", user.getPhone());

        return userService.findAll();
    }

    @GetMapping("edit")
    public String userEditForm(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("customer", Role.CUSTOMER);
        model.addAttribute("executor", Role.EXECUTOR);
        model.addAttribute("name", user.getName());


        return "userEdit";
    }

    @PostMapping
    public String userSave(
        @RequestParam String name,
        @RequestParam String surname,
        @RequestParam String email,
        @RequestParam String information,
        @RequestParam String phone,
        @RequestParam String password,
        @RequestParam String role,
        @AuthenticationPrincipal User user
    )  {

        userService.userSave(
                name,
                surname,
                email,
                information,
                phone,
                password,
                role,
                user
        );


        return "redirect:/user";
    }



}
