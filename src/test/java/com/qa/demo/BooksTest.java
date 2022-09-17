package com.qa.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.qa.demo.persistence.domain.Books;
import com.qa.demo.repo.BooksRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
final public class BooksTest {


	@Autowired
	private BooksRepo repo;
	
//Create	
		@Test
	public void testCreateBooks() {
		Books books = new Books(null, "Moonlight", 14, 155, "Victoria", 98706732);
		Books savedBooks=repo.save(books);
		
			}
	
//Find by Name	
	@Test
	public void testFindBooksByName() {
		String name="Moonlight";
		Books books=repo.findByName(name);
		
	}
	
	@Test
//update Books	
	public void testUpdateBooks() {
		String booksName= "Twilight";
		Books books=new Books(null, booksName, 11, 175, "StephenyMayor", 98765023);
		repo. save(books);
		repo.findByName(booksName);
		
			}
	
	@Test
	public void testDeleteProduct() {
		int id=2;
		boolean present1=repo.findById((long)id).isPresent();
		repo.deleteById((long)id);
		boolean notExistAfterDelete=repo.findById((long)id).isPresent();
	}
}
