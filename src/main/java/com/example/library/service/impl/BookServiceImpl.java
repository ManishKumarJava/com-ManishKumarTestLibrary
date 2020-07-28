package com.example.library.service.impl;

import com.example.library.model.dto.BookDto;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private com.example.library.dao.BookDao bookDao;

    public List<BookDto> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public BookDto updateBook(BookDto book) {
        return bookDao.updateBook(book);
    }

    @Override
    public BookDto addBook(BookDto book) {
        return bookDao.addBook(book);
    }

    @Override
    public BookDto getBook(String isbn) {
        return bookDao.getBook(isbn);
    }

    public boolean deleteBook(String isbn){
        return bookDao.deleteBook(isbn);
    }

}
