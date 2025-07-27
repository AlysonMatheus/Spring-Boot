package br.com.Alyson.file.importer.impl;

import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.file.importer.contract.FileImporter;

import java.io.InputStream;
import java.util.List;

public class XlsxImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        return List.of();
    }
}
