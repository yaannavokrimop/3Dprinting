package com.netcracker.educ.printing.model.bean;

import lombok.Data;

import java.util.List;

@Data
public class SearchParam {
    List<String> cities;
    int height;
    int width;
    int length;
    List<String> materials;
    int currentPage;
    int perPage;
}
