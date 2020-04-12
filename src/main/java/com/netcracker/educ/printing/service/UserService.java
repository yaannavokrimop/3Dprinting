package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.Address;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.AddressRepresent;
import com.netcracker.educ.printing.model.representationModel.UserRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

        public boolean createUser(User user) {
        User userFromDB = userRepo.findByEmail(user.getEmail());
        if (userFromDB != null) return false;
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

    public UserRepresent updateUser(UserRepresent user){
        User dbUser=userRepo.findById(user.getId()).get();
        User updateUser=new User(user.getName(),user.getSurname(),user.getEmail(),user.getInformation(),user.getPhone(),user.getRole());
        BeanUtils.copyProperties(updateUser,dbUser,"id","password","addresses");
        return userToUserRepresent(userRepo.save(dbUser));
    }

    public List<UserRepresent> findAllExecutors() {
        List<User> executors=userRepo.findByRole(Role.EXECUTOR);
        return usersToUserRepresents(executors);
    }

    public List<UserRepresent> findExecutorsByAddresses() {
        List<User> executors=userRepo.findByRole(Role.EXECUTOR);
        return usersToUserRepresents(executors);
    }

    public List<UserRepresent> usersToUserRepresents(List<User> users){
        List<UserRepresent> userRepresents=new ArrayList<>();
        User user;
        Iterator<User> iter=users.iterator();
        while (iter.hasNext()){
            user=iter.next();
            userRepresents.add(userToUserRepresent(user));
        }
        return userRepresents;
    }

    public UserRepresent userToUserRepresent(User user) {
        return new UserRepresent(user.getId(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getPhone(),
                user.getEmail(),
                user.getInformation(),
                addressToAddressRepresent(user.getAddresses()));
    }

    public List<AddressRepresent> addressToAddressRepresent(List<Address> addresses){
            List<AddressRepresent> addressRepresents=new ArrayList<>();
            Iterator<Address> iterator=addresses.iterator();
            Address addr;
            while(iterator.hasNext()){
                addr=iterator.next();
                addressRepresents.add(new AddressRepresent(addr.getCity().getTitle(),addr.getDescription(),addr.getUser().getId()));
            }
            return addressRepresents;
    }

    public UserRepresent getCurrentUser(UserDetailsImpl principal) {
        UserRepresent user=userToUserRepresent(userRepo.findByEmail(principal.getEmail()));
        log.info("This user "+user.getEmail()+" in his profile");
        return user;


    }

    public Boolean checkUserRole(UserDetailsImpl principal) {
        User user=userRepo.findByEmail(principal.getEmail());
        return user.getRole().toString().equals("CUSTOMER");

    }
}
