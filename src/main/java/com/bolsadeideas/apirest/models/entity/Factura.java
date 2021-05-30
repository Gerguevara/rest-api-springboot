package com.bolsadeideas.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// toda clase entity debe implementar la clase serizable


@Entity
@Table(name = "facturas")
public class Factura implements Serializable {


    /*
    * DECLARACIONES
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private String observacion;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @JsonIgnoreProperties(value={"facturas", "hibernateLazyInitializer", "handler"}, allowSetters=true)// super importante excluir para evitar bucles envevidos
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<ItemFactura> items;

    /*
     * metodos pre persist
     * */

    @PrePersist
    public void preGuardadoDeDatos(){
        this.createAt = new Date();
    }

    /*
    * constructores
    * */

    public Factura() {
        items = new ArrayList<>();
    }



    /*
    * METODOS PROPIOS
    * */
    public Double getTotal(){
        Double total = 0.00;
        for(ItemFactura item: items){
            total = item.getmporte();
        }
     return  total;
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


    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
