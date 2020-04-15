package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.representationModel.UserRepresent;
import com.netcracker.educ.printing.service.SearchService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/cityList")
    public List<UserRepresent> searchExecutorsByAddresses(@RequestBody List<String> city){
        return searchService.searchExecutorsByAddresses(city);
    }

    @PostMapping("/orderParam")
    public List<UserRepresent> searchExecutorByOrderParams(@RequestBody Order order) {
        return searchService.searchExecutorsByOrderParameters(order);
    }
}
