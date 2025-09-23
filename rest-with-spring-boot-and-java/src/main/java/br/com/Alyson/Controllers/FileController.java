package br.com.Alyson.Controllers;

import br.com.Alyson.Controllers.docs.FileControllerDocs;
import br.com.Alyson.data.dto.UploadFileResponseDTO;
import br.com.Alyson.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileStorageService service;


    @PostMapping("/uploadFile")
    @Override
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file)// recebe um multipartFile atraves da Request
    {
        var fileName = service.storeFile(file);

        // http:// localhost::8081/api/file/v1/downloadFile/filename.docx (monta o link dessa maneira)
        var fileDownload = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/v1/downloadFile/")// ServletUriComponentsBuilder.fromCurrentContextPath() chamado para construir o caminho do arquivo(http // etc...)
                .path(fileName).toUriString();
        return new UploadFileResponseDTO(fileName, fileDownload, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFile")
    @Override
    public List<UploadFileResponseDTO> uploadMultipleFile(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("downloadFile/{fileName:.+}")
    @Override
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = service.loadFileAsResource(fileName); // lendo o arquivo em disco atraves do nome
        String contentType = null;//determina o contentType
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());// pega o caminho absoluto do arquivo, tenta obter o contentType atraves disso
        } catch (Exception e) {
            logger.error("Could not determine file type!");

        }
        if (contentType == null) {// caso ele seja null, seta um contentType default
            contentType = "application/octet - stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))// tipo de conteudo
                .header(HttpHeaders.CONTENT_DISPOSITION,// dizendo pra response que no cabeçalho estamos devolvendo um anexo
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);// passando no body o arquivo
    }
}
