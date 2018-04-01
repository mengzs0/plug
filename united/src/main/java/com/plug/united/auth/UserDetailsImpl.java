package com.plug.united.auth;

import com.plug.united.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserDetailsImpl extends User {

	public UserDetailsImpl(String id, List<GrantedAuthority> authorities) {
		super(id, "", authorities);
	}

	public UserDetailsImpl(Member member, List<GrantedAuthority> authorities) {
		super(member.getUserId(), member.getPassword(), authorities);
	}
}
