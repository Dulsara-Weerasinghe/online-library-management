package com.Assessment.Library_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FilterBooksRequest {
    private String author;
    private Integer publishedYear;
}
