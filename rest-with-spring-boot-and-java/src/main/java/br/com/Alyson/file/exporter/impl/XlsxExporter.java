package br.com.Alyson.file.exporter.impl;

import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.file.exporter.contract.FileExporter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XlsxExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> people) throws Exception {
        return null;
    }
}