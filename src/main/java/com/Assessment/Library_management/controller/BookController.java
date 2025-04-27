package com.Assessment.Library_management.controller;

import com.Assessment.Library_management.dto.FilterBooksRequest;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.exception.DataNotFounException;
import com.Assessment.Library_management.service.BookService;
import com.Assessment.Library_management.util.EndPoints;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor

public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private static BookService bookService;

    @GetMapping(value = EndPoints.EXPLORE_BOOKS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> availableBooks() throws DataNotFounException {
        log.info("Get book details");
        return bookService.availableBooks();
    }


    @PostMapping(value = EndPoints.FILTER_BOOKS, produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> filterBooks(@RequestBody FilterBooksRequest filterBooksRequest) throws DataNotFounException {
        log.info("Get book details by author and publish date" + filterBooksRequest);
        return bookService.filterBooks(filterBooksRequest);
    }


}
