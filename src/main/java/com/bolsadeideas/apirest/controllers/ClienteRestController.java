package com.bolsadeideas.apirest.controllers;

import com.bolsadeideas.apirest.models.entity.Cliente;
import com.bolsadeideas.apirest.models.service.IclienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

@Autowired()
private IclienteService CLienteService;

/*
* END POINTS*
* */

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return CLienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public Cliente showCliente(@PathVariable Long id){
       //path variable toma de la url el elemento
        return CLienteService.findById(id);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED) // retora un codido status especifico por defecto es OK 200
    public Cliente create(@RequestBody Cliente cliente){
        return CLienteService.save(cliente);
    }


    @PutMapping("/clientes/{id}")



}
