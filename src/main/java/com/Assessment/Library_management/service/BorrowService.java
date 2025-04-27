package com.Assessment.Library_management.service;

import com.Assessment.Library_management.dto.BorrowBookRequest;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.dto.ReturnBooksRequest;
import com.Assessment.Library_management.exception.DataNotFounException;

public interface BorrowService {
    ResponseBean borrowBooks(BorrowBookRequest borrowBookRequest);

    ResponseBean returnBooks(ReturnBooksRequest returnBooksRequest);

    ResponseBean borrowHistory(String userId) throws DataNotFounException;
}
