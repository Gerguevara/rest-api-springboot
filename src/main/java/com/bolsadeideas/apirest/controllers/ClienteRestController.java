package com.bolsadeideas.apirest.controllers;

import com.bolsadeideas.apirest.models.entity.Cliente;
import com.bolsadeideas.apirest.models.service.IclienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@CrossOrigin(origins = {"http://localhost:4200"}) // cors de dominio de peticiones acceptadas
@RestController // denota que respondera con json las peticiones
@RequestMapping("/api") // pefijo de url global
public class ClienteRestController {

    @Autowired()
    private IclienteService CLienteService; //declaracion del service a usar

    /*****************************************************************************
     ******************************** END POINTS**********************************
     ******************************************************************************/
    @GetMapping("/clientes")
    public List<Cliente> index() {
        return CLienteService.findAll();
    }

    /*
     * GET CLIENTE
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> showCliente(@PathVariable Long id) {

        //path variable toma de la url el elemento
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        // errores de servidor
        try {
            cliente = CLienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos ");
            response.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // errores de parametros no validos enviados por el usuario
        if (cliente == null) {
            response.put("mensaje", "No existe el cliente con el id ".concat(id.toString()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK); // responde un 200
    }

    /*
     * CREAR CLIENTES
     */
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED) // retora un codido status especifico por defecto es OK 200
    public ResponseEntity<?> create(@Valid  @RequestBody Cliente cliente, BindingResult result) {

        Cliente clienteNuevo = null;
        Map<String, Object> response = new HashMap<>();

        //basado en las notaciones de validacion en el entity
        //la noacion valid en el metodo y el objeto result valida que cumpla la estructura de objeto
        if(result.hasErrors()){
            List<String> errors  =  result.getFieldErrors()
                    .stream()
                    .map( err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        //error de guardado
        try {
            clienteNuevo = CLienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el guardado en la base de datos");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // mensaje de exito
        response.put("mesnaje", "creado con exito");
        response.put("cliente", clienteNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); // responde un 200
    }

    /*
     * EDITAR CLIENTES
     */
    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result , @PathVariable Long id) {
        // toma el cliente
        Cliente clienteActual = CLienteService.findById(id);
        Cliente clienteActualizado = null;
        Map<String, Object> response = new HashMap<>();

        //la noacion valid en el metodo y el objeto result valida que cumpla la estructura de objeto
        if(result.hasErrors()){
            List<String> errors  =  result.getFieldErrors()
                    .stream()
                    .map( err -> "El campo " + err.getField() + "" + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        // errores de parametros no validos enviados por el usuario
        if (clienteActual == null) {
            response.put("mensaje", "No existe el cliente con el id ".concat(id.toString()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        // le signa valores
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setEmail(cliente.getEmail());

        try {
            // guarda los cambios y retorna
            clienteActualizado = CLienteService.save(clienteActual);
        }catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mesnaje", "Actualizado con exito");
        response.put("cliente", clienteActualizado);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /*
     * BORRAR
     */
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?>  delete(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        try{
            CLienteService.delete(id);
        }catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar en la base de datos");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Eliminado correctamente" );
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
