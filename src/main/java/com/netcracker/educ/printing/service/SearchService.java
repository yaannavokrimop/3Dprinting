package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.bean.SearchParam;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.CityRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SearchService {
    private UserRepo userRepo;
    private CityRepo cityRepo;

    public SearchService(CityRepo cityRepo, UserRepo userRepo) {
        this.cityRepo = cityRepo;
        this.userRepo = userRepo;
    }

    public List<String> getCitiesByTitlePart(String cityTitlePart) {
        return cityRepo.findTitleByTitleContaining(cityTitlePart);
    }

    public Page<User> getPageOfExecutors(SearchParam params) {
        List<City> cities;
        if (params.getCities() == null || params.getCities().isEmpty()) cities = null;
        else cities = cityRepo.findAllByTitleIn(params.getCities());
        Pageable page = PageRequest.of(params.getCurrentPage() - 1, params.getPerPage(), Sort.by("surname").and(Sort.by("name")));
        return userRepo.findAllByParams(cities, params.getHeight(), params.getWidth(), params.getLength(), Role.EXECUTOR, page);
    }
}
