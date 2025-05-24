package com.library.model;

import lombok.Data;

@Data
public class Author {
    private Long id;
    private String name;
    private Integer birthYear;
    private String country;
} 