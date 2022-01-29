package com.comeon.study.member.dto;

import com.comeon.study.common.config.security.refreshtoken.RefreshToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginResponse {

    private String accessToken;

    private String refreshToken;

    public MemberLoginResponse(String accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken.getValue();
    }
}
