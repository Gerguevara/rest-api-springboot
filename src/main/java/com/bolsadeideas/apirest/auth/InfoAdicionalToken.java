package com.bolsadeideas.apirest.auth;

import com.bolsadeideas.apirest.models.entity.Usuario;
import com.bolsadeideas.apirest.models.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// esta clase agrega mas informacion al token de la que trae por defecto
@Component
public class InfoAdicionalToken implements TokenEnhancer {


    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        Usuario usuario = usuarioService.findByUsername(oAuth2Authentication.getName());
        Map<String, Object>  info = new HashMap<>();
        info.put("info_adicional", "Hola soy".concat(oAuth2Authentication.getName()));
        info.put("Email", usuario.getEmail());
        info.put("Nombre completo", usuario.getNombre() + " " + usuario.getApellido());
        // se hace un cast para poder pasar esta informacion a la clase u poder usar el setAdditionaInfo
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);

        return oAuth2AccessToken;
    }
}
