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

    @BeforeEach
    public void setUp(){
         bookRepository = mock(BookRepository.class);
         modelMapper = new ModelMapper();
         bookService = new BookServiceImpl(bookRepository, modelMapper);
    }

    @Test
    void testAddNewBook_whenBookIsNotExists_shouldReturnBook() {
        //given
        Book book = new Book("ssfasfkamdk","Yeraltından Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        BookDto bookdto = new BookDto("Yeraltından Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        ResponseBookDto responseBookDto = modelMapper.map(bookdto, ResponseBookDto.class);
        //When
        Mockito.when(bookRepository.findByName(book.getName())).thenReturn(Optional.empty());
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        //then
        assertEquals(responseBookDto, bookService.addNewBook(bookdto));
        Mockito.verify(bookRepository).findByName(book.getName());
        //Mockito.verify(bookRepository).save(book);
    }

    @Test
    void testAddNewBook_whenBookIsAlreadyExists_shouldThrowBookAlreadyExistsException() {
        //given
        Book book = new Book("ssfasfkamdk","Yeraltından Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        BookDto bookdto = new BookDto("Yeraltından Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        //when
        Mockito.when(bookRepository.findByName(book.getName())).thenReturn(Optional.of(book));
        //then
        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> bookService.addNewBook(bookdto));
        assertEquals(ExceptionCode.BOOK_ALREADY_EXIST, exception.getExceptionCode());
        Mockito.verify(bookRepository).findByName(book.getName());
    }

    @Test
    void testUpdateBookStock_whenBookIsExistsAndSetStockNumberBiggerThanZero_shouldReturnBook() {
        //given
        Book book = new Book("ssfasfkamdk","Yeraltından Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        BookDto bookdto = new BookDto("Yeraltından Notlar", "Dostoyevski", 10, BigDecimal.valueOf(10.0),"description");
        ResponseBookDto responseBookDto = modelMapper.map(bookdto, ResponseBookDto.class);
        //when
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        //then
        assertEquals(responseBookDto.getStock(), bookService.updateBookStock(book.getId(), bookdto).getStock());
        Mockito.verify(bookRepository).findById(book.getId());
    }

    @Test
    void testUpdateBookStock_whenBookIsExistsAndSetStockNumberLessThanZero_shouldReturnBook() {
        //given
        Book book = new Book("ssfasfkamdk","Yeraltından Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        BookDto bookdto = new BookDto("Yeraltından Notlar", "Dostoyevski", 10, BigDecimal.valueOf(10.0),"description");
        ResponseBookDto responseBookDto = modelMapper.map(bookdto, ResponseBookDto.class);
        //when
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        //then
        assertEquals(responseBookDto.getStock(), bookService.updateBookStock(book.getId(), bookdto).getStock());
        Mockito.verify(bookRepository).findById(book.getId());
    }

    @Test
    void testUpdateBookStock_whenBookIsNotExists_shouldThrowStockMustBeGreaterThanZeroException() {
        //given
        Book book = new Book("ssfasfkamdk","Yeraltından Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        BookDto bookdto = new BookDto("Yeraltından Notlar", "Dostoyevski", -4, BigDecimal.valueOf(10.0),"description");
        ResponseBookDto responseBookDto = modelMapper.map(bookdto, ResponseBookDto.class);
        //when
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        //then
        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> bookService.updateBookStock(book.getId(), bookdto));
        assertEquals(ExceptionCode.STOCK_MUST_BE_GREATER_THAN_ZERO, exception.getExceptionCode());
        Mockito.verify(bookRepository).findById(book.getId());
    }

    @Test
    void testGetBookInfoByName_whenBookIsExists_shouldReturnBook() {
        //given
        Book book = new Book("ssfasfkamdk","Yeraltından Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        BookDto bookdto = new BookDto("Yeraltından Notlar", "Dostoyevski", -4, BigDecimal.valueOf(10.0),"description");
        ResponseBookDto responseBookDto = modelMapper.map(book, ResponseBookDto.class);
        //when
        Mockito.when(bookRepository.findByName(book.getName())).thenReturn(Optional.of(book));

        //then
        assertEquals(responseBookDto, bookService.getBookInfoByName(bookdto.getName()));
        Mockito.verify(bookRepository).findByName(book.getName());
    }

    @Test
    void testGetBookInfoByName_whenBookIsNotExists_shouldThrowBookNotFoundException() {
        //given
        Book book = new Book("ssfasfkamdk","Yeraltindan Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        BookDto bookdto = new BookDto("Yeraltindan Notlar", "Dostoyevski", -4, BigDecimal.valueOf(10.0),"description");
        ResponseBookDto responseBookDto = modelMapper.map(book, ResponseBookDto.class);
        //when
        Mockito.when(bookRepository.findByName(book.getName())).thenReturn(Optional.empty());

        //then
        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> bookService.getBookInfoByName(bookdto.getName()));
        assertEquals(ExceptionCode.BOOK_NOT_FOUND, exception.getExceptionCode());
        Mockito.verify(bookRepository).findByName(book.getName());
    }
}