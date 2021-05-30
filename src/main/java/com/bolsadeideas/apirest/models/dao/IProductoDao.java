package com.bolsadeideas.apirest.models.dao;


import com.bolsadeideas.apirest.models.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductoDao extends CrudRepository<Producto, Long> {

    @Query("select p from Producto p where p.nombre like %?1%")
    public List<Producto> findByNameUsingQuery(String termino);

    // otra forma aprovechando  opciones de jpa, se construye con base al nombre del metodo como este caso  ContainingIgnoringCase
    public List<Producto> findByNombreContainingIgnoringCase(String termino);

}
