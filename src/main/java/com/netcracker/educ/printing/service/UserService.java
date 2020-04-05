package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

        public boolean createUser(User user) {
        User userFromDB = userRepo.findByEmail(user.getEmail());
        if (userFromDB != null) return false;
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }

    public Optional<User> get(UUID id) {
            return userRepo.findById(id);
    }

    public String userSave(
            String name,
            String surname,
            String email,
            String information,
            String phone,
            String password,
//            String role,
            User user
    )  {

        editInfo(user, "name", name);
        editInfo(user, "surname", surname);
        editInfo(user, "email", email);
        editInfo(user, "information", information);
        editInfo(user, "phone", phone);
//        editInfo(user, "role", role);
        savePass(user, password);

        userRepo.save(user);

        return "redirect:/user";
    }

    public void editInfo(User user, String field, String value)  {
        try {
            StringBuilder methodName = new StringBuilder("set");
            methodName.append(field.substring(0, 1).toUpperCase()).append(field.substring(1));
            Method setter = user.getClass().getMethod(methodName.toString(), value.getClass());
            if (!value.equals("")) {
                setter.invoke(user, value);
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }


    public void savePass(User user, String password) {
        if (!password.equals("")) {
            editInfo(user,
                    "password",
                    passwordEncoder.encode(password)
            );
        }
    }

    public User updateUser(User user){
        return userRepo.save(user);
    }

    public List<User> findAllExecutors() {
        return userRepo.findByRole(Role.EXECUTOR);
    }
}
