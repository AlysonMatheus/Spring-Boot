package br.com.Alyson.services;

import br.com.Alyson.Controllers.FileController;
import br.com.Alyson.Exception.FileNotFoundException;
import br.com.Alyson.Exception.FileStorageException;
import br.com.Alyson.config.FileStorageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private  static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    //defino a varialvel aonde vamos armezanar os aqruivos
    private final Path fileStorageLocation;



    @Autowired
    //Lançando execeção caso ocorra erro quando for lançado
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        // caminho do diretorio para o salvamento do arquivo... normalizando eles tratando os caracteres invalidos
        Path path = Paths.get(fileStorageConfig.getUpload_dir()).toAbsolutePath().toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        try {
            logger.info("Creating Directories");
            // criando o diretorio de armazenamento
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("Could bot create the directory where files will be stored!");
            // se ocorrer alguma falha vai lançar essa exceção
            throw new FileStorageException("Could bot create the directory where files will be stored! ", e);
        }
    }

    public String storeFile(MultipartFile file) {// armazena o arquivo em disco, 1 limpa os caractereces invalidos do disco, 2 valida se o nome do arquivo contem coisas indesejadas
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                logger.error("Sorry Filename contains invalid path Sequence" + filename);
                throw new FileStorageException("Sorry Filename contains invalid path Sequence" + filename);
            }
            logger.info("Saving file in Disk");
            // vai determinar o path ate onde o arquivo vai ser salvo e o nome do arquivo
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);// copia o nome do arquivo pro sistema,
            // convertendo o arquivo pra uma Inputstream, passando o destino e pra fim verifica se o arquivo ja existe( com o mesmo nome)
        } catch (Exception e) {
            logger.error("Could not store file" + filename + ". Please try Again");
            throw new FileStorageException("Could not store file" + filename + ". Please try Again", e);
        }
        return filename; // retorna o nome do arquivo
    }
    public Resource loadFileAsResource (String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();// internamente o java vai pegar o arquivo esta sendo salvo (ele vai defenir o caminho)
            Resource resource =  new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            }else {
                logger.error("File not found" + fileName);
                throw new FileNotFoundException("File not found" + fileName);
            }

        }catch (Exception e){
            logger.error("File not found" + fileName, e);
            throw new FileNotFoundException("File not found" + fileName, e);

        }

    }
}
