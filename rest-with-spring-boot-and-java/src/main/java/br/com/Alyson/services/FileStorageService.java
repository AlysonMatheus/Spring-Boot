package br.com.Alyson.services;

import br.com.Alyson.Exception.FileStorageException;
import br.com.Alyson.config.FileStorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    //defino a varialvel aonde vamos armezanar os aqruivos
    private final Path fileStorageLocation;

    @Autowired
    //Lançando execeção caso ocorra erro quando for lançado
    public FileStorageService(FileStorageConfig fileStorageLocation) {
        // caminho do diretorio para o salvamento do arquivo... normalizando eles tratando os caracteres invalidos
        Path path = Paths.get(fileStorageLocation
                        .getUpload_dir())
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        try {
            // criando o diretorio de armazenamento
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            // se ocorrer alguma falha vai lançar essa exceção
            throw new FileStorageException("Could bot create the directory where files will be stored! ", e);
        }
    }

    public String storeFile(MultipartFile file) {// armazena o arquivo em disco, 1 limpa os caractereces invalidos do disco, 2 valida se o nome do arquivo contem coisas indesejadas
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                throw new FileStorageException("Sorry Filename contains invalid path Sequence" + filename);
            }
            // vai determinar o path ate onde o arquivo vai ser salvo e o nome do arquivo
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);// copia o nome do arquivo pro sistema,
            // convertendo o arquivo pra uma Inputstream, passando o destino e pr fim verifica se o arquivo ja existe( com o mesmo nome)
        } catch (Exception e) {
            throw new FileStorageException("Could not store file" + filename + ". Please try Again", e);
        }
        return filename; // retorna o nome do arquivo
    }
}
