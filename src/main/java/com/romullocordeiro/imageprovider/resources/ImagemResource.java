package com.romullocordeiro.imageprovider.resources;

import com.romullocordeiro.imageprovider.models.Imagem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


import com.romullocordeiro.imageprovider.repository.ImagemRepository;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping(value="/api")
public class ImagemResource {

	@Autowired
	ImagemRepository imagemRepository;
	
	//endereço e senha do servidor ftp
	final String ftpHost = "files.000webhost.com";
	final String ftpLogin = "romulloimagedatabase";
	final String ftpPassword = "maluquinho1";
	final String databaseUrl = "https://romulloimagedatabase.000webhostapp.com/Imagens/";
	
	//-----------Retorna Todas Imagens do banco------------------
	@GetMapping("/imagens")
	public List<Imagem> listaImagens(){
		return imagemRepository.findAllOrdered();
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

	//--------------Envia uma imagem com suas informa��es para o servidor de imagens e salva no banco a referencia------------
	@PostMapping("/upload")
	public Imagem uploadImage(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "name", required = true) String name, 
			@RequestParam(value = "uploader", required = true) String uploader,
			@RequestParam(value = "tag", required = true) String tag) {
		
		//todo consegui fazer o FTP funcionar, porem preciso
		//agora fazer o envio da imagem junto com as informa��es
		//como nome, uploader e etc
		
		if(name.length() <= 30 && uploader.length() <= 30 && tag.length() <=50) {

			String generatedName = "";
			try {
	            // Envia o arquivo por ftp para o servidor de arquivos
	            FTPClient client = new FTPClient();
	            client.enterLocalActiveMode();
	            generatedName = RandomString.make(15) + ".png";
	            String filename = generatedName;
	            
	            
	            client.connect(ftpHost);
	            if(client.login(ftpLogin, ftpPassword) && !file.isEmpty()) {
	            	client.enterLocalPassiveMode();
	            	client.setFileType(FTP.BINARY_FILE_TYPE);
	            	client.changeWorkingDirectory("/public_html/Imagens");
	            	
	            	client.storeFile(filename, file.getInputStream()); 
	            	client.logout();
	            	client.disconnect();
	            	
					try {
						Imagem imagem = new Imagem(name, uploader, tag, databaseUrl + generatedName);
					    return imagemRepository.save(imagem);

					}catch(Exception e) {
						return null;
					}
					
	            }else {
	            	System.out.println("Talvez a imagem seja nula!");
	            }            

	            

	        	} catch (IOException e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	           
	        }
	

			
		}else {
			return null;
		}
		return null;		
		
	}//fim upload
	
	

	//------------------------Atualiza as informa��es de uma imagem especifa usando seu id para identificar------------------
	//todo preciso testar o post de update abaixo
	@PostMapping("/update")
	public Imagem updateImage(
			@RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "name", required = true) String name, 
			@RequestParam(value = "uploader", required = true) String uploader,
			@RequestParam(value = "tag", required = true) String tag) {
		

			Imagem imagem = imagemRepository.findById(id);
			try {
				if(imagem != null) {
					imagem.setName(name);
					imagem.setUploader(uploader);
					imagem.setTag(tag);
					return imagemRepository.save(imagem);
				}else {
					System.out.println("CARA, DEU MERDA AQUI, A IMAGEM N�O EXISTE, USA A ROTA DE UPLOAD PF MEU CHAPA");
				}	
			}catch(Exception e) {
				System.out.println("meu patrao, deu uma merda fodida aqui");
			}
		
		return null;
	}
	
	@PostMapping("/delete")
	public Imagem deleteImage(
			@RequestParam(value = "id", required = true) int id){
				Imagem imagem = imagemRepository.findById(id);
				if(imagem != null) {
					imagemRepository.delete(imagem);
					//apaga a imagem no servidor FTP
					try {
						
						FTPClient client = new FTPClient();
			            //client.enterLocalActiveMode();			            
			            client.connect(ftpHost);
			            if(client.login(ftpLogin, ftpPassword)) {
			            	boolean success = client.deleteFile(imagem.getName());
			            	client.logout();
			            	client.disconnect();
			            	if(success) {
			            		return imagem;
			            	}
			            }
			            
					} catch (Exception e) {
						// TODO: handle exception
					}

					return null;
					
				}
				System.out.println("Falha ao encontrar imagem para deletar, n�o existe");
				return null;
			}

	
	
}
