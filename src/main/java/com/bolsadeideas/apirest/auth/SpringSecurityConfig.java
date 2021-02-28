package com.bolsadeideas.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService usuarioService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
    }


    // metodo de ecriptacion
   @Bean // se usar para registrar metodos en el contenedor de spring, similar a componet pero para metodos
    public BCryptPasswordEncoder  passwordEncoder(){
            return new BCryptPasswordEncoder();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                //necesario deshabilitar esta seguridad en una api y tambien las sessiones
        }
}

/**
 * Notas este es el passo ! de la configuracion, esto se le pasa a AuthorizationServerConfig *
 *
 * @Bean("BCryptPasswordEncoder") se utiliza para darle un nombre especifico al bena que registra pero no es necesario
 * siempre toma por defecto el nombre de el metodo
 * */