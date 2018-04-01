package com.plug.united.auth.ajax;

import com.plug.united.auth.UserDetailsImpl;
import com.plug.united.member.entity.Member;
import com.plug.united.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AjaxUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository repository;

	@Override
	public UserDetails loadUserByUsername(String userId) {
		Member member = repository.findById(userId).orElse(null);
		//Member member = repository.findMemberByUserId(username).orElse(null);

		if (member == null) {
			throw new UsernameNotFoundException(userId + "라는 사용자가 없습니다.");
		}

		return new UserDetailsImpl(member, AuthorityUtils.createAuthorityList(member.getRole()));
	}
}
