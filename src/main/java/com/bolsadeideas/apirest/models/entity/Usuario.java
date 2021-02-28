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
* @JoinTable(name="users_authories" @JoinColumn(name="user_id", inverseJoinColumns=@JoinColumn(name="user_id")))
* ejemplo ce como customisar una tabla intermedia
* El nombre de la tabla intermedia puede customizarce en la clase dueña de la relacion, en este caso usuarios
* */