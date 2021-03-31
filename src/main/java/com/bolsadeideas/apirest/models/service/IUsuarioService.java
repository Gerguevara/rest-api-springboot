package com.bolsadeideas.apirest.models.service;

import com.bolsadeideas.apirest.models.entity.Usuario;

public interface IUsuarioService {


    public Usuario findByUsername(String username);
}

// se utiliza para agregar informacion adicional en el token y es implementada usuarioService