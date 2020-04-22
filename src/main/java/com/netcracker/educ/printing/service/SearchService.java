package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.Equipment;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.*;
import com.netcracker.educ.printing.model.representationModel.UserRepresent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchService {

    private UserService userService;
    private UserRepo userRepo;
    private AddressRepo addressRepo;
    private CityRepo cityRepo;
    private OrderRepo orderRepo;
    private EquipmentRepo equipmentRepo;

    public SearchService(UserService userService, AddressRepo addressRepo, CityRepo cityRepo,UserRepo userRepo, OrderRepo orderRepo,EquipmentRepo equipmentRepo) {
        this.userService = userService;
        this.addressRepo = addressRepo;
        this.cityRepo = cityRepo;
        this.userRepo=userRepo;
        this.orderRepo = orderRepo;
        this.equipmentRepo=equipmentRepo;
    }


    public List<UserRepresent> searchExecutorsByAddress(String city) {
        List<User> users=userRepo.findByRoleAndAddressesCityName(cityRepo.findAllByTitle(city),Role.EXECUTOR);
        return userService.usersToUserRepresents(users);
    }


    public List<UserRepresent> searchExecutorsByAddresses(List<String> city) {
        List<User> users=userRepo.findByRoleAndAddressesCityNames(cityRepo.findAllByTitleIn(city),Role.EXECUTOR);
        return userService.usersToUserRepresents(users);
    }

    public List<UserRepresent> searchExecutorsByOrderParameters(Order order) {
        Order orderParam = orderRepo.findById(order.getId()).orElse(new Order());
        List<User> users = userRepo.findByRoleAndEquipmentsParameters(orderParam.getHeight(), orderParam.getWidth(), orderParam.getLength(), Role.EXECUTOR);
        return userService.usersToUserRepresents(users);
    }

    public List<String> getCitiesByTitlePart(String cityTitlePart) {
        return cityRepo.findTitleByTitleContaining(cityTitlePart);

    }
}
