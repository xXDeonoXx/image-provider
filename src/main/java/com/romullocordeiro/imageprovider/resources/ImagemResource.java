package com.romullocordeiro.imageprovider.resources;

import com.romullocordeiro.imageprovider.models.Imagem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romullocordeiro.imageprovider.repository.ImagemRepository;

@RestController
@RequestMapping(value="/api")
public class ImagemResource {

	@Autowired
	ImagemRepository imagemRepository;
	
	//-----------Retorna Todas Imagens do banco------------------
	@GetMapping("/imagens")
	public List<Imagem> listaImagens(){
		return imagemRepository.findAll();		
	}
	
    //----------------Retorna imagem especifica com id X-----------------------
	@GetMapping("/imagens/id/{id}")
	public Imagem getOneImageById(@PathVariable(value="id") long id) {
		return imagemRepository.findById(id);
	}

	//------------Retorna Todas imagens com tag X------------------
	@GetMapping("/imagens/tag/{tag}")
	public List<Imagem> listaImagens(@PathVariable(value="tag")String tag){
		return imagemRepository.findByTagContainingIgnoreCase(tag);	
	} 
	
	
	
	//Posts
	
	@PostMapping("/imagem")
	public Imagem saveImagem(@RequestBody Imagem imagem) {
		return imagemRepository.save(imagem);
	}
	
	
}
