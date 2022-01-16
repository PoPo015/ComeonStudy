package com.comeon.study.common.config.security.jwt;

public abstract class JwtTokenConfirmManager extends JwtTokenUtil{

    protected JwtTokenConfirmManager(String secretKey) {
        super(secretKey);
    }

    protected String removeHeader(String tokenWithHeader) {
        return tokenWithHeader.substring(AuthHeader.DECIDED_HEADER_NAME.length());
    }
}
