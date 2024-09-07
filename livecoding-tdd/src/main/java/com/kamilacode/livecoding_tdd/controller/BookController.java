package com.kamilacode.livecoding_tdd.controller;

import com.kamilacode.livecoding_tdd.domain.Book;
import com.kamilacode.livecoding_tdd.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return this.bookService.createBook(book);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> listAllBooks() {
        return this.bookService.listAllBooks();
    }

}
