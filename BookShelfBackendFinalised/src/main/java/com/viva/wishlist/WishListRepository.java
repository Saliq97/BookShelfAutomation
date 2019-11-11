package com.viva.wishlist;


import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface WishListRepository extends CrudRepository<WishList, Integer> {
	
	public List<WishList> findByMemberMemberId(Integer memberId);
	public List<WishList> findByBookBookId(Integer bookId);
	public void deleteByMemberMemberId(Integer memberId);
	public WishList findByMemberMemberIdAndWishListId(Integer memberId,Integer id);
	public void deleteByMemberMemberIdAndWishListId(Integer memberId,Integer id);
	public void deleteByMemberMemberIdAndBookBookId(Integer memberId, Integer bookId);
	//public List<WishList> deleteByBookBookId(Integer memberId);
	
}
