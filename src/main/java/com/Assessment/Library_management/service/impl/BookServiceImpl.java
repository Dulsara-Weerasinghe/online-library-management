package com.Assessment.Library_management.service.impl;

import com.Assessment.Library_management.controller.BookController;
import com.Assessment.Library_management.dto.FilterBooksRequest;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.entity.Book;
import com.Assessment.Library_management.exception.DataNotFounException;
import com.Assessment.Library_management.repository.BookRepository;
import com.Assessment.Library_management.repository.UserRepository;
import com.Assessment.Library_management.service.BookService;
import com.Assessment.Library_management.util.MessageVarList;
import com.Assessment.Library_management.util.StatusVarList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service

@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private  BookRepository bookRepository;
    @Override
    public ResponseEntity<?> availableBooks() throws DataNotFounException {
        log.info("Available books");

        List<Book> availableCopies = bookRepository.findByAvailableCopiesGreaterThan(0).orElseThrow(()-> new DataNotFounException("No Available books with available copies"));

       if (!availableCopies.isEmpty()){

//           return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.SUCCESS,availableCopies);
           return ResponseEntity.ok(availableCopies);

       }else{
           return ResponseEntity.ok(new ArrayList<>());
//           return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, StatusVarList.AVAILABLE_BOOKS_NOT_FOUND,new ArrayList<>());
       }

    }

    @Override
    public ResponseEntity<?> filterBooks(FilterBooksRequest filterBooksRequest) {
        List<Book> filteredBooks = null;
        log.info(" Filter bookd request" + filterBooksRequest);

        if (filterBooksRequest.getAuthor()!=null && filterBooksRequest.getPublishedYear()!=null){
             filteredBooks = bookRepository.getFilteredBooks(filterBooksRequest.getAuthor(), filterBooksRequest.getPublishedYear());
        } else if (filterBooksRequest.getAuthor()!=null && filterBooksRequest.getPublishedYear()==null) {
            filteredBooks = bookRepository.getFilteredBooksByAuthor(filterBooksRequest.getAuthor());
        } else if (filterBooksRequest.getAuthor()==null && filterBooksRequest.getPublishedYear()!=null) {
            filteredBooks = bookRepository.getFilteredBooksByYear(filterBooksRequest.getPublishedYear());
        }else{
            log.info("");
        }
        return ResponseEntity.ok(filteredBooks);
//        return new ResponseBean(MessageVarList.RSP_SUCCESS,StatusVarList.FILTERED_BOOKS,filteredBooks);
    }
}
