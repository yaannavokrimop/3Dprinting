package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.service.SearchService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    private SearchService searchService;


    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }



}
