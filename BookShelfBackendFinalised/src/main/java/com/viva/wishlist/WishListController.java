package com.viva.wishlist;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viva.book.Book;
import com.viva.member.Member;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WishListController {

	@Autowired
	private WishListService wishListService;

	@RequestMapping("/wishlist")
	public List<WishList> getWishList() {
		return wishListService.getAllWishes();
	}

	@RequestMapping("/wishlist/{memberId}")
	public List<WishList> getWishesByMember(@PathVariable int memberId) {
		return wishListService.getWishesByMemberId(memberId);
	}
	
	@RequestMapping("/wishlist/{memberId}/{id}")
	public WishList getWishByMember(@PathVariable int memberId,@PathVariable int id) {
		return wishListService.getWishByMemberIdAndId(memberId,id);
	}

	@RequestMapping("/members/{memberId}/wishlist")
	public List<Book> getWishableBooks(@PathVariable int memberId) {
		return wishListService.getWishableBooks();
	}

	@RequestMapping("/books/{bookId}/wishlisted")
	public List<WishList> getWishedBook(@PathVariable int bookId) {
		return wishListService.getWishesByBookId(bookId);
	}
	
	@RequestMapping("/members/{memberId}/wishlisted")
	public List<WishList> getMemberWishes(@PathVariable int memberId) {
		return wishListService.getWishesByMemberId(memberId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/members/{memberId}/wishlist")
	public void addWishByMember(@RequestBody WishList wishList, @PathVariable int memberId) {
		Book b = wishListService.getBookByBookId(wishList.getBookId());
		if (null != b) {
			if (b.getIsAvailable().equals("false")) {
				wishList.setBook(b);
				wishList.setMember(new Member(memberId));
				wishListService.addWish(wishList);
			}
			else
				System.out.println("Book is Available! No need to Wishlist");
		}
		else System.out.println("Book Not Found!");
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/wishlist/{id}")
	public void deleteWish(@PathVariable int id) {
		wishListService.deleteWish(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/members/{memberId}/wishlist/{id}")
	public void deleteWishByMember(@PathVariable int id, @PathVariable int memberId) {
		wishListService.deleteWishByMemberIdAndId(memberId, id);
	}
	
	@RequestMapping("/wishlist/update")
	public void alertWishes() {
		wishListService.updateAvailableWishes();
	}

}
