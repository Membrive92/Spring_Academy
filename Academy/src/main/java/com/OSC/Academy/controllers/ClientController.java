package com.OSC.Academy.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.OSC.Academy.models.entity.Client;
import com.OSC.Academy.models.services.IClientService;


@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientController {

	@Autowired
	private IClientService clientService;
	
	@GetMapping("/clients")
	public List<Client> index(){
		return clientService.findAll();
	}
	@GetMapping("/clients/page/{page}")
	public Page<Client> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 1);
		return clientService.findAll(pageable);
	}
	@GetMapping("/clients/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Client client = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			client = clientService.findById(id);
		} catch (DataAccessException e) {
			response.put("message", "Hay un error en la base de datos");
			response.put("error",e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(client == null) {
			response.put("message", "ID client: " .concat(id.toString().concat(" No encontrado en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Client>(client, HttpStatus.OK); 
		
	}
	
	@PostMapping("/clients")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Client client,BindingResult result ) {
		Client newClient = null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			
			/* A way of collect error from validation
			 * List<String> errors = new ArrayList<>();
			 * 
			 * for(FieldError err: result.getFieldErrors()) { errors.add("Field '"+
			 * err.getField() + "' "+ err.getDefaultMessage()); }
			 */
			List<String> errors = result.getFieldErrors().stream().map(err ->  "Field '" + err.getField() + "' " +err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			newClient = clientService.save(client);
		} catch (DataAccessException e) {
			response.put("message", "Ha ocurrido un error durante la creacion del cliente");
			response.put("error",e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "El cliente se ha creado con exito");
		response.put("client", newClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
		
	}
	
	@PutMapping("/clients/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Client client,BindingResult result, @PathVariable Long id) {
		 Client currentClient = clientService.findById(id);
		 Client upadtedClient = null;
		 Map<String, Object> response = new HashMap<>();
		 if(result.hasErrors()) {
				
				/* A way of collect error from validation
				 * List<String> errors = new ArrayList<>();
				 * 
				 * for(FieldError err: result.getFieldErrors()) { errors.add("Field '"+
				 * err.getField() + "' "+ err.getDefaultMessage()); }
				 */
				List<String> errors = result.getFieldErrors().stream().map(err ->  "Field '" + err.getField() + "' " +err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		 
		 if(currentClient == null) {
				response.put("message", "Error: ID client: " .concat(id.toString().concat(" No puede ser editado")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		 
		 
		try {
			 currentClient.setName(client.getName());
			 currentClient.setLastName(client.getLastName());
			 currentClient.setEmail(client.getEmail());
			 currentClient.setCreateAt(client.getCreateAt());
			 
			 upadtedClient = clientService.save(currentClient);
		} catch (DataAccessException e) {
			response.put("message", "Ha ocurrido un error al actualizar");
			response.put("error",e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "El cliente ha sido actualizado con exito");
		response.put("client", upadtedClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
		
	}
	
	@DeleteMapping("/clients/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public  ResponseEntity<?> delete(@PathVariable Long id) {
		
		 Map<String, Object> response = new HashMap<>();
		 try {
		clientService.delete(id);
	} catch(DataAccessException e) {
		response.put("message", "No se ha podido eliminar el cliente");
		response.put("error",e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		 response.put("message", "El cliente ha sido eliminado con exito");
		 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
}
}
