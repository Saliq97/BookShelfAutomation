package com.viva.sub_book;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viva.member.Member;
import com.viva.member.MemberRepository;
import com.viva.wishlist.WishListRepository;

@Service
public class BookCopyService {

	@Autowired
	private BookCopyRepository bookCopyRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private WishListRepository wishListRepository;

	public List<BookCopy> getAllBookCopies(int bookId) {
		List<BookCopy> bookCopies = new ArrayList<>();
		bookCopyRepository.findByBookBookId(bookId).forEach(bookCopies::add);
		return bookCopies;
	}

	public Optional<BookCopy> getBookCopy(String id) {
		return bookCopyRepository.findById(id);
	}

	public void addBookCopy(BookCopy bookCopy) {
		bookCopyRepository.save(bookCopy);
	}

	public void updateBookCopy(BookCopy bookCopy) {
		bookCopyRepository.save(bookCopy);
	}

	public void deleteBookCopy(String id) {
		bookCopyRepository.deleteById(id);
	}

	public void issueBook(int memberId, int bookId) {
		List<BookCopy> bookCopies = getAllBookCopies(bookId);
		Iterator<BookCopy> iterator = bookCopies.iterator();
		while (iterator.hasNext()) {
			BookCopy bookCopy = iterator.next();
			if (bookCopy.getMember() == null) {
				wishListRepository.deleteByMemberMemberIdAndBookBookId(memberId, bookId);
				bookCopy.setIssueDate(LocalDate.now());
				bookCopy.setMember(memberRepository.findById(memberId).get());
				bookCopyRepository.save(bookCopy);
				Member issuer = memberRepository.findById(memberId).get();
				ArrayList<String> bookCopyIssued;
				if (issuer.getBookCopyIssued() == null)
					bookCopyIssued = new ArrayList<String>();
				else
					bookCopyIssued = issuer.getBookCopyIssued();
				bookCopyIssued.add(bookCopy.getSubBookId());
				issuer.setBookCopyIssued(bookCopyIssued);
				ArrayList<Integer> wishListedBooks = issuer.getWishedBooksAvailable();
				if(wishListedBooks != null)
				wishListedBooks.removeIf(wishBookId -> wishBookId.equals(bookId));
				issuer.setWishedBooksAvailable(wishListedBooks);
				memberRepository.save(issuer);

				break;
			}

		}
		
	}

	public void returnBook(int memberId, int bookId) {
		boolean flag = true;
		Member issuer = memberRepository.findById(memberId).get();
		ArrayList<String> bookCopiesIssued = issuer.getBookCopyIssued();
		Iterator<String> iterator = bookCopiesIssued.iterator();
		while (iterator.hasNext()) {
			BookCopy issuedCopy = bookCopyRepository.findById(iterator.next()).get();
			if (Integer.parseInt(issuedCopy.getSubBookId().substring(0, 4)) == bookId) {
				issuer.setFineAccumulated(
						issuer.getFineAccumulated() + generateFine(issuedCopy.getIssueDate(), LocalDate.now()));
				bookCopiesIssued.remove(issuedCopy.getSubBookId());
				issuer.setBookCopyIssued(bookCopiesIssued);
				memberRepository.save(issuer);
				issuedCopy.setMember(null);
				issuedCopy.setIssueDate(null);
				bookCopyRepository.save(issuedCopy);
				flag = false;

				break;
			}

		}
		if (flag)
			System.out.println("Book " + bookId + " not Issued by you!");
		
	}

	private float generateFine(LocalDate issueDate, LocalDate returnDate) {
		float fine;
		long elapsedDays = ChronoUnit.DAYS.between(issueDate, returnDate);
		if (elapsedDays < 7)
			fine = 0;
		else if (elapsedDays >= 7 && elapsedDays < 15)
			fine = 12.5f;
		else
			fine = (float) (1.5 * elapsedDays);
		return fine;
	}
}
