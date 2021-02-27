package com.bolsadeideas.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="clientes")
public class Cliente implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 10)
    @Column(nullable = false)
    private String nombre;

    @NotEmpty
    @Size(min = 4, max = 10)
    private String apellido;

    @NotEmpty
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column( name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;


    private  String photo;


   @NotNull(message = "no puede estar vacio")
   @ManyToOne(fetch = FetchType.LAZY) // una region puede tener muchos clientes la cardinalidad empieza de regiones a clientes
   @JoinColumn(name = "region_id") //el campo que hace la union, pero se puede omitir y automaticamente tomara el nombre de la tabla mas _id
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // super importante excluir estos campor
   private Region region;

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


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Region getRegion() {
        return region;
    }

    public Region setRegion(Region region) {
        this.region = region;
        return region;
    }


    // requerido por la interfaz
    private static final long serialVersionUID = 1l;

}
