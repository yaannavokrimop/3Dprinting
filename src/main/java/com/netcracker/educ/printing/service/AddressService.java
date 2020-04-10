package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.entity.Address;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.AddressRepo;
import com.netcracker.educ.printing.model.repository.CityRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final CityRepo cityRepo;

    public Address add(String userEmail, String cityName, String description) {
        User user = userRepo.findByEmail(userEmail);
        City city = cityRepo.findAllByTitle(cityName);
        Address address = new Address(user, city, description);
        addressRepo.save(address);
        city.getAddresses().add(address);
        user.getAddresses().add(address);
        return address;
    }
}
