package com.netcracker.educ.printing.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationBean {
    private int pageCount;
    private List<? extends Pageable> content;

}
