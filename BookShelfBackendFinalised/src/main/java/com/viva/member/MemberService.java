package com.viva.member;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viva.member.MemberRepository;
import com.viva.member.Member;


@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberrepository;
	
	public List<Member> getAllMembers(){
		List<Member> members =new ArrayList<Member>();
		memberrepository.findAll().forEach(members::add);
		return members;
	}
	
	public Optional<Member> getMember(int id) {
		return memberrepository.findById(id);
	}
	
	public void addMember(Member member) {
		memberrepository.save(member);		
	}
	
	public void updateMember(Member member,int id) {
		memberrepository.save(member);		
	}
	
	public void deleteMember(int id) {
		memberrepository.deleteById(id);
		return;		
	}
		
}
