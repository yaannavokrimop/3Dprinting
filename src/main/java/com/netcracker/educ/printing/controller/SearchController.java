package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.bean.PaginationBean;
import com.netcracker.educ.printing.model.bean.SearchParam;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.representationModel.UserRepresent;
import com.netcracker.educ.printing.service.SearchService;
import com.netcracker.educ.printing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/search")
public class SearchController {
    private SearchService searchService;
    private UserService  userService;


    public SearchController(SearchService searchService, UserService userService) {
        this.searchService = searchService;
        this.userService = userService;
    }

    @PostMapping("/executors")
    public ResponseEntity<PaginationBean> listExecutorsPage(@RequestBody SearchParam params) {
        log.debug("Get list of executors by params");
        Page<User> executorsPage = searchService.getPageOfExecutors(params);
        List<UserRepresent> executors = userService.usersToUserRepresents(executorsPage.getContent());
        return ResponseEntity.ok(new PaginationBean(executorsPage.getTotalPages(), executors));
    }

    @GetMapping("/cityList/{titlePart}")
    public List<String> getCityNames(@PathVariable("titlePart") String cityTitlePart){
        log.debug("Get cityName by part of name: {}",cityTitlePart);
        return searchService.getCitiesByTitlePart(cityTitlePart);

    }
}
