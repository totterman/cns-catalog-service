package com.totterman.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenBookToCreateAlreadyExistsThenThrows() {
        var isbn = "1234561232";
        var bookToCreate = new Book(isbn, "Title", "Author", 9.90);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage("A book with ISBN " + isbn + " already exists.");
    }

    @Test
    void whenBookToReadDoesNotExistThenThrows() {
        var isbn = "1234561232";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.viewBookDetails(isbn))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("The book with ISBN " + isbn + " was not found.");
    }
}
