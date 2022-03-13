package com.getir.readingIsGood.book.service;

import com.getir.readingIsGood.book.dto.BookDto;
import com.getir.readingIsGood.book.dto.ResponseBookDto;
import com.getir.readingIsGood.book.model.Book;
import com.getir.readingIsGood.book.repository.BookRepository;
import com.getir.readingIsGood.common.exception.ExceptionCode;
import com.getir.readingIsGood.common.exception.ReadingIsGoodException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseBookDto addNewBook(BookDto bookDto) {
        Optional<Book> book = bookRepository.findByName(bookDto.getName());
        if(book.isPresent()){
            throw new ReadingIsGoodException(book.toString(), ExceptionCode.BOOK_ALREADY_EXIST);
        }
        return saveBook(bookDto);
    }

    private ResponseBookDto saveBook(BookDto bookDto){
        Book newBook= modelMapper.map(bookDto, Book.class);
        bookRepository.save(newBook);
        return modelMapper.map(newBook, ResponseBookDto.class);
    }

    @Override
    @Transactional
    public ResponseBookDto updateBookStock(String id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->new ReadingIsGoodException(id, ExceptionCode.BOOK_NOT_FOUND));
        if(bookDto.getStock() < 0)
            throw new ReadingIsGoodException(id, ExceptionCode.STOCK_MUST_BE_GREATER_THAN_ZERO);
        book.setStock(bookDto.getStock());
        bookRepository.save(book);
        return modelMapper.map(book, ResponseBookDto.class);
    }

    @Override
    public ResponseBookDto getBookInfoByName(String name) {
        Book book = bookRepository.findByName(name).orElseThrow(() ->new ReadingIsGoodException(name, ExceptionCode.BOOK_NOT_FOUND));
        return modelMapper.map(book, ResponseBookDto.class);
    }
}
