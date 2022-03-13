package com.getir.readingIsGood.book.integration.repository;

import com.getir.readingIsGood.book.dto.BookDto;
import com.getir.readingIsGood.book.dto.ResponseBookDto;
import com.getir.readingIsGood.book.model.Book;
import com.getir.readingIsGood.book.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    Book book;
    BookDto bookdto;
    ResponseBookDto responseBookDto;

    @BeforeEach
    public void setUp(){
        book = new Book("","Yeraltindan Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
    }

    @AfterEach
    void tearDown() {
        Optional<Book> addedBook = bookRepository.findByName(book.getName());
        bookRepository.deleteById(addedBook.get().getId());
    }

    @Test
    public void testFindByName() {
        //Given
        bookRepository.save(book);
        Optional<Book> addedBookOptional = bookRepository.findByName(book.getName());
        Book addedBook = addedBookOptional.get();
        //Then
        assertEquals(book.getName(), addedBook.getName());
        assertEquals(book.getAuthor(), addedBook.getAuthor());
        assertEquals(book.getStock(), addedBook.getStock());
        assertEquals(book.getPrice(), addedBook.getPrice());
        assertEquals(book.getDescription(), addedBook.getDescription());
    }
}
