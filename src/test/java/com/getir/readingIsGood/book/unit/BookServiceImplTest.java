package com.getir.readingIsGood.book.unit;

import com.getir.readingIsGood.book.dto.BookDto;
import com.getir.readingIsGood.book.dto.ResponseBookDto;
import com.getir.readingIsGood.book.model.Book;
import com.getir.readingIsGood.book.repository.BookRepository;
import com.getir.readingIsGood.book.service.BookService;
import com.getir.readingIsGood.book.service.BookServiceImpl;
import com.getir.readingIsGood.common.exception.ExceptionCode;
import com.getir.readingIsGood.common.exception.ReadingIsGoodException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceImplTest {

    private BookService bookService;
    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    Book book;
    BookDto bookDto;
    ResponseBookDto responseBookDto;

    @BeforeEach
    public void setUp(){
         bookRepository = mock(BookRepository.class);
         modelMapper = new ModelMapper();
         bookService = new BookServiceImpl(bookRepository, modelMapper);
         book = new Book("id","Yeraltindan Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
         bookDto = new BookDto("Yeraltindan Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
         responseBookDto = new ResponseBookDto(null,"Yeraltindan Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
    }

    @Test
    void testAddNewBook_whenBookIsNotExists_shouldReturnBook() {
        //When
        Mockito.when(bookRepository.findByName(bookDto.getName())).thenReturn(Optional.empty());
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        //then
        assertEquals(responseBookDto, bookService.addNewBook(bookDto));
        Mockito.verify(bookRepository).findByName(bookDto.getName());
    }

    @Test
    void testAddNewBook_whenBookIsAlreadyExists_shouldThrowBookAlreadyExistsException() {
        //when
        Mockito.when(bookRepository.findByName(bookDto.getName())).thenReturn(Optional.of(book));
        //then
        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> bookService.addNewBook(bookDto));
        assertEquals(ExceptionCode.BOOK_ALREADY_EXIST, exception.getExceptionCode());
        Mockito.verify(bookRepository).findByName(book.getName());
    }

    @Test
    void testUpdateBookStock_whenBookIsExistsAndSetStockNumberBiggerThanZero_shouldReturnBook() {
        //when
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        //then
        assertEquals(responseBookDto.getStock(), bookService.updateBookStock(book.getId(), bookDto).getStock());
        Mockito.verify(bookRepository).findById(book.getId());
    }

    @Test
    void testUpdateBookStock_whenBookIsExistsAndSetStockNumberLessThanZero_shouldReturnBook() {
          //when
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        //then
        assertEquals(responseBookDto.getStock(), bookService.updateBookStock(book.getId(), bookDto).getStock());
        Mockito.verify(bookRepository).findById(book.getId());
    }

    @Test
    void testUpdateBookStock_whenBookIsNotExists_shouldThrowStockMustBeGreaterThanZeroException() {
        bookDto.setStock(-4);
        //when
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        //then
        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> bookService.updateBookStock(book.getId(), bookDto));
        assertEquals(ExceptionCode.STOCK_MUST_BE_GREATER_THAN_ZERO, exception.getExceptionCode());
        Mockito.verify(bookRepository).findById(book.getId());
        bookDto.setStock(3);

    }

    @Test
    void testGetBookInfoByName_whenBookIsExists_shouldReturnBook() {
        //when
        Mockito.when(bookRepository.findByName(bookDto.getName())).thenReturn(Optional.of(book));
        responseBookDto.setId("id");
        //then
        assertEquals(responseBookDto, bookService.getBookInfoByName(bookDto.getName()));
        Mockito.verify(bookRepository).findByName(book.getName());
    }

    @Test
    void testGetBookInfoByName_whenBookIsNotExists_shouldThrowBookNotFoundException() {
        //when
        Mockito.when(bookRepository.findByName(bookDto.getName())).thenReturn(Optional.empty());

        //then
        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> bookService.getBookInfoByName(bookDto.getName()));
        assertEquals(ExceptionCode.BOOK_NOT_FOUND, exception.getExceptionCode());
        Mockito.verify(bookRepository).findByName(book.getName());
    }
}