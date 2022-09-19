package com.qa.demo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.persistence.domain.Books;
import com.qa.dto.BooksDTO;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BooksControllerIntegrationTest {

    @Autowired
    private MockMvc mockMVC;

    @Autowired
    private ObjectMapper mapper;

    private final Books TEST_BOOKS = new Books(null, "Rich Dad poor Dad", 15, 255, "Robert Kiosaki", 97810019);

    private final Books TEST_SAVED_BOOKS = new Books(null,"Rich Dad poor Dad", 15, 255, "Robert Kiosaki", 9780019);

    @Test
    public void testCreate() throws JsonProcessingException, Exception {
        String resultString = this.mockMVC
                .perform(post("/create").contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(TEST_BOOKS)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        BooksDTO result = this.mapper.readValue(resultString, BooksDTO.class);
        assertNotNull(result.getId());
    }


	
    @Test
    public void testReadAll() throws Exception {
        final List<Books> PEOPLE = new ArrayList<>();
        PEOPLE.add(TEST_SAVED_BOOKS);

        final String resultString = this.mockMVC
                .perform(request(HttpMethod.GET, "/getAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<BooksDTO> results = Arrays.asList(mapper.readValue(resultString, BooksDTO[].class));
        assertThat(results).hasAtLeastOneElementOfType(BooksDTO.class);
      
    }

    @Test
    public void testUpdate() throws Exception {
        final Books newBooks = new Books(null, "Let us c", 6, 150, "BalaGuru", 98700146);
        String resultString = this.mockMVC
                .perform(put("/update/1").contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(newBooks)))
                .andExpect(status().isOk()).andReturn().getRequest().getContentAsString();

        BooksDTO result = this.mapper.readValue(resultString, BooksDTO.class);
        assertThat(result.getAuthor()).isEqualTo(newBooks.getAuthor());
        assertThat(result.getName()).isEqualTo(newBooks.getName());
    }

    @Test
    public void testDelete() throws Exception {
        this.mockMVC.perform(delete("/delete/1")).andExpect(status().isOk());
    }
}

