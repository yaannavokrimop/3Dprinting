package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Data
public class MainController {


    private final UserRepo userRepo;


//    @GetMapping(value = "/")
//    public String greeting() {
//        return "";
//    }



    @GetMapping("/main")
    public String main() {
        return "Hello World";
    }
}