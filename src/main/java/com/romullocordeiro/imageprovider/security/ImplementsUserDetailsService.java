package com.romullocordeiro.imageprovider.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.romullocordeiro.imageprovider.models.Usuario;
import com.romullocordeiro.imageprovider.repository.UsuarioRepository;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository ur;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = ur.findByLogin(login);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario n�o encontrado");
		}
		return new User(usuario.getUsername(), usuario.getPassword(), true, true ,true ,true,usuario.getAuthorities());
	}

}
