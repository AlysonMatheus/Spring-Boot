package br.com.Alyson.file.exporter.impl;

import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.file.exporter.contract.FileExporter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component // Indica que essa classe é um componente gerenciado pelo Spring (Bean)
public class PdfExporter implements FileExporter { // Implementa a interface FileExporter
    @Override
    public Resource exportFile(List<PersonDTO> people) throws Exception {
        // Método que gera um PDF a partir de uma lista de PersonDTO e retorna como Resource

        // Carrega o arquivo de template .jrxml do classpath
        InputStream inputStream = getClass().getResourceAsStream("/templates/people.jrxml");

        // Se não encontrar o arquivo, lança um erro
        if (inputStream == null ){
            throw new RuntimeException("Template file nout found: /templates/people.jrxml");
        }

        // Compila o arquivo .jrxml em um objeto JasperReport
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Converte a lista de pessoas em uma fonte de dados que o JasperReports entende
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(people);

        // Mapa de parâmetros opcionais para o relatório (atualmente sem parâmetros)
        Map<String, Object> parameters = new HashMap<>();
        //parameters.put("title","People Report"); // Exemplo de como passar um parâmetro

        // Preenche o relatório com o layout, parâmetros e dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Usa try-with-resources para criar um OutputStream que vai guardar o PDF na memória
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){

            // Exporta o relatório preenchido para formato PDF, escrevendo no outputStream
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            // Retorna o PDF como um ByteArrayResource para o Spring poder enviar como resposta HTTP
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }
    @Override
    public Resource exportPerson(PersonDTO person) throws Exception {
        return null;
    }
}
