package com.romullocordeiro.imageprovider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.romullocordeiro.imageprovider.models.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long>{

	Imagem findById(long id);
	
	@Async
	@Query(nativeQuery = true, value="SELECT * FROM tb_imagem WHERE tag LIKE LOWER (%?1%)")
    List<Imagem> findByTag(String tag);
	
	
}




/*
 * lembrete de queryes modelo
 * 
 * 
 * a querye abaixo busca todos valores que possuem 'anime' na tag
 * SELECT * FROM `tb_imagem` WHERE tag LIKE '%anime%'
 * 
 */
