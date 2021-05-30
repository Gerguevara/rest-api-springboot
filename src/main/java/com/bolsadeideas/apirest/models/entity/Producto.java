package com.bolsadeideas.apirest.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {


    /*
    * Declarations
    * */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  String nombre;

    private  Double precio;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;


    private String descripcion;


    /*
     * metodos pre persist
     * */

    @PrePersist
    public void preGuardadoDeDatos() {
        this.createAt = new Date();

    }

    /*
    GETTERS AND SETTERS
    */


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



}
