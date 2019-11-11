package com.viva.sub_book;


import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface BookCopyRepository extends CrudRepository<BookCopy, String> {
	
	public List<BookCopy> findByBookBookId(Integer bookId);
	
}
