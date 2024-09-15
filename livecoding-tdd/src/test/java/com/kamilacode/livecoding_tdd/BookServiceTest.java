package com.kamilacode.livecoding_tdd;

import com.kamilacode.livecoding_tdd.domain.Book;
import com.kamilacode.livecoding_tdd.domain.Category;
import com.kamilacode.livecoding_tdd.domain.Status;
import com.kamilacode.livecoding_tdd.exception.BookNotFoundException;
import com.kamilacode.livecoding_tdd.repository.BookRepository;
import com.kamilacode.livecoding_tdd.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Success - Should save book with success")
    void shouldSaveBookSuccess() {
        when(this.bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(BookFactory.createBook());
        Book created = this.bookService.createBook(BookFactory.createBook());
        assertThat(created.getName()).isSameAs(BookFactory.createBook().getName());
        assertNotNull(created.getId());
        assertEquals(1L, created.getId());
    }

    @Test
    @DisplayName("Success - Should return the list of books with success")
    void shouldReturnListOfBooksWithSuccess() {
        when(this.bookRepository.findAll()).thenReturn(List.of(BookFactory.createBook()));
        List<Book> books = this.bookService.listAllBooks();
        assertEquals(1, books.size());
    }

    @Test
    @DisplayName("Success - Should find a book by id with success")
    void shouldFindBookByIdWithSuccess() throws BookNotFoundException {
        Book book = new Book();
        book.setId(1L);
        book.setName("cracking the code interview");
        book.setCategory(Category.PROGRAMMING);
        book.setStatus(Status.NOT_STARTED);

        when(this.bookRepository.findById(book.getId())).thenReturn(Optional.of(BookFactory.createBook()));

        Optional<Book> expected = this.bookService.getBookById(book.getId());
        assertThat(expected.get().getId()).isEqualTo(book.getId());
        assertThat(expected.get().getName()).isEqualTo(book.getName());
        assertThat(expected.get().getCategory()).isEqualTo(book.getCategory());
        assertThat(expected.get().getStatus()).isEqualTo(book.getStatus());
        assertDoesNotThrow(() -> {
            this.bookService.getBookById(book.getId());
        });
    }

    @Test
    @DisplayName("Error - Should throw exception when try to find book by id")
    void shouldThrowExceptionWhenTryToFindBookById() {
        when(this.bookRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            this.bookService.getBookById(2L);
        });

        assertEquals("Book with id " + 2L + " not found in database", exception.getMessage());
    }

    @Test
    @DisplayName("Success - Should delete with success")
    void shouldDeleteWithSuccess() {
        when(this.bookRepository.findById(BookFactory.createBook().getId()))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> expected = this.bookService.deleteBookById(BookFactory.createBook().getId());

        assertThat(expected.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Success - Should find a book by partial name")
    void shouldFindBookByPartialNameWithSuccess() {
        Book book = new Book();
        book.setId(1L);
        book.setName("essencialismo");
        book.setCategory(Category.SOFT_SKILLS);
        book.setStatus(Status.IN_PROGRESS);

        when(this.bookRepository.findByNameStartingWith("ess")).thenReturn(List.of(book));

        List<Book> expected = this.bookService.listBooksThatStartsWith("ess");
        assertThat(expected.get(0).getId()).isEqualTo(book.getId());
        assertThat(expected.get(0).getName()).isEqualTo(book.getName());
        assertThat(expected.get(0).getCategory()).isEqualTo(book.getCategory());
        assertThat(expected.get(0).getStatus()).isEqualTo(book.getStatus());
        assertThat(expected.get(0).getName()).isNotEqualTo(BookFactory.createBook().getName());
    }

}
