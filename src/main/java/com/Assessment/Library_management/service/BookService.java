package com.Assessment.Library_management.service;

import com.Assessment.Library_management.dto.FilterBooksRequest;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.exception.DataNotFounException;

public interface BookService {
    ResponseBean availableBooks() throws DataNotFounException;

    ResponseBean filterBooks(FilterBooksRequest filterBooksRequest);
}
