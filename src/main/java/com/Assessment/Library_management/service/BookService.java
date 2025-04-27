package com.Assessment.Library_management.service;

import com.Assessment.Library_management.dto.FilterBooksRequest;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.exception.DataNotFounException;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<?> availableBooks() throws DataNotFounException;

    ResponseEntity<?> filterBooks(FilterBooksRequest filterBooksRequest);
}
