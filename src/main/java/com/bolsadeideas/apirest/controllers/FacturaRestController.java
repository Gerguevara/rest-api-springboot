package com.bolsadeideas.apirest.controllers;


import com.bolsadeideas.apirest.models.entity.Factura;
import com.bolsadeideas.apirest.models.entity.Producto;
import com.bolsadeideas.apirest.models.service.IclienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200"}) // cors de dominio de peticiones acceptadas
@RestController // denota que respondera con json las peticiones
@RequestMapping("/api") // pefijo de url global
public class FacturaRestController {


    @Autowired
    private IclienteService iclienteService;


    @GetMapping("/facturas/{id}")
    @ResponseStatus(code= HttpStatus.OK)
    private Factura show(@PathVariable Long id){
        return this.iclienteService.findFacturaById(id);
    }

    @DeleteMapping("/facturas/{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    private void delete(@PathVariable Long id){
         this.iclienteService.deleteFactura(id);
    }
    //Tambien crearemos aqui mismo el controlador de productos


    @GetMapping("productos/{termino}")
    @ResponseStatus(code= HttpStatus.OK)
    private List<Producto> findProductos(@PathVariable String termino){
        return  this.iclienteService.findProductoByNombre(termino);
    }



    @PostMapping("facturas")
    @ResponseStatus(HttpStatus.CREATED)
    private Factura crear(@RequestBody Factura factura){
         return  this.iclienteService.saveFactura(factura);
    }
}
