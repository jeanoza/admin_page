package com.fastcampus.java.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 모든 parameter에 대한 생성자 추가
public class SearchParam {

    private String account;
    private String email;
    private int page;
    private String bebe;


}
