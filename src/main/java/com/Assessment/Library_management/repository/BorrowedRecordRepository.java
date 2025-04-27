package com.Assessment.Library_management.repository;

import com.Assessment.Library_management.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedRecordRepository extends JpaRepository<BorrowingRecord,Long> {


//    Optional<BorrowingRecord> findFirstByuser_userIdAndbook_bookIdAndReturnDateIsNull(String userId, String bookId);

    @Query(value = "SELECT br FROM BorrowingRecord br " +
            "WHERE br.user.userId = :userId " +
            "AND br.book.bookId = :bookId " +
            "AND br.returnDate IS NULL ORDER BY br.id ASC")
    Optional<BorrowingRecord> findFirstByuser_userIdAndbook_bookIdAndReturnDateIsNull(
            @Param("userId") String userId,
            @Param("bookId") String bookId);

    Optional<List<BorrowingRecord>> findByuser_userId(String userId);
}
