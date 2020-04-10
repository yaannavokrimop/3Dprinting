package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.Address;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepo extends JpaRepository<Address, UUID> {
    List<Address> findAllByCity(City city);
    List<Address> findAllByUser(User user);
}
