package com.Assessment.Library_management.dto;

import com.Assessment.Library_management.entity.BorrowingRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecordDTO {

    private String bookTitle;
    private String author;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;


    // Mapping from BorrowingRecord entity
    public static BorrowingRecordDTO fromEntity(BorrowingRecord record) {
        return new BorrowingRecordDTO(
                record.getBook().getTitle(),
                record.getBook().getAuthor(),
                record.getBorrowDate(),
                record.getReturnDate()
        );
    }
}
