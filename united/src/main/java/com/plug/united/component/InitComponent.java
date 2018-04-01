package com.plug.united.component;

import com.plug.united.member.entity.Member;
import com.plug.united.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitComponent implements ApplicationRunner {

	@Autowired
	private MemberRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public void run(ApplicationArguments args) {
		Member member = new Member("jmkwon", passwordEncoder.encode("1234"),"USER");
		repository.save(member);

		Member admin = new Member("admin", passwordEncoder.encode("1234"), "ADMIN");
		repository.save(admin);
	}
}
