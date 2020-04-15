package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {
    City findAllByTitle(String title);
    List<City> findAllByTitleIn(List<String> title);
    City findAllById(Long id);
}
