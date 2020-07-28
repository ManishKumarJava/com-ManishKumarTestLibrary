package com.example.library.dao.impl;

import com.example.library.dao.BookDao;
import com.example.library.dao.repo.BookRepository;
import com.example.library.model.Book;
import com.example.library.model.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Not doing exception handling
 *
 */
@Repository
public class BookDaoImpl implements BookDao {


    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public List<BookDto> getBooks() {
        List<Book> bookList = bookRepository.findAll();
        List<BookDto> bookDtoList = bookList.stream().map(b ->  getBookDto(b)).collect(Collectors.toList());
        return bookDtoList;
    }

    @Transactional
    public BookDto getBook(String isbn) {
        Book book = getBookEntity(isbn);
        return getBookDto(book);
    }

    public Book getBookEntity(String isbn){
        List<Book> bookList = bookRepository.findByIsbn(isbn);
        //Assuming all calls are genuine and it will return some thing.
        //TODO Exception Handling
        if(bookList.size() < 1) {
        	return new Book();
        }
        return bookList.get(0);
    }

    //returning dto to client (instead of entity)
    private BookDto getBookDto(Book book){
        return new BookDto(book.getId(), book.getIsbn(),book.getBookName(), book.getAuthor());
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto book) {
        Book bookEntity = getBookEntity(book.getIsbn());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setBookName(book.getBookName());
        //only updating Author, bookname, we can update the whole book object.
        Book bookEntityResult = bookRepository.save(bookEntity);
        return getBookDto(bookEntityResult);
    }

    @Override
    @Transactional
    public BookDto addBook(BookDto bookDto) {
        Book book = new Book(bookDto.getIsbn(), bookDto.getBookName(), bookDto.getAuthor());
        Book bookEntityResult = bookRepository.save(book);
        return getBookDto(bookEntityResult);
    }

    @Override
    @Transactional
    public boolean deleteBook(String isbn) {
        Book bookEntity = getBookEntity(isbn);
        bookRepository.delete(bookEntity);
        return true;
    }

}