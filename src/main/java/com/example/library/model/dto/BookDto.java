package com.example.library.model.dto;

public class BookDto {

    private int id;

    private String isbn;

    private String bookName;

    private String author;

    public BookDto() {
    }

    public BookDto(int id, String isbn, String bookName, String author) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
    }

    public BookDto(String isbn, String bookName, String author) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
