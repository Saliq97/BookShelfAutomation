package com.viva.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viva.member.Member;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/members")
	public List<Member> getAllMembers() {
		return memberService.getAllMembers();
	}

	@RequestMapping("/members/{id}")
	public Optional<Member> getMember(@PathVariable int id) {
		return memberService.getMember(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/members" )
	public void addMember(@RequestBody Member topic) {
		memberService.addMember(topic);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/members/{id}" )
	public void updateMember(@RequestBody Member member,@PathVariable int id) {
		memberService.updateMember(member,id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/members/{id}" )
	public void removeMember(@PathVariable int id) {
		memberService.deleteMember(id);
	}
	
	
}
