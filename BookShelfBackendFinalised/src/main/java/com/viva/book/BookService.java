package com.viva.book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viva.member.Member;
import com.viva.member.MemberRepository;
import com.viva.sub_book.BookCopy;
import com.viva.sub_book.BookCopyService;

//import com.viva.sub_book.BookCopy;
//import com.viva.sub_book.BookCopyService;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	BookCopyService bookCopyService;

	// private BookCopyService bookCopyService;

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		bookRepository.findAll().forEach(books::add);
		return books;
	}

	public Optional<Book> getBook(int id) {
		return bookRepository.findById(id);
	}

	public void addBook(Book book) {
		bookRepository.save(book);
		for (int i = 0; i < book.getBookQuantity(); i++) {
			BookCopy bookCopy = new BookCopy();
			bookCopy.setSubBookId(Integer.toString(book.getBookId()) + "." + Integer.toString(i + 1));
			bookCopy.setBook(book);
			bookCopyService.addBookCopy(bookCopy);
		} // this loop automatically creates SubBook Entities for new book
	}

	public void updateBook(Book book) {
		bookRepository.save(book);
	}

	public void deleteBook(int id) {
		bookRepository.deleteById(id);
	}

	public Book getBookById(int id) {
		return bookRepository.getBookByBookId(id);
	}

	public List<Book> searchBook(String bookAuthor, String bookGenre, String bookPublisher, String bookTitle) {

		if (bookGenre == null && bookPublisher == null && bookTitle == null)
			return bookRepository.findByBookAuthor(bookAuthor);
		else if (bookAuthor == null && bookPublisher == null && bookTitle == null)
			return bookRepository.findByBookGenre(bookGenre);
		else if (bookAuthor == null && bookGenre == null && bookTitle == null)
			return bookRepository.findByBookPublisher(bookPublisher);
		else if (bookAuthor == null && bookGenre == null && bookPublisher == null)
			return bookRepository.findByBookTitle(bookTitle);

		else if (bookPublisher == null && bookTitle == null)
			return bookRepository.findByBookAuthorAndBookGenre(bookAuthor, bookGenre);
		else if (bookGenre == null && bookTitle == null)
			return bookRepository.findByBookAuthorAndBookPublisher(bookAuthor, bookPublisher);
		else if (bookGenre == null && bookPublisher == null)
			return bookRepository.findByBookAuthorAndBookTitle(bookAuthor, bookTitle);
		else if (bookAuthor == null && bookTitle == null)
			return bookRepository.findByBookGenreAndBookPublisher(bookGenre, bookPublisher);
		else if (bookAuthor == null && bookPublisher == null)
			return bookRepository.findByBookGenreAndBookTitle(bookGenre, bookTitle);
		else if (bookAuthor == null && bookGenre == null)
			return bookRepository.findByBookPublisherAndBookTitle(bookPublisher, bookTitle);

		else if (bookTitle == null)
			return bookRepository.findByBookAuthorAndBookGenreAndBookPublisher(bookAuthor, bookGenre, bookPublisher);
		else if (bookPublisher == null)
			return bookRepository.findByBookAuthorAndBookGenreAndBookTitle(bookAuthor, bookGenre, bookTitle);
		else if (bookAuthor == null)
			return bookRepository.findByBookGenreAndBookTitleAndBookPublisher(bookGenre, bookTitle, bookPublisher);
		else if (bookGenre == null)
			return bookRepository.findByBookAuthorAndBookTitleAndBookPublisher(bookAuthor, bookTitle, bookPublisher);

		else
			return bookRepository.findByBookAuthorAndBookGenreAndBookPublisherAndBookTitle(bookAuthor, bookGenre,
					bookPublisher, bookTitle);
	}

	public List<Book> getAvailableBooks(String isAvailable) {
		return bookRepository.findByIsAvailable(isAvailable);
	}

	public boolean bookExists(int bookId) {
		return bookRepository.existsByBookId(bookId);
	}

	public List<Book> getIssuedBooks(int memberId) {
		ArrayList<Book> booksIssued = new ArrayList<Book>();
		Member issuer = memberRepository.findById(memberId).get();
		System.out.println("issuerId : " + issuer.getMemberId());
		List<String> bookCopiesId = issuer.getBookCopyIssued();
		System.out.println("List of BookCopies issued : " + bookCopiesId);
		Iterator<String> iterator = bookCopiesId.iterator();
		while (iterator.hasNext()) {
			String bookCopyId = iterator.next();
			int bookIssuedId = Integer.parseInt(bookCopyId.substring(0, 4));
			System.out.println("bookIssuedId : " + bookIssuedId);
			booksIssued.add(bookRepository.getBookByBookId(bookIssuedId));
		}
		return booksIssued;
	}

	public void updateAvailability(int bookId) {
		Book book = bookRepository.getBookByBookId(bookId);
		book.setIsAvailable("false");
		List<BookCopy> bookCopies = bookCopyService.getAllBookCopies(bookId);
		Iterator<BookCopy> iterator = bookCopies.iterator();
		while (iterator.hasNext()) {
			BookCopy bookCopy = iterator.next();
			if (bookCopy.getIssueDate() == null) {
				book.setIsAvailable("true");
			}
		}
		bookRepository.save(book);
	}
}
