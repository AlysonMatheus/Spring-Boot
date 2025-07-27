package br.com.Alyson.file.importer.contract;

import br.com.Alyson.data.dto.v1.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;


}
