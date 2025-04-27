package com.Assessment.Library_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "BORROWING_RECORD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book", referencedColumnName = "BOOK_ID")
    private Book book;

    @Column(name = "BORROWED_DATE")
    private LocalDateTime borrowDate;

    @Column(name = "RETURNED_DATE")
    private LocalDateTime returnDate;
}
