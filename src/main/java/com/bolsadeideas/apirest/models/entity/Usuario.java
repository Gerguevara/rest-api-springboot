package com.bolsadeideas.apirest.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 20)
    private  String username;

    @Column(length = 30)
    private  String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="usuarios_roles", joinColumns= @JoinColumn(name="usuario_id"),
    inverseJoinColumns=@JoinColumn(name="role_id"),
    uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id", "role_id"})})
    private List<Role> roles;

    private boolean enabled;


    /*
    * GETTER AND SETTERS
    * */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

/*anotaciones
* una relacion many to many crea una tabla intermedia
* en este casos usuarios_roles,  en este caso la relacion es unidireccional Usuarios - Roles
* y no es necesaria ponerla en ambas clases.
**
* El nombre de la tabla intermedia puede customizarce en la clase dueña de la relacion, en este caso usuarios
*   @JoinTable(name="usuarios_roles", joinColumns= @JoinColumn(name="usuario_id"),
    inverseJoinColumns=@JoinColumn(name="role_id"),
    uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id", "role_id"})})
* ejemplo ce como customisar una tabla intermedia, @UniqueConstraint indica que la relacion de pares debe ser unica, un usuario no puede repetir roles
* Lo demas solo es la asignacion del nombre de la tabla intermedia y la indicacion de que capos hacen la union
* */