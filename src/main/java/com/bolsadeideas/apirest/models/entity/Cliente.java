package com.bolsadeideas.apirest.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String nombre;
    private String apellido;
    private String email;

    @Column( name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;


    /*
    * Pre Persist (mnetodos que se ejecutan antes de guardar)
    * */
    @PrePersist
    public void prePersist(){
        createAt = new Date(); //setea la fecha antes de guardar cualquier elemento
    }



    /*
    * Gettes and Setters
    * */

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    // requerido por la interfaz
    private static final long serialVersionUID = 1l;

}