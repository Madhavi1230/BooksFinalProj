package com.qa.demo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.persistence.domain.Books;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:books-schema.sql",
"classpath:books-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class BooksControllerIntegrationTest {

    @Autowired
    private MockMvc mockMVC;

    @Autowired
    private ObjectMapper mapper;

    private final Books TEST_BOOKS = new Books(null, "Rich Dad poor Dad", 15, 255, "Robert Kiosaki", 97810019);

    private final Books TEST_SAVED_BOOKS = new Books(1L,"Rich Dad poor Dad", 15, 255, "Robert Kiosaki", 9780019);

    @Test
    public void testCreate() throws JsonProcessingException, Exception {
        String resultString = this.mockMVC
                .perform(post("/books/create").contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(TEST_BOOKS)))
                .andExpect(status().isCreated()).andReturn().getRequest().getContentAsString();

        Books result = this.mapper.readValue(resultString, Books.class);
        assertThat(result).isEqualTo(TEST_BOOKS);
    }

    @Test
    public void testReadOne() throws Exception {
        this.mockMVC.perform(get("/books/read/1")).andExpect(status().isOk())
                .andExpect(content().json(this.mapper.writeValueAsString(TEST_SAVED_BOOKS)));

    }

    @Test
    public void testReadAll() throws Exception {
        final List<Books> PEOPLE = new ArrayList<>();
        PEOPLE.add(TEST_SAVED_BOOKS);

        final String resultString = this.mockMVC
                .perform(request(HttpMethod.GET, "/books/readAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<Books> results = Arrays.asList(mapper.readValue(resultString, Books[].class));
        assertThat(results).contains(this.TEST_BOOKS).hasSize(3);
    }

    @Test
    public void testUpdate() throws Exception {
        final Books newBooks = new Books(null, "Let us c", 6, 150, "BalaGuru", 98700146);
        String resultString = this.mockMVC
                .perform(put("/books/update/3").contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(newBooks)))
                .andExpect(status().isAccepted()).andReturn().getRequest().getContentAsString();

        Books result = this.mapper.readValue(resultString, Books.class);
        assertThat(result).isEqualTo(newBooks);
    }

    @Test
    public void testDelete() throws Exception {
        this.mockMVC.perform(delete("/books/delete/2")).andExpect(status().isNoContent());
    }
}

