package com.romullocordeiro.imageprovider.resources;

import com.romullocordeiro.imageprovider.models.Imagem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romullocordeiro.imageprovider.repository.ImagemRepository;

@RestController
@RequestMapping(value="/api")
public class ImagemResource {

	@Autowired
	ImagemRepository imagemRepository;
	
	//Todas Imagens do banco
	@GetMapping("/imagens")
	public List<Imagem> listaImagens(){
		return imagemRepository.findAll();		
	}
	
    //imagem especifica com id X
	@GetMapping("/imagens/id/{id}")
	public Imagem getOneImageById(@PathVariable(value="id") long id) {
		return imagemRepository.findById(id);
	}

	//Todas imagens com tag X
	@GetMapping("/imagens/tag/{tag}")
	public List<Imagem> listaImagens(@PathVariable(value="tag")String tag){
		return imagemRepository.findByTag(tag);	
	} 
	
	
	
}
