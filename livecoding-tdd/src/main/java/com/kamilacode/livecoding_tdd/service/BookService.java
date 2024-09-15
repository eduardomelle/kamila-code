package com.kamilacode.livecoding_tdd.service;

import com.kamilacode.livecoding_tdd.domain.Book;
import com.kamilacode.livecoding_tdd.exception.BookNotFoundException;
import com.kamilacode.livecoding_tdd.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Book> getBookById(Long id) throws BookNotFoundException {
        Optional<Book> optionalBook = this.bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException(id);
        }

        return optionalBook;
    }

    public ResponseEntity<Book> updateBookById(Long id, Book book) {
        return this.bookRepository.findById(id).map(
                bookToUpdate -> {
                    bookToUpdate.setName(book.getName());
                    bookToUpdate.setCategory(book.getCategory());
                    bookToUpdate.setStatus(book.getStatus());

                    Book updated = this.bookRepository.save(bookToUpdate);

                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> deleteBookById(Long id) {
        return this.bookRepository.findById(id).map(bookToDelete -> {
            this.bookRepository.delete(bookToDelete);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    public List<Book> listBooksThatStartsWith(String partialName) {
        return this.bookRepository.findByNameStartingWith(partialName);
    }

}
