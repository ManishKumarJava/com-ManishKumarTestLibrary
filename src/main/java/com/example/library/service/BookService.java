package com.example.library.service;

import com.example.library.model.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getBooks();

    BookDto updateBook(BookDto book);

    BookDto addBook(BookDto book);

    BookDto getBook(String isbn);

    boolean deleteBook(String isbn);
}


