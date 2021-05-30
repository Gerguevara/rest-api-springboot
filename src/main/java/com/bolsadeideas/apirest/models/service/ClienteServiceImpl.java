package com.bolsadeideas.apirest.models.service;

import com.bolsadeideas.apirest.models.dao.IClienteDao;
import com.bolsadeideas.apirest.models.dao.IFacturaDao;
import com.bolsadeideas.apirest.models.dao.IProductoDao;
import com.bolsadeideas.apirest.models.entity.Cliente;
import com.bolsadeideas.apirest.models.entity.Factura;
import com.bolsadeideas.apirest.models.entity.Producto;
import com.bolsadeideas.apirest.models.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements  IclienteService{
    // necesario para utilizar el objeto que maneja


    /***
     * DECLARACIONES
     * ***/

   @Autowired
    private IClienteDao clienteDao;

  @Autowired
  private IFacturaDao facturaDao;

  @Autowired
    private IProductoDao  productoDao;

    /***
     * METODOS
     * ***/


// metodos de cliente
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


    // implementacion de metodos factura en el servicio de Cliente
    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(long id) {
        // debe agregarse el orElse porque retorna un optiuonal
        return facturaDao.findById(id).orElse(null);
    }
    @Override
    public Factura saveFactura(Factura factura) {
        return facturaDao.save(factura);
    }
    @Override
    public void deleteFactura(long id) {
        facturaDao.deleteById(id);
    }

    // implementacios de metodos de producto
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findProductoByNombre(String term) {
        return  this.productoDao.findByNameUsingQuery(term);
    }
}

/*notas:
*  @Transactional(readOnly = true) no es necesario porque interfaz crudRepository ya la define,
*  pero si es necesaria cuando se utiliza un metodo customizado de query*, es buena practica ponerla en
* metodos de lectura
* */