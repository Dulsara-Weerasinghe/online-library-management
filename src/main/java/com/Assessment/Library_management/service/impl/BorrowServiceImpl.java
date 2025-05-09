package com.Assessment.Library_management.service.impl;

import com.Assessment.Library_management.Bean.LoginResponseBean;
import com.Assessment.Library_management.controller.BookController;
import com.Assessment.Library_management.dto.BorrowBookRequest;
import com.Assessment.Library_management.dto.BorrowingRecordDTO;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.dto.ReturnBooksRequest;
import com.Assessment.Library_management.entity.Book;
import com.Assessment.Library_management.entity.BorrowingRecord;
import com.Assessment.Library_management.entity.User;
import com.Assessment.Library_management.exception.DataNotFounException;
import com.Assessment.Library_management.repository.BookRepository;
import com.Assessment.Library_management.repository.BorrowedRecordRepository;
import com.Assessment.Library_management.repository.UserRepository;
import com.Assessment.Library_management.service.BorrowService;
import com.Assessment.Library_management.util.MessageVarList;
import com.Assessment.Library_management.util.StatusVarList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowedRecordRepository borrowingRecordRepository;

    private static final Logger log = LoggerFactory.getLogger(BorrowServiceImpl.class);


    @Override
    @Transactional
    public ResponseEntity<?> borrowBooks(BorrowBookRequest borrowBookRequest) {

        User user = userRepository.findByuserId(borrowBookRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findByBookId(borrowBookRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            return ResponseEntity.ok(StatusVarList.NO_available_books);
//            return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, StatusVarList.NO_available_books, null);
        }


        BorrowingRecord record = new BorrowingRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(LocalDateTime.now());
        borrowingRecordRepository.save(record);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

//        return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.SUCCESSFULLY_BORROWED, null);
        return ResponseEntity.ok(StatusVarList.SUCCESSFULLY_BORROWED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> returnBooks(ReturnBooksRequest returnBooksRequest) {

        // Find borrow record where returnDate is null (That means not yet returned)
        BorrowingRecord record = borrowingRecordRepository
                .findFirstByuser_userIdAndbook_bookIdAndReturnDateIsNull(returnBooksRequest.getUserId(), returnBooksRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("No borrowed book found to return"));

        record.setReturnDate(LocalDateTime.now());
        borrowingRecordRepository.save(record);

        Book book = bookRepository.findByBookId(returnBooksRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        return ResponseEntity.ok(StatusVarList.BOOK_RETURNED_SUCCESSFULLY);
//        return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.BOOK_RETURNED_SUCCESSFULLY, null);

    }

    @Override
    public ResponseEntity<?> borrowHistory(String userId) throws DataNotFounException {
        List<BorrowingRecordDTO> list = new ArrayList<>();
        List<BorrowingRecord> records = borrowingRecordRepository.findByuser_userId(userId).orElseThrow(() -> new DataNotFounException("Borrow history not found for the user"));
        records.forEach(borrowingRecord -> {
            BorrowingRecordDTO borrowingRecordDTO = new BorrowingRecordDTO();
            borrowingRecordDTO.setBorrowDate(borrowingRecord.getBorrowDate());
            borrowingRecordDTO.setAuthor(borrowingRecord.getBook().getAuthor());
            borrowingRecordDTO.setBookTitle(borrowingRecord.getBook().getTitle());
            borrowingRecordDTO.getReturnDate(borrowingRecord.getReturnDate());
            list.add(borrowingRecordDTO);
        });

//        List<BorrowingRecordDTO> borrowedHistory = records.stream().map(BorrowingRecordDTO::fromEntity).collect(Collectors.toList());
        if (!list.isEmpty()) {
            log.debug("Borrowed history availble");

            return ResponseEntity.ok(list);
//            return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.BORROWED_HISTORY_AVAILABLE, borrowedHistory);
        } else {
            log.debug("Borrowed history not availble");
            return ResponseEntity.ok(StatusVarList.BORROWED_HISTORY_NOT_AVAILABLE);
            //      return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, StatusVarList.BORROWED_HISTORY_NOT_AVAILABLE, borrowedHistory);
        }
    }
}
