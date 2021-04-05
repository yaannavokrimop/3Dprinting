package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.Address;
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

    private final AddressService addressService;

    @GetMapping("/user")
    public List<AddressRepresent> getAddressByUser(@AuthenticationPrincipal UserDetailsImpl principal) {
        log.debug("Get addresses for user {}",principal.getId());
        return addressService.getAddressesByUser(principal);
    }

    @GetMapping("/user/city")
    public List<String> getCitiesByUser() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("Get cities by user {}",principal.getId());
        return addressService.getCitiesTitleByUser(principal);
    }

    @GetMapping("/city")
    public List<Address> getAddressByCity(@RequestParam Long cityId) {
        log.debug("Get addresses by city {}",cityId);
        return addressService.findAllByCity(cityId);
    }

    @PostMapping
    public Address addAddress(@RequestBody Map<String, String> address) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("Address was added by user {}",principal.getId());
        return addressService.add(principal.getEmail(), address.get("cityTitle"), address.get("description"));
    }

    @DeleteMapping("{id}")
    public UUID deleteAddress(@PathVariable("id") UUID id) {
        log.debug("Delete address {}",id);
        return addressService.deleteAddressById(id);
    }
}
