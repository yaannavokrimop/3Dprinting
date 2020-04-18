package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {

    City findAllByTitle(String title);
    List<City> findAllByTitleIn(List<String> title);
    City findAllById(Long id);

    @Query("select c.title from City c where c.title like concat('%',:title,'%')")
       List<String> findTitleByTitleContaining(@Param(value = "title") String titlePart);
}
