package com.getir.readingIsGood.book.controller;

import com.getir.readingIsGood.book.dto.BookDto;
import com.getir.readingIsGood.book.dto.ResponseBookDto;
import com.getir.readingIsGood.book.model.Book;
import com.getir.readingIsGood.book.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RequestMapping("/api/book")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/addNewBook")
    public ResponseEntity<ResponseBookDto> addNewBook(@Valid @RequestBody BookDto bookDto){
        ResponseBookDto responseBookDto= bookService.addNewBook(bookDto);
        return ResponseEntity.ok(responseBookDto);
    }

    @PutMapping("/updateStock/{id}")
    public ResponseEntity<ResponseBookDto> updateBookStock(@PathVariable("id") String id, @RequestBody BookDto bookDto){
        ResponseBookDto responseBookDto= bookService.updateBookStock(id, bookDto);
        return ResponseEntity.ok(responseBookDto);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ResponseBookDto> getBookInfo(@PathVariable("name") String name){
        ResponseBookDto responseBookDto= bookService.getBookInfoByName(name);
        return ResponseEntity.ok(responseBookDto);
    }

}
