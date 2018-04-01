package com.plug.united.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.plug.united.member.entity.Member;
import com.plug.united.member.repository.MemberRepository;
import com.plug.united.member.service.MemberService;

@RestController
public class MemberRestController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

    private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public @ResponseBody Member add(@RequestBody Member member) {

		logger.debug("PLUGPLUGPLUG:" + member.getUserId());
		
		member = new Member(member.getUserId(), passwordEncoder.encode(member.getPassword()),"USER");
		memberService.saveUser(member);

		return member;
	}
	

	@RequestMapping("/")
	public String index() {
		return "helloworld!";
	}
}