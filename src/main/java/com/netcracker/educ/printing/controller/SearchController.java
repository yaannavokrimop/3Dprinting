package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.representationModel.UserRepresent;
import com.netcracker.educ.printing.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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

    @PostMapping("/executorsByCities")
    public List<UserRepresent> searchExecutorsByAddresses(@RequestBody Map<String,List<String>> city){
        return searchService.searchExecutorsByAddresses(city.get("w"));
    }

    @PostMapping("/orderParam")
    public List<UserRepresent> searchExecutorByOrderParams(@RequestBody Order order) {
        return searchService.searchExecutorsByOrderParameters(order);
    }

    @PostMapping("/cityList")
    public List<String> getCityNames(@RequestBody Map<String,String> cityTitlePart){
        return searchService.getCitiesByTitlePart(cityTitlePart.get("cityPartName"));

    }
}
