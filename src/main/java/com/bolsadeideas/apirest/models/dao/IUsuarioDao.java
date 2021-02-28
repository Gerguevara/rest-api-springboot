package com.bolsadeideas.apirest.models.dao;

import com.bolsadeideas.apirest.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao  extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String username);

   @Query("select u from Usuario u where u.username=?1")
    public Usuario findByUsernameWithQueryExample(String username);

// ambaas son exactamente lo mismo
}
