package com.bolsadeideas.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private InfoAdicionalToken infoAdicionalToken;

    @Autowired
    @Qualifier("authenticationManager") //qualifier para elegir una  implementacion especifica de un bean por si hay otro con un mismo nombre
    private AuthenticationManager authenticationManager;

    @Override //configura la ruta
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
         security.tokenKeyAccess("permitAll()")
         .checkTokenAccess("isAuthenticated");
    }


    // configura y registra varios clientes del front por ejemplo aca es una applicaion de angular
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory().withClient("angularapp")
            .secret(passwordEncoder.encode("dexoxidoribonucleico")) // semilla para codificar passwords
            .scopes("read","write") //permisos
            .authorizedGrantTypes("password", "refresh_token") //tipos de autenticacion soportados
            .accessTokenValiditySeconds(3600) //exp tpken
            .refreshTokenValiditySeconds(3600);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //concatena la informacion adicional que respondera el token (de la clase infoAdicional)
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
         endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
     JwtAccessTokenConverter jwtAccessTokenConverter = new  JwtAccessTokenConverter();
     jwtAccessTokenConverter.setSigningKey("mypasswoerfortoken"); // setea la clave del token , si no se pasa esta es generada automaticamente
	return jwtAccessTokenConverter;
    }
}

/*
* accessTokenConverter maneja el  los datos de el token, ahi se le puede agregar mas informacion al token
* */