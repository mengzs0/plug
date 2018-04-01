package com.plug.united.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.plug.united.member.entity.Member;
import com.plug.united.member.repository.MemberRepository;
import com.plug.united.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void saveUser(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    
	@Override
	public Member findMemberByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}