package com.viva.sub_book;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viva.book.Book;
import com.viva.book.BookService;
import com.viva.wishlist.WishListService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookCopyController {

	@Autowired
	private BookCopyService bookCopyService;

	@Autowired
	private BookService bookService;

	@Autowired
	HttpServletResponse httpResponse;
	
	@Autowired
	WishListService wishListService;

	@RequestMapping("/books/{bookId}/copies")
	public List<BookCopy> getAllBookCopies(@PathVariable int bookId) {
		return bookCopyService.getAllBookCopies(bookId);
	}

	@RequestMapping("/books/{bookId}/copies/{id}")
	public Optional<BookCopy> getBookCopy(@PathVariable String id) {
		return bookCopyService.getBookCopy(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/books/{bookId}/copies")
	public void addBookCopy(@RequestBody BookCopy bookCopy, @PathVariable int bookId) {
		bookCopy.setSubBookId(Integer.toString(bookId) + "." + bookCopy.getSubBookId());
		bookCopy.setBook(new Book(bookId));
		bookCopyService.addBookCopy(bookCopy);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/books/{bookId}/copies/{id}")
	public void updateBookCopy(@RequestBody BookCopy bookCopy, @PathVariable int bookId, @PathVariable String id) {
		bookCopy.setBook(new Book(bookId));
		bookCopyService.updateBookCopy(bookCopy);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/books/{bookId}/copies/{id}")
	public void deleteBookCopy(@PathVariable String id) {
		bookCopyService.deleteBookCopy(id);
	}

	@RequestMapping("/members/{memberId}/issue/{bookId}")
	public void issueBook(@PathVariable int memberId, @PathVariable int bookId) throws IOException {

		if (bookService.bookExists(bookId)
				&& (bookService.getBookById(bookId).getIsAvailable().equalsIgnoreCase("true"))) {
			bookCopyService.issueBook(memberId, bookId);
			bookService.updateAvailability(bookId);
		}

		else {
			System.out.println("Book does not exist or Book is not available! Redirecting you to Available books.");
			httpResponse.sendRedirect("/members/" + Integer.toString(memberId) + "/issue");
		}
		wishListService.updateAvailableWishes();
	}

	@RequestMapping("/members/{memberId}/return/{bookId}")
	public void returnBook(@PathVariable int memberId, @PathVariable int bookId) throws IOException {
		if(bookService.bookExists(bookId)) {
		bookCopyService.returnBook(memberId, bookId);
		bookService.updateAvailability(bookId);
		}
		wishListService.updateAvailableWishes();
	}

}
