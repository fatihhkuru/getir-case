package com.getir.readingIsGood.book.service;

import com.getir.readingIsGood.book.dto.BookDto;
import com.getir.readingIsGood.book.dto.ResponseBookDto;
import org.springframework.transaction.annotation.Transactional;

public interface BookService {

    ResponseBookDto addNewBook(BookDto bookDto);
    @Transactional
    ResponseBookDto updateBookStock(String id, BookDto bookDto);
    //TODO getALl books
    ResponseBookDto getBookInfoByName(String name);
    //TODO price da değiştirilebilir olabilir
}
