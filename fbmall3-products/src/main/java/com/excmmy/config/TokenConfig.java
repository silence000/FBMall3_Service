package com.excmmy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import pojo.MallConstant;

@Configuration
public class TokenConfig {

    @Bean
    public TokenStore tokenStore() {
        // Jwt令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(MallConstant.SIGNING_KEY); // 对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }
}
