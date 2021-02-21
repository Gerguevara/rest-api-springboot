package com.bolsadeideas.apirest.controllers;

import com.bolsadeideas.apirest.models.entity.Cliente;
import com.bolsadeideas.apirest.models.service.IclienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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


    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(0, 4, Sort.by("email"));
        // Pageable pageable = PageRequest.of(0, 20, Sort.by("nombre").ascending().and(Sort.by("apellido").descending()));
        return CLienteService.findAll(pageable);
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
                    .map( err -> err.getField() + "" + err.getDefaultMessage())
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
        // antes de borrar al cliente tambien se borra su foto
        try{
            Cliente cliente = CLienteService.findById(id);
            String nombreFotoAnterior = cliente.getPhoto();

            if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0){
                Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                    archivoFotoAnterior.delete();
                }
            }
            CLienteService.delete(id);
        }catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar en la base de datos");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Eliminado correctamente" );
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    // guardano foto de cliente
    @PostMapping("clientes/upload")
    public ResponseEntity<?> uploadImg(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") long id){
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = CLienteService.findById(id);


        if(!archivo.isEmpty()){
                String archivoNombre = UUID.randomUUID().toString() + archivo.getOriginalFilename().replace(" ", "");
                Path rutaArchivo = Paths.get("uploads").resolve(archivoNombre).toAbsolutePath() ;
            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                response.put("mensaje", "error al guardar la imagen");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //borrando foto anterior si existe
            String nombreFotoAnterior = cliente.getPhoto();

            if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0){
                Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                    archivoFotoAnterior.delete();
                }
            }

            // seteando  foto y guardandola fisicamente
            cliente.setPhoto(archivoNombre);
            CLienteService.save(cliente);

            response.put("cliente", cliente);
            response.put("mensaje", "actualizado");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    // visualizar una foto
    @GetMapping("/uploads/img/{nombreFoto:.+}")
     public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){

        Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath() ;

        Resource recurso = null;

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
            if(!recurso.exists() && !recurso.isReadable()){
                throw new RuntimeException("Error no se pudo cargar la imagen");
            }

        HttpHeaders cabecera  = new HttpHeaders();
            cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ recurso.getFilename()+"\"");

        return new ResponseEntity<Resource>(recurso , cabecera,  HttpStatus.OK);
     }




}
