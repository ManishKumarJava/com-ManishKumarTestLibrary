package com.example.library.dao;

import com.example.library.model.dto.BookDto;

import java.util.List;

public interface BookDao {

    List<BookDto> getBooks();

    BookDto updateBook(BookDto BookDto);

    BookDto addBook(BookDto BookDto);

    BookDto getBook(String isbn);

    boolean deleteBook(String isbn);

 }
