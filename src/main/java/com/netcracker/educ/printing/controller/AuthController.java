package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.payload.LoginRequest;
import com.netcracker.educ.printing.payload.LoginResponce;
import com.netcracker.educ.printing.payload.SignUpRequest;
import com.netcracker.educ.printing.payload.SignupResponce;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        log.info("User " + userDetails.getId() + " logged in! ");

        return ResponseEntity.ok(new LoginResponce(jwt, roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registration(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepo.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new SignupResponce( false, "Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getEmail(),
                signUpRequest.getInformation(), signUpRequest.getPhone());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Role role = Role.valueOf(signUpRequest.getRole());
        user.setRole(role);
        User result = userRepo.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/signup")
                .buildAndExpand(result.getEmail()).toUri();
        return ResponseEntity.created(location).body(new SignupResponce(true, "User registered successfully"));
    }
}
