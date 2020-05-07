package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.bean.SearchParam;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.Material;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.CityRepo;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SearchService {
    private final UserRepo userRepo;
    private final CityRepo cityRepo;
    private final MaterialRepo materialRepo;
    private final JdbcTemplate jdbcTemplate;

    private final String QUERY = "";

    public List<String> getCitiesByTitlePart(String cityTitlePart) {
        return cityRepo.findTitleByTitleContaining(cityTitlePart);
    }

    public Page<User> getPageOfExecutors(SearchParam params) {
        Pageable page = PageRequest.of(params.getCurrentPage() - 1, params.getPerPage(), Sort.by("surname").and(Sort.by("name")));
        List<City> cities;
        if (params.getCities() == null || params.getCities().isEmpty()) cities = null;
        else cities = cityRepo.findAllByTitleIn(params.getCities());
        List<Material> materials;
        if (params.getMaterials() == null || params.getMaterials().isEmpty())
            return userRepo.findAllByParams(cities, params.getHeight(), params.getWidth(), params.getLength(), Role.EXECUTOR, page);
        else {
            materials = materialRepo.findAllByMatTitleIn(params.getMaterials());
            return userRepo.findAllByParamsWithMaterials(cities, params.getHeight(), params.getWidth(), params.getLength(), materials, Role.EXECUTOR, page);
        }
    }
}
