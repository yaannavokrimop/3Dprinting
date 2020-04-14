package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.representationModel.UserRepresent;
import com.netcracker.educ.printing.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private SearchService searchService;


    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping("{city}")
    public List<UserRepresent> searchExecutorsByAddress(@PathVariable("city")String city){
     return searchService.searchExecutorsByAddress(city);
    }


}
