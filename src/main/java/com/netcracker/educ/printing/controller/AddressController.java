package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.Address;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.AddressRepo;
import com.netcracker.educ.printing.model.repository.CityRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.AddressRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/address")
@AllArgsConstructor
@Slf4j
public class AddressController {

    private AddressRepo addressRepo;
    private UserRepo userRepo;
    private CityRepo cityRepo;
    private final AddressService addressService;

    @GetMapping("/user")
    public List<AddressRepresent> getAddressByUser(@AuthenticationPrincipal UserDetailsImpl principal) {
        return addressService.getAddressesByUser(principal);
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

    @PostMapping
    public Address addAddress(@RequestBody Map<String, String> address) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return addressService.add(principal.getEmail(), address.get("cityTitle"), address.get("description"));
    }

    @DeleteMapping("{id}")
    public UUID deleteAddress(@PathVariable("id") UUID id) {
        addressRepo.deleteById(id);

        return id;
    }
}
