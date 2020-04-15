package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.AddressRepo;
import com.netcracker.educ.printing.model.repository.CityRepo;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.UserRepresent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private UserService userService;
    private UserRepo userRepo;
    private AddressRepo addressRepo;
    private CityRepo cityRepo;
    private OrderRepo orderRepo;

    public SearchService(UserService userService, AddressRepo addressRepo, CityRepo cityRepo,UserRepo userRepo, OrderRepo orderRepo) {
        this.userService = userService;
        this.addressRepo = addressRepo;
        this.cityRepo = cityRepo;
        this.userRepo=userRepo;
        this.orderRepo = orderRepo;
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
}
