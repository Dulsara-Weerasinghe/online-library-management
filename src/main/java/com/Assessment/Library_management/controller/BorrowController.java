package com.Assessment.Library_management.controller;

import com.Assessment.Library_management.dto.BorrowBookRequest;
import com.Assessment.Library_management.dto.FilterBooksRequest;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.dto.ReturnBooksRequest;
import com.Assessment.Library_management.exception.DataNotFounException;
import com.Assessment.Library_management.service.BookService;
import com.Assessment.Library_management.service.BorrowService;
import com.Assessment.Library_management.util.EndPoints;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/borrow")
@AllArgsConstructor
@Slf4j
public class BorrowController {
    @Autowired
    private static BorrowService borrowService;


    /**
     *
     *  API to Borrow Book
     */

    @PostMapping(value = EndPoints.BORROW_BOOK,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBean borrowBooks(@RequestBody BorrowBookRequest borrowBookRequest) throws DataNotFounException {
        log.debug("Borrow Book request" + borrowBookRequest);
        return borrowService.borrowBooks(borrowBookRequest);
    }


    /**
     *
     * Return available books
     */

    @PostMapping(value = EndPoints.RETURN_BOOKS,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBean returnBooks(@RequestBody ReturnBooksRequest returnBooksRequest) throws DataNotFounException {
        log.debug("Return book request" + returnBooksRequest);
        return borrowService.returnBooks(returnBooksRequest);
    }



    /**
     *
     * Borrow History
     */
    @PostMapping(value = EndPoints.BORROW_HISTORY,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBean borrowHistory(@PathVariable("userId") String userId) throws DataNotFounException {
        log.debug("Borrow History" + userId);
        return borrowService.borrowHistory(userId);
    }
}
