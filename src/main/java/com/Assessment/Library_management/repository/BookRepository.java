package com.Assessment.Library_management.repository;

import com.Assessment.Library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    // Already have
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByPublishedYear(Integer year);

    Optional<List<Book>> findByAvailableCopiesGreaterThan(Integer count);

    @Query(value = "select  n from Book n where n.author=:author and n.publishedYear=:publishYear")
    List<Book> getFilteredBooks(@Param("author")String author, @Param("publishYear")Integer year);

    @Query(value = "select  n from Book n where n.author=:author ")
    List<Book> getFilteredBooksByAuthor(@Param("author")String author);

    @Query(value = "select  n from Book n where  n.publishedYear=:publishYear ")
    List<Book> getFilteredBooksByYear( @Param("publishYear")Integer year);



    Optional<Book> findByBookId(String bookId);
}
