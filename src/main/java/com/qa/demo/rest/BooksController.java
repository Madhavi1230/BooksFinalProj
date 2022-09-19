package com.qa.demo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.demo.persistence.domain.Books;
import com.qa.demo.service.BooksService;
import com.qa.dto.BooksDTO;

@RestController
public class BooksController {

	private BooksService service;

    public BooksController(BooksService service) {
        super();
        this.service = service;
    }

    @GetMapping("/test")
    public String test() {
        return "Hello, World!";
 }
    
    // Create
    @PostMapping("/create")
    public BooksDTO addBooks(@RequestBody Books books) {
        return this.service.addBooks(books);
    }
    
    // READ
    @GetMapping("/getAll")
    public List<BooksDTO> getAllBooks() {
    return this.service.getAllBooks();
    }
    
  //Update     
    @PutMapping("/update/{id}")
    public BooksDTO updateBooks(@PathVariable("id") Long id, @RequestBody Books books) {
    	return this.service.updateBooks(id, books);
    }
    
  //Delete	     
    @DeleteMapping("/delete/{id}")
    public boolean removeBooks(@PathVariable Long id) {
        // Remove Books and return it
        return this.service.removeBooks(id);
    }
    
	/*
	 * @GetMapping("/getOne/{bookid}") private Books
	 * getBooks(@PathVariable("bookid") int bookid) { return
	 * this.service.getBooksById(bookid); }
	 */

  
}
