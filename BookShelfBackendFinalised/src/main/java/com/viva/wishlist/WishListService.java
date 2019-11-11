package com.viva.wishlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viva.book.Book;
import com.viva.book.BookRepository;
import com.viva.member.Member;
import com.viva.member.MemberRepository;

@Service
public class WishListService {

	@Autowired
	private WishListRepository wishListRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private MemberRepository memberRepository;

//	@PersistenceContext
//	private EntityManager em;

	public Book getBookByBookId(int id) {
//		String sql = "SELECT * FROM BOOK WHERE BOOK_ID = ?";
//		Query query = em.createNativeQuery(sql, Book.class);
//		query.setParameter(1, id);
//		Book b = (Book) query.getSingleResult();
//		return b;
		return bookRepository.getBookByBookId(id);

	}

	public List<WishList> getWishesByMemberId(int memberId) {
		List<WishList> wishes = new ArrayList<>();
		wishListRepository.findByMemberMemberId(memberId).forEach(wishes::add);
		return wishes;
	}

	public List<WishList> getWishesByBookId(int bookId) {
		List<WishList> wishes = new ArrayList<>();
		wishListRepository.findByBookBookId(bookId).forEach(wishes::add);
		return wishes;
	}

	public Optional<WishList> getWishById(int id) {
		return wishListRepository.findById(id);
	}

	public List<WishList> getAllWishes() {
		return (List<WishList>) wishListRepository.findAll();
	}

	public void addWish(WishList wishList) {
		wishListRepository.save(wishList);
	}

	public void deleteWish(int id) {
		wishListRepository.deleteById(id);
	}

	public WishList getWishByMemberIdAndId(int memberId, int id) {
//		String sql = "SELECT * FROM WISH_LIST WHERE MEMBER_MEMBER_ID = ? AND WISH_LIST_ID = ?";
//		Query query = em.createNativeQuery(sql, WishList.class);
//		query.setParameter(1, memberId);
//		query.setParameter(2, id);
//		WishList w = (WishList) query.getSingleResult();
		WishList w = wishListRepository.findByMemberMemberIdAndWishListId(memberId, id);
		return w;
	}

	@Transactional
	public void deleteWishByMemberIdAndId(int memberId, int id) {
//		String sql = "DELETE FROM WISH_LIST WHERE MEMBER_MEMBER_ID = ? AND WISH_LIST_ID = ?";
//		Query query = em.createNativeQuery(sql, WishList.class);
//		query.setParameter(1, memberId);
//		query.setParameter(2, id);
//		int rowsDeleted = query.executeUpdate();
//		System.out.println("Rows Deleted : " + rowsDeleted);
		wishListRepository.deleteByMemberMemberIdAndWishListId(memberId, id);
	}

	public List<Book> getWishableBooks() {
		List<Book> allBooks = new ArrayList<>();
		List<Book> wishableBooks = new ArrayList<>();
		bookRepository.findAll().forEach(allBooks::add);
		Iterator<Book> iterator = allBooks.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			String isAvailable = book.getIsAvailable();
			if (isAvailable.equals("false"))
				wishableBooks.add(book);
		}
		return wishableBooks;
	}

	public void updateAvailableWishes() {
		List<Member> allMembers = new ArrayList<>();
		memberRepository.findAll().forEach(allMembers::add);
		Iterator<Member> memberIterator = allMembers.iterator();
		while (memberIterator.hasNext()) {
			ArrayList<Integer> pushableWishes = new ArrayList<>();
			Member member = memberIterator.next();
			List<WishList> memberWishes = getWishesByMemberId(member.getMemberId());
			Iterator<WishList> wishIterator = memberWishes.iterator();
			while (wishIterator.hasNext()) {
				WishList wish = wishIterator.next();
				System.out.println("bookID :"+wish.getBook().getBookId());
				if (wish.getBook().getIsAvailable().equals("true"))
					pushableWishes.add(wish.getBook().getBookId());
			}
			member.setWishedBooksAvailable(pushableWishes);
			memberRepository.save(member);
		}
	}

}
