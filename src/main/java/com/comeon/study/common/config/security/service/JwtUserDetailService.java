package com.comeon.study.common.config.security.service;

import com.comeon.study.common.config.security.service.accountcontext.AccountContext;
import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.exception.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(username))
                .orElseThrow(NotFoundMemberException::new);

        return new AccountContext(
                member.getId(),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(
                        member.getRole()
                                .getValue())));
    }
}
