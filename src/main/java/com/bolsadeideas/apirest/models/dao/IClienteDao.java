package com.bolsadeideas.apirest.models.dao;

import com.bolsadeideas.apirest.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao  extends CrudRepository<Cliente, Long> {
}
