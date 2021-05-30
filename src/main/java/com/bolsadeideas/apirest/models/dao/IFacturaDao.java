package com.bolsadeideas.apirest.models.dao;

import com.bolsadeideas.apirest.models.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long>{
// los metodos seran aplicados en el servicio de cliente
    //ejemplo como hacer un join column
    // reduce los querys a una sola consulta
     /*  @Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
    public Factura fetchByIdWithClienteWhithItemFacturaWithProducto(Long id);
    */
}