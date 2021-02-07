package com.bolsadeideas.apirest.models.service;

import com.bolsadeideas.apirest.models.entity.Cliente;

import java.util.List;

public interface IclienteService {

    public List<Cliente> findAll();
    public  Cliente findById(long id);
    public Cliente save(Cliente cliente);
    public void delete(long id);

}
