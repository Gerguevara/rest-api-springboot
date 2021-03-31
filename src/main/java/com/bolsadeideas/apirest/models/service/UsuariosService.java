package com.bolsadeideas.apirest.models.service;

import com.bolsadeideas.apirest.models.dao.IUsuarioDao;
import com.bolsadeideas.apirest.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService implements IUsuarioService, UserDetailsService {


    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario == null){
            System.out.println("No existe el usuario para loggin");
            throw  new UsernameNotFoundException("No existe el usuario para loggin");
        }


        List<GrantedAuthority> authorities = usuario.getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.getNombre()))
        .collect(Collectors.toList());

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true ,true, authorities);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }
}
