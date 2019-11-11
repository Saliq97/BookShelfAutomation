package com.viva.book;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping("/books")
	public List<Book> searchBook() {
		return bookService.getAllBooks();
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/books/search")
	public List<Book> searchBook(@RequestBody Book book) {
		return bookService.searchBook(book.getBookAuthor(),book.getBookGenre(),book.getBookPublisher(),book.getBookTitle());
	}

	@RequestMapping("/books/{id}")
	public Optional<Book> viewBookById(@PathVariable int id) {
		return bookService.getBook(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/books" )
	public void addBook(@RequestBody Book book) {
		bookService.addBook(book);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/books/{id}" )
	public void updateBook(@RequestBody Book book, @PathVariable int id) {
		if(book.getBookAuthor()==null)
			book.setBookAuthor(bookService.getBookById(id).getBookAuthor());
		if(book.getBookGenre()==null)
			book.setBookGenre(bookService.getBookById(id).getBookGenre());
		if(book.getBookPrice()==0)
			book.setBookPrice(bookService.getBookById(id).getBookPrice());
		if(book.getBookPublisher()==null)
			book.setBookPublisher(bookService.getBookById(id).getBookPublisher());
		if(book.getBookQuantity()==0)
			book.setBookQuantity(bookService.getBookById(id).getBookQuantity());
		if(book.getBookTitle()==null)
			book.setBookTitle(bookService.getBookById(id).getBookTitle());
		if(book.getIsAvailable()==null)
			book.setIsAvailable(bookService.getBookById(id).getIsAvailable());
		book.setBookId(id);
		bookService.updateBook(book);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/books/{id}" )
	public void removeBook(@PathVariable int id) {
		bookService.deleteBook(id);
	}
	
	@RequestMapping("/members/{id}/issue" )
	public List<Book> showIssuableBooks() {
		return bookService.getAvailableBooks("true");
	}
	
	@RequestMapping("/members/{id}/return" )
	public List<Book> showReturnableBooks(@PathVariable int id) {
		return bookService.getIssuedBooks(id);
	}
	
}
