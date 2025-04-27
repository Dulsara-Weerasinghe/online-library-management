package com.Assessment.Library_management.service;

import com.Assessment.Library_management.dto.BorrowBookRequest;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.dto.ReturnBooksRequest;
import com.Assessment.Library_management.exception.DataNotFounException;
import org.springframework.http.ResponseEntity;

public interface BorrowService {
    ResponseEntity<?> borrowBooks(BorrowBookRequest borrowBookRequest);

    ResponseEntity<?> returnBooks(ReturnBooksRequest returnBooksRequest);

    ResponseEntity<?> borrowHistory(String userId) throws DataNotFounException;
}
