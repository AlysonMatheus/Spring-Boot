package br.com.Alyson.Controllers;

import br.com.Alyson.Controllers.docs.FileControllerDocs;
import br.com.Alyson.data.dto.UploadFileResponseDTO;
import br.com.Alyson.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {

    private  static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileStorageService service;


    @PostMapping("/uploadFile")
    @Override
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file)// recebe um multipartFile atraves da Request
    {
      var fileName =  service.storeFile(file);

      // http:// localhost::8081/api/file/v1/downloadFile/filename.docx (monta o link dessa maneira)
      var fileDownload = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/v1/downloadFile/")// ServletUriComponentsBuilder.fromCurrentContextPath() chamado para construir o caminho do arquivo(http // etc...)
              .path(fileName).toUriString();
        return new UploadFileResponseDTO(fileName, fileDownload, file.getContentType(), file.getSize() );
    }

    @Override
    public List<UploadFileResponseDTO> uploadMultipleFile(MultipartFile[] files) {
        return List.of();
    }

    @Override
    public ResponseEntity<ResponseEntity> downloadFile(String fileName, HttpServletRequest request) {
        return null;
    }
}
