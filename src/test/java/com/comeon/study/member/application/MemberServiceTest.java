package com.comeon.study.member.application;

import com.comeon.study.common.config.security.jwt.JwtTokenProvider;
import com.comeon.study.member.domain.refreshtoken.RefreshToken;
import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.domain.repository.RefreshTokenRepository;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberJoinResponse;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static com.comeon.study.member.fixture.RefreshTokenFixture.TEST_REFRESH_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(
                memberRepository,
                refreshTokenRepository,
                passwordEncoder,
                jwtTokenProvider);
    }

    @Test
    void 회원가입_성공() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest();

        given(memberRepository.save(any(Member.class))).willReturn(MEMBER);

        // when
        MemberJoinResponse memberJoinResponse = memberService.join(memberJoinRequest);

        // then
        assertAll(
                () -> assertThat(memberJoinResponse.getEmail()).isEqualTo(MEMBER.getEmail()),
                () -> assertThat(memberJoinResponse.getNickName()).isEqualTo(MEMBER.getNickName())
        );
    }

    @Test
    void 로그인_성공() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(TEST_MEMBER_LOGIN_EMAIL, TEST_MEMBER_LOGIN_PASSWORD);
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(TEST_LOGIN_MEMBER));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(refreshTokenRepository.save(any(RefreshToken.class))).willReturn(TEST_REFRESH_TOKEN);

        // when
        MemberLoginResponse memberLoginResponse = memberService.signIn(memberLoginRequest);

        // then

        assertAll(
                () -> assertThat(memberLoginResponse.getNickName()).isEqualTo(TEST_MEMBER_LOGIN_NICKNAME),
                () -> assertThat(memberLoginResponse.getRefreshToken()).isEqualTo(TEST_REFRESH_TOKEN.getValue())
        );
    }
}