package com.viva.member;


import org.springframework.data.repository.CrudRepository;

import com.viva.member.Member;

public interface MemberRepository extends CrudRepository<Member,Integer> {
	

}
