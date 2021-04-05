package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.Address;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.AddressRepo;
import com.netcracker.educ.printing.model.repository.CityRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/address")
@AllArgsConstructor
public class AddressController {

    private AddressRepo addressRepo;
    private UserRepo userRepo;
    private CityRepo cityRepo;

    @GetMapping("/user")
    public List<Address> getAddressByUser() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByName(principal.getUsername());
        return addressRepo.findAllByUser(user);
    }

    @GetMapping("/city")
    public List<Address> getAddressByCity(@RequestParam Long cityId) {
        City city = cityRepo.findAllById(cityId);
        return addressRepo.findAllByCity(city);
    }

//    @PostMapping("/user")
//    public Address createAddress(@RequestParam Integer cityId, String description) {
//        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepo.findByName(principal.getUsername());
//        City city = cityRepo.findAllById(cityId);
//        Address address = new Address(city, user, description);
//        return addressRepo.save(address);
//    }
}
