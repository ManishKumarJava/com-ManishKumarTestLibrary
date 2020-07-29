package com.example.library;

import com.example.library.dao.repo.BookRepository;
import com.example.library.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;

    @Test
    public void injectedComponentsAreNotNull(){

        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(bookRepository).isNotNull();
    }

    @Test
    public void should_find_no_of_books_if_repository_is_empty() {
        Iterable<Book> tutorials = bookRepository.findAll();
        assertThat(tutorials).isEmpty();
    }

    @Test
    public void should_store_a_book() {
        Book book = bookRepository.save(new Book("b2c2","Java from Beginning", "XYZ", 3));

        assertThat(book).hasFieldOrPropertyWithValue("isbn", "b2c2");
        assertThat(book).hasFieldOrPropertyWithValue("bookName", "Java from Beginning");
        assertThat(book).hasFieldOrPropertyWithValue("author", "XYZ");
        assertThat(book).hasFieldOrPropertyWithValue("numbers", 3);
    }

    @Test
    public void should_find_all_books() {
        Book book = new Book("b2c2","Java from Beginning", "XYZ", 3);
        bookRepository.save(book);

        Book book1 = new Book("b2c2","Java from Beginning", "XYZ", 3);
        entityManager.persist(book1);

        Book book2 = new Book("b2c2","Java from Beginning", "XYZ", 3);
        bookRepository.save(book2);

        Iterable<Book> books = bookRepository.findAll();

        assertThat(books).hasSize(3).contains(book, book1, book2);
    }
}
