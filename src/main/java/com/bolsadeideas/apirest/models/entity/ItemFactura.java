package com.bolsadeideas.apirest.models.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "facturas_items")
public class ItemFactura implements Serializable {



    /*
    * DECLARACIONES
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch =FetchType.LAZY )
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    /*
    * metodos  propios
    */

    public Double getmporte(){
        return this.cantidad.doubleValue() * producto.getPrecio();
    }


    /*
    * GETTERS AND SETTERS
    * */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


}
