package com.qa.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.demo.persistence.domain.Books;

@Repository
public interface BooksRepo extends JpaRepository<Books, Long>  {
    public Books findByName(String name);
}
