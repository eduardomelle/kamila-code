package com.kamilacode.livecoding_tdd.service;

import com.kamilacode.livecoding_tdd.domain.Book;
import com.kamilacode.livecoding_tdd.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }

    public List<Book> listAllBooks() {
        return this.bookRepository.findAll();
    }

}
