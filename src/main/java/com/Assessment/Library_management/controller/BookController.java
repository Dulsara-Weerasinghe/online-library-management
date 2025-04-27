package com.Assessment.Library_management.controller;

import com.Assessment.Library_management.dto.FilterBooksRequest;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.exception.DataNotFounException;
import com.Assessment.Library_management.service.BookService;
import com.Assessment.Library_management.service.UserService;
import com.Assessment.Library_management.util.EndPoints;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
@Slf4j
public class BookController {

    @Autowired
    private static BookService bookService;

    @GetMapping(value = EndPoints.EXPLORE_BOOKS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBean availableBooks() throws DataNotFounException {
        log.debug("Get book details");
        return bookService.availableBooks();
    }


    @PostMapping(value = EndPoints.FILTER_BOOKS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBean filterBooks(@RequestBody FilterBooksRequest filterBooksRequest) throws DataNotFounException {
        log.debug("Get book details by author and publish date" + filterBooksRequest);
        return bookService.filterBooks(filterBooksRequest);
    }


}
