package com.Assessment.Library_management.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;
    @Override
    public ResponseBean availableBooks() throws DataNotFounException {
        log.debug("Available books");

        List<Book> availableCopies = bookRepository.findByAvailableCopiesGreaterThan(0).orElseThrow(()-> new DataNotFounException("No Available books with available copies"));

       if (!availableCopies.isEmpty()){
           return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.SUCCESS,availableCopies);
       }else{
           return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, StatusVarList.AVAILABLE_BOOKS_NOT_FOUND,new ArrayList<>());
       }

    }

    @Override
    public ResponseBean filterBooks(FilterBooksRequest filterBooksRequest) {
        List<Book> filteredBooks = null;
        log.debug(" Filter bookd request" + filterBooksRequest);

        if (filterBooksRequest.getAuthor()!=null && filterBooksRequest.getPublishedYear()!=null){
             filteredBooks = bookRepository.getFilteredBooks(filterBooksRequest.getAuthor(), filterBooksRequest.getPublishedYear());
        } else if (filterBooksRequest.getAuthor()!=null && filterBooksRequest.getPublishedYear()==null) {
            filteredBooks = bookRepository.getFilteredBooksByAuthor(filterBooksRequest.getAuthor());
        } else if (filterBooksRequest.getAuthor()==null && filterBooksRequest.getPublishedYear()!=null) {
            filteredBooks = bookRepository.getFilteredBooksByYear(filterBooksRequest.getPublishedYear());
        }else{
            log.debug("");
        }
        return new ResponseBean(MessageVarList.RSP_SUCCESS,StatusVarList.FILTERED_BOOKS,filteredBooks);
    }
}
