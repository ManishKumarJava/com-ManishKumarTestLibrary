package com.example.library;


import com.example.library.LibraryApplication;
import com.example.library.model.dto.BookDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class LibraryTest {

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Value("${myProperty.test.app:0}")
    private String myPropertytestapp;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void testCreateBook() throws Exception {
        String uri = "/library/createBook";
        BookDto bookDto = new BookDto(91, "isbn_121212", "bookName_1", "author_1");
        String inputJson = mapToJson(bookDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        BookDto resultBookDto = mapFromJson(content, BookDto.class);
        assertEquals("bookName_1", resultBookDto.getBookName());
        System.out.println(" testCreateBook done");
    }
    
    @Test
    public void testGetAllBooks() throws Exception {
        String uri = "/library/allBooks"; // http://localhost:8088/library/allBooks
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        BookDto[] resultBookDtos = mapFromJson(content, BookDto[].class);
        assertEquals("bookName_1", resultBookDtos[0].getBookName());
        System.out.println(" get all books done");
    }

//    @Test
//    @Ignore
//    public void testUpdateBook2Positive() throws Exception {
//        String uri = "/library/updateBook2";
//        BookDto bookDto = new BookDto(91, "isbn_121212", "bookName_1_updated", "author_1_updated");
//        String inputJson = mapToJson(bookDto);
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        BookDto resultBookDto = mapFromJson(content, BookDto.class);
//        assertEquals("bookName_1_updated", resultBookDto.getBookName());
//        System.out.println(" testCreateBook2Positive done");
//    }    
    //TODO need to work on this.

    @Test
    public void testUpdateBook2Negative() throws Exception {
        String uri = "/library/updateBook2";
        BookDto bookDto = new BookDto(91, "isbn_INVALID", "bookName_1_updated", "author_1_updated");
        String inputJson = mapToJson(bookDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(500, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Exception while updating book record", content);
        System.out.println(" testCreateBook2Positive done");
    }      
    

    
}
