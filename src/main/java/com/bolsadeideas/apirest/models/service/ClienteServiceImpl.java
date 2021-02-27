package com.bolsadeideas.apirest.models.service;

import com.bolsadeideas.apirest.models.dao.IClienteDao;
import com.bolsadeideas.apirest.models.entity.Cliente;
import com.bolsadeideas.apirest.models.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements  IclienteService{
    // necesario para utilizar el objeto que maneja
   @Autowired
    private IClienteDao clienteDao;


    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
        //devuelve un iterable por eso hace un cast
    }

    // findAllwith Pagination
    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }


    @Override
    public Cliente findById(long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Override
    public void delete(long id) {
        clienteDao.deleteById(id);
    }

    @Override
    public List<Region> findAllRegiones() {
        return null;
    }
}

/*notas:
*  @Transactional(readOnly = true) no es necesario porque interfaz crudRepository ya la define,
*  pero si es necesaria cuando se utiliza un metodo customizado de query*
* */