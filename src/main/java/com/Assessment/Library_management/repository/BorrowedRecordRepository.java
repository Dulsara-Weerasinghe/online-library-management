package com.Assessment.Library_management.repository;

import com.Assessment.Library_management.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedRecordRepository extends JpaRepository<BorrowingRecord,Long> {


    Optional<BorrowingRecord> findFirstByUserIdAndBookIdAndReturnDateIsNull(String userId, String bookId);


    Optional<List<BorrowingRecord>> findByUser(String userId);
}
