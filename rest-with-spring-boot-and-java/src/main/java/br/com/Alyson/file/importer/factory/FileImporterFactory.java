package br.com.Alyson.file.importer.factory;


import br.com.Alyson.Exception.BadReuqestException;
import br.com.Alyson.file.importer.contract.FileImporter;
import br.com.Alyson.file.importer.impl.CsvImporter;
import br.com.Alyson.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component // conseguir injetar em outras classes
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);
    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception{
        if (fileName.endsWith(".xlsx")){
            return context.getBean(XlsxImporter.class);
//            return new XlsxImporter();

        } else if (fileName.endsWith("csv")) {
            return context.getBean(CsvImporter.class);

        } else {
            throw new BadReuqestException("Invalid File Format");
        }

    }
}
