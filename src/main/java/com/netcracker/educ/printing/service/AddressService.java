package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.entity.Address;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.AddressRepo;
import com.netcracker.educ.printing.model.repository.CityRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.AddressRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final CityRepo cityRepo;
    private final UserService userService;


    public Address add(String userEmail, String cityName, String description) {
        User user = userRepo.findByEmail(userEmail);
        City city = cityRepo.findAllByTitle(cityName);
        Address address = new Address(user, city, description);
        addressRepo.save(address);
        return address;
    }

    public List<AddressRepresent> getAddressesByUser(UserDetailsImpl principal) {
        User user = userRepo.findByEmail(principal.getEmail());
        return userService.addressToAddressRepresent(user.getAddresses());
    }

    public List<String> getCitiesTitleByUser(UserDetailsImpl principal) {
        User user = userRepo.findByEmail(principal.getEmail());
        List<String> cities = new ArrayList<>();
        for (Address address : user.getAddresses()) {
            cities.add(address.getCity().getTitle());
        }
        return cities;
    }
}
