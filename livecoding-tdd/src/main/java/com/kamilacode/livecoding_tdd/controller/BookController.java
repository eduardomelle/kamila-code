package com.kamilacode.livecoding_tdd.controller;

import com.kamilacode.livecoding_tdd.domain.Book;
import com.kamilacode.livecoding_tdd.exception.BookNotFoundException;
import com.kamilacode.livecoding_tdd.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Book> getBookById(@PathVariable(value = "id") Long id) throws BookNotFoundException {
        return this.bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Book> updateBookById(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        return this.bookService.updateBookById(id, book);
    }

}
