package com.bolsadeideas.apirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

public class TokenController {

    private final TokenStore tokenStore;

    @Autowired
    public TokenController(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/oauth/revoke")
    public void revokeToken(Authentication authentication) {
        Optional.ofNullable(authentication).ifPresent(auth -> {
            OAuth2AccessToken accessToken = tokenStore.getAccessToken((OAuth2Authentication) auth);

            Optional.ofNullable(accessToken).ifPresent(oAuth2AccessToken -> {
                Optional.ofNullable(oAuth2AccessToken.getRefreshToken()).ifPresent(tokenStore::removeRefreshToken);
                tokenStore.removeAccessToken(accessToken);
            });
        });
    }

}
