package com.bolsadeideas.apirest.models.service;

import com.bolsadeideas.apirest.models.entity.Cliente;
import com.bolsadeideas.apirest.models.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IclienteService {

    public List<Cliente> findAll();
    public Page<Cliente> findAll(Pageable pageable); //metodo de paginacion
    public  Cliente findById(long id);
    public Cliente save(Cliente cliente);
    public void delete(long id);
    public List<Region> findAllRegiones();

}
