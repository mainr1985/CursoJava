package com.meusestudos.projetosboot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meusestudos.projetosboot.entities.User;

//pra dizer que é um controlador Rest
@RestController
@RequestMapping(value="/users") //equivalente das rotas do laravel
public class UserResource {

	//ResponseEntity é um tipo específico do Spring que retorna respostas de requisições web 
	@GetMapping//indica que esse método responde a uma requisição GET do http
	public ResponseEntity<User> findAll(){
		User u = new User(1L,"Maíra","mairanrocha@hotmail.com", "111111111","1234");
		return ResponseEntity.ok().body(u); //retorna um ok + o corpo da resposta da requisição
	}
}
