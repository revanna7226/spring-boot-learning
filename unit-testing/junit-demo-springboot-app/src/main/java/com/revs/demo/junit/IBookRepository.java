package com.revs.demo.junit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IBookRepository extends JpaRepository<Book, Integer> {
    @Query("" +
            "SELECT CASE WHEN COUNT(b) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Book b " +
            "WHERE b.name = ?1"
    )
	boolean findBookByName(String bookName);

}
