package com.qa.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.qa.demo.persistence.domain.Books;
import com.qa.demo.repo.BooksRepo;
import com.qa.dto.BooksDTO;

@Service
public class BooksService {
	private BooksRepo repo;

	private ModelMapper mapper;

	public BooksService(BooksRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

//Convert to BooksDTO
	private BooksDTO mapToDTO(Books books) {
		return this.mapper.map(books, BooksDTO.class);
	}

//Create
	public BooksDTO addBooks(Books books) {
		Books saved = this.repo.save(books);
		BooksDTO b = this.mapToDTO(saved);
		return b;
	}

//Read
	public List<BooksDTO> getAllBooks() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

//Update
	public BooksDTO updateBooks(Long id, Books newBooks) {
		Optional<Books> existingOptional = this.repo.findById(id);

		if (existingOptional.isPresent()) {
			Books existing = existingOptional.get();
			existing.setName(newBooks.getName());
			existing.setAuthor(newBooks.getAuthor());
   		    newBooks = this.repo.save(existing);
		}

		return this.mapToDTO(newBooks);

	}

//Delete

	public boolean removeBooks(Long id) {
// Remove Books and return it
//   return this.books.remove(id);
		boolean exists = false;
		try {
			this.repo.deleteById(id);
			exists = this.repo.existsById(id);
		} catch (EmptyResultDataAccessException ex) {

		}
// returns true if entity no longer exists
		return !exists;
	}

}
