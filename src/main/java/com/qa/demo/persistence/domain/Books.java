package com.qa.demo.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Books {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Long id; // <- Type of id

			   private String name;
			   private float price;
			   private int pages;
			   private String author;
			   private long isbn;
			   public Books(){
				    super();
			   }
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public int getPages() {
				return pages;
			}
			public void setPages(int pages) {
				this.pages = pages;
			}
			public String getAuthor() {
				return author;
			}
			public void setAuthor(String author) {
				this.author = author;
			}
			public float getPrice() {
				return price;
			}
			public void setPrice(float price) {
				this.price = price;
			}
			public long getIsbn() {
				return isbn;
			}
			public void setIsbn(long isbn) {
				this.isbn = isbn;
			}
			public Books(Long id, String name, float price, int pages, String author, long isbn) {
				super();
				this.id = id;
				this.name = name;
				this.price = price;
				this.pages = pages;
				this.author = author;
				this.isbn = isbn;
			}
			
//	@OneToMany(targetEntity = Authors.class,cascade = CascadeType.ALL)
// @JoinColumn(name="ba_fk",referencedColumnName="id")
//	private List<Authors> authors;	   
 
}
			   

