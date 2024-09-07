package com.kamilacode.livecoding_tdd.repository;

import com.kamilacode.livecoding_tdd.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
