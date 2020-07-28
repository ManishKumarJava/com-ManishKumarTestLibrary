package com.example.library.service;

import com.example.library.dao.BookDao;
import com.example.library.model.dto.BookDto;
import com.example.library.service.impl.BookServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;


public class BookServiceTest {

    public static final String TEST_ISBN = "test-isbn";
    public static final String TEST_BOOK_NAME = "test-bookName";
    public static final String TEST_AUTHOR = "test-author";

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookDao bookDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        List<BookDto> books = new ArrayList<>();
        BookDto bookDto = getTestBookDto();
        books.add(bookDto);

        when(bookDao.getBooks()).thenReturn(books);
        when(bookDao.updateBook(anyObject())).thenReturn(getTestBookDto());
        when(bookDao.addBook(anyObject())).thenReturn(getTestBookDto());
        when(bookDao.deleteBook(anyObject())).thenReturn(true);
    }

    private BookDto getTestBookDto() {
        BookDto bookDto = new BookDto(TEST_ISBN, TEST_BOOK_NAME, TEST_AUTHOR);
        return bookDto;
    }

    @Test
    public void getBooksTest() {
        List<BookDto> booksActual = bookService.getBooks();
        Assert.assertEquals(1, booksActual.size());
        Assert.assertEquals(TEST_ISBN, booksActual.get(0).getIsbn());
        Assert.assertEquals(TEST_BOOK_NAME, booksActual.get(0).getBookName());
        Assert.assertEquals(TEST_AUTHOR, booksActual.get(0).getAuthor());
    }

    @Test
    public void updateBookTest(){
        BookDto bookResult = bookService.updateBook(new BookDto());
        Assert.assertEquals(TEST_ISBN, bookResult.getIsbn());
        Assert.assertEquals(TEST_BOOK_NAME, bookResult.getBookName());
        Assert.assertEquals(TEST_AUTHOR, bookResult.getAuthor());
    }

    @Test
    public void addBookTest(){
        BookDto bookResult = bookService.addBook(new BookDto());
        Assert.assertEquals(TEST_ISBN, bookResult.getIsbn());
        Assert.assertEquals(TEST_BOOK_NAME, bookResult.getBookName());
        Assert.assertEquals(TEST_AUTHOR, bookResult.getAuthor());
    }

    @Test
    public void deleteBookTest(){
        boolean deleteResult = bookService.deleteBook(TEST_ISBN);
        Assert.assertTrue(deleteResult);
    }
}
