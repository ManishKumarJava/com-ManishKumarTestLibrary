package com.example.library.controller;

import com.example.library.exception.LibraryException;
import com.example.library.model.dto.BookDto;
import com.example.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/library")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;
    
    @Autowired
    private RestTemplate restTemplate;

    // TODO Exception handling/  Validations
    
    //create a new book only if it does not exist 
    //Not using PUT for testing
    @PostMapping(value = "/updateBook2", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookDto> updateBook2(@RequestBody BookDto bookParam) {
    	System.out.print("updateBook2");
    	String url = "http://localhost:8088/library/getBook/"; // /getBook/{isbn}
    	ResponseEntity<BookDto> getResponse = restTemplate.getForEntity(url + bookParam.getIsbn(), BookDto.class); 
    	
    	
    	if(bookParam.getIsbn().equals(getResponse.getBody().getIsbn())){
            BookDto bookResult = bookService.updateBook(bookParam);
            return new ResponseEntity<>(bookResult, HttpStatus.OK);
    	}
        
    	//throw exception if we couldn't find this in server
    	throw new LibraryException("Exception while updating book record");
    }    
    

    //create a new book
    @PostMapping(value = "/createBook", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookParam) {
        LOG.info("createBook API called with isbn:{}, bookName:{}, author:{} ", bookParam.getIsbn(), bookParam.getBookName(), bookParam.getAuthor());
        BookDto bookResult = bookService.addBook(bookParam);
        if(bookResult == null){
            //Just showing that we can throw exception like this.
            throw new LibraryException("Exception while creating book record");
        }
        return new ResponseEntity<>(bookResult, HttpStatus.OK);
    }

    //We can remove 'id' from BookDto if we don't want to share it externally.
    @GetMapping(value = "/getBook/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        LOG.info("getBook API called with isbn:{} ", isbn);
        BookDto bookResult = bookService.getBook(isbn);
        return new ResponseEntity<>(bookResult, HttpStatus.OK);
    }

    //Instead of passing a list of values, we can wrap it in a class and share across.
    @RequestMapping(value = "/allBooks", method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getAllBooks() {
        LOG.info("allBooks API called ");
        List<BookDto> bookDetails = bookService.getBooks();
        if(bookDetails.size() < 1){
            throw new LibraryException("No books found");
        }
        return new ResponseEntity<>(bookDetails, HttpStatus.OK);
    }

    //only updating Author, ignoring others, we can modify it to update the whole object.
    @PutMapping(value = "/updateBook")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto book) {
        LOG.info("updateBook API called with isbn:{}, author:{} ", book.getIsbn(), book.getAuthor());
        BookDto bookResult = bookService.updateBook(book);
        return new ResponseEntity<>(bookResult, HttpStatus.OK);
    }

    //Not maintaining relationship with Library for counts.
    @DeleteMapping(value = "/deleteBook/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable("isbn") String isbn) {
        LOG.info("deleteBook API called with isbn:{} ", isbn);
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(isbn, HttpStatus.OK);
    }


 }