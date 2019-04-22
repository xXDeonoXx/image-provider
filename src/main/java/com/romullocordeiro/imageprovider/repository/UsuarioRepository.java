package com.romullocordeiro.imageprovider.repository;

import org.springframework.data.repository.CrudRepository;

import com.romullocordeiro.imageprovider.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

	Usuario findByLogin(String login);
	
}
