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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final CityRepo cityRepo;
    private final UserService userService;


    public Address add(String userEmail, String cityName, String description) {
        log.debug("User {} add address: City:{} Description: {}",userEmail,cityName,description);
        User user = userRepo.findByEmail(userEmail);
        City city = cityRepo.findAllByTitle(cityName);
        Address address = new Address(user, city, description);
        Address dbAddress=addressRepo.save(address);
        log.info("User {} added address: City:{} Description: {}",user.getId(),city.getId(),description);
        return dbAddress;
    }

    public List<AddressRepresent> getAddressesByUser(UserDetailsImpl principal) {
        log.info("Get Addresses by user {}",principal.getId());
        User user = userRepo.findByEmail(principal.getEmail());
        return userService.addressToAddressRepresent(user.getAddresses());
    }

    public List<String> getCitiesTitleByUser(UserDetailsImpl principal) {
        User user = userRepo.findByEmail(principal.getEmail());
        List<String> cities = new ArrayList<>();
        for (Address address : user.getAddresses()) {
            cities.add(address.getCity().getTitle());
        }
        log.info("Get Cities for user {}",principal.getId());
        return cities;
    }

    public List<Address> findAllByCity(Long cityId) {
        City city = cityRepo.findAllById(cityId);
        List<Address> dbAddresses=addressRepo.findAllByCity(city);
        log.info("Get addresses by city {}",cityId);
        return dbAddresses;
    }

    public UUID deleteAddressById(UUID id) {
        addressRepo.deleteById(id);
        log.info("Delete address {}",id);
        return id;
    }
}
