package com.library.model;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private Long authorId;
    private String genre;
    private Integer publishYear;
} 