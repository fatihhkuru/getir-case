package com.getir.readingIsGood.book.Integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingIsGood.book.controller.BookController;
import com.getir.readingIsGood.book.dto.BookDto;
import com.getir.readingIsGood.book.dto.ResponseBookDto;
import com.getir.readingIsGood.book.model.Book;
import com.getir.readingIsGood.book.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ModelMapper modelMapper;

    Book book;
    BookDto bookdto;
    ResponseBookDto responseBookDto;
    @BeforeEach
    void setUp() {
        book = new Book("622d8c5ed123ff3726a90b18","Yeraltindan Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        bookdto = new BookDto("Yeraltindan Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
        responseBookDto = new ResponseBookDto("622d8c5ed123ff3726a90b18","Yeraltindan Notlar", "Dostoyevski", 3, BigDecimal.valueOf(10.0),"description");
    }

    @MockBean
    private BookService bookService;


    @Test
    void testAddNewBook() throws Exception {
        //When
        when(bookService.addNewBook(bookdto)).thenReturn(responseBookDto);
        ObjectMapper mapper = new ObjectMapper();
        String responseBookDtoJson = mapper.writeValueAsString(responseBookDto);
        String bookDtoJson = mapper.writeValueAsString(bookdto);
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/book/addNewBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson);
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(responseBookDtoJson)));
        verify(bookService).addNewBook(bookdto);
    }

    @Test
    void testAddNewBook_whenNameIsBlank_shouldThrowMethodArgumentNotValidException() throws Exception {
        //When
        when(bookService.addNewBook(bookdto)).thenReturn(responseBookDto);
        ObjectMapper mapper = new ObjectMapper();
        String responseBookDtoJson = mapper.writeValueAsString(responseBookDto);
        bookdto.setName("");
        String bookDtoJson = mapper.writeValueAsString(bookdto);
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/book/addNewBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson);
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("name may not be empty")));
    }

    @Test
    void testAddNewBook_whenAuthorIsBlank_shouldThrowMethodArgumentNotValidException() throws Exception {
        //When
        when(bookService.addNewBook(bookdto)).thenReturn(responseBookDto);
        ObjectMapper mapper = new ObjectMapper();
        String responseBookDtoJson = mapper.writeValueAsString(responseBookDto);
        bookdto.setAuthor("");
        String bookDtoJson = mapper.writeValueAsString(bookdto);
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/book/addNewBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson);
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("author may not be empty")));
    }

    @Test
    void testAddNewBook_whenStockIsLessThanZero_shouldThrowMethodArgumentNotValidException() throws Exception {
        //When
        when(bookService.addNewBook(bookdto)).thenReturn(responseBookDto);
        ObjectMapper mapper = new ObjectMapper();
        String responseBookDtoJson = mapper.writeValueAsString(responseBookDto);
        bookdto.setStock(-2);
        String bookDtoJson = mapper.writeValueAsString(bookdto);
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/book/addNewBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson);
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("stock must be greater than or equal to 0")));
    }

    @Test
    void testAddNewBook_whenPriceIsLessThanZero_shouldThrowMethodArgumentNotValidException() throws Exception {
        //When
        when(bookService.addNewBook(bookdto)).thenReturn(responseBookDto);
        ObjectMapper mapper = new ObjectMapper();
        String responseBookDtoJson = mapper.writeValueAsString(responseBookDto);
        bookdto.setPrice(BigDecimal.valueOf(-2));
        String bookDtoJson = mapper.writeValueAsString(bookdto);
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/book/addNewBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson);
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("price must be greater than or equal to 0")));
    }

    @Test
    void testUpdateBookStock() throws Exception {
        //When
        bookdto.setStock(4);
        responseBookDto.setStock(4);
        when(bookService.updateBookStock(book.getId(), bookdto)).thenReturn(responseBookDto);
        ObjectMapper mapper = new ObjectMapper();
        String bookDtoJson = mapper.writeValueAsString(bookdto);
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/book/updateStock/"+ book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson);
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock", is(responseBookDto.getStock())));
        verify(bookService).updateBookStock(book.getId(),bookdto);
    }

    @Test
    void testGetBookInfo() throws Exception {
        //When
        when(bookService.getBookInfoByName(responseBookDto.getName())).thenReturn(responseBookDto);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(responseBookDto);
        //Then
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/book/"+responseBookDto.getName());
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(jsonString)));
        verify(bookService).getBookInfoByName(responseBookDto.getName());
    }



}