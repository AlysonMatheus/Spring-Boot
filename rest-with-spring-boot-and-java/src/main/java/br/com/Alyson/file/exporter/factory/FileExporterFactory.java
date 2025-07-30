package br.com.Alyson.file.exporter.factory;


import br.com.Alyson.Exception.BadReuqestException;
import br.com.Alyson.file.exporter.contract.FileExporter;
import br.com.Alyson.file.exporter.impl.CsvExporter;
import br.com.Alyson.file.exporter.impl.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component // conseguir injetar em outras classes
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);
    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) throws Exception{
        if (acceptHeader.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return context.getBean(XlsxExporter.class);
//            return new XlsxImporter();

        } else if (acceptHeader.equalsIgnoreCase("text/cvs")) {
            return context.getBean(CsvExporter.class);

        } else {
            throw new BadReuqestException("Invalid File Format");
        }

    }
}
