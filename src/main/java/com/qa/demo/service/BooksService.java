package com.qa.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
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
    Books saved =  this.repo.save(books);
    return this.mapToDTO(saved);
}

//Read
public List<BooksDTO> getAllBooks() {
	   return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
 }


//Update
public BooksDTO updateBooks(Long id, Books newBooks) {
    Optional<Books> existingOptional = this.repo.findById(id);
    Books existing = existingOptional.get();

    existing.setName(newBooks.getName());
    existing.setAuthor(newBooks.getAuthor());

    Books updated =  this.repo.save(existing);
    return this.mapToDTO(updated);

}

//Delete

  public boolean removeBooks(Long id) {
// Remove Books and return it
//   return this.books.remove(id);
  this.repo.deleteById(id);
  boolean exists = this.repo.existsById(id);
// returns true if entity no longer exists
  return !exists;
}


}
