package br.com.Alyson.file.exporter.impl;

import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.file.exporter.contract.FileExporter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
        // Carrega o arquivo de template .jrxml do classpath
        InputStream mainTemplateStream = getClass().getResourceAsStream("/templates/person.jrxml");

        // Se não encontrar o arquivo, lança um erro
        if (mainTemplateStream == null ){
            throw new RuntimeException("Template file nout found: /templates/person.jrxml");
        }
        InputStream subReportStream = getClass().getResourceAsStream("/templates/books.jrxml");

        // Se não encontrar o arquivo, lança um erro
        if (subReportStream == null ){
            throw new RuntimeException("Template file nout found: /templates/books.jrxml");
        }
        InputStream qrCodeStream = generateQRCode(person.getProfileUrl(),200,200);


        // Compila o arquivo .jrxml em um objeto JasperReport
        JasperReport mainReport = JasperCompileManager.compileReport(mainTemplateStream);
        JasperReport subReport = JasperCompileManager.compileReport(subReportStream);



        // Converte a lista de pessoas em uma fonte de dados que o JasperReports entende
        JRBeanCollectionDataSource subDataSource = new JRBeanCollectionDataSource(person.getBooks());
        // Mapa de parâmetros opcionais para o relatório (atualmente sem parâmetros)
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("SUB_REPORT_DATA_SOURCE",subDataSource);
        parameters.put("BOOK_SUB_REPORT",subReport);
        parameters.put("QR_CODEIMAGE",qrCodeStream);

        JRBeanCollectionDataSource mainDataSource = new JRBeanCollectionDataSource(Collections.singletonList(person));



        //parameters.put("title","People Report"); // Exemplo de como passar um parâmetro

        // Preenche o relatório com o layout, parâmetros e dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, mainDataSource);

        // Usa try-with-resources para criar um OutputStream que vai guardar o PDF na memória
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){

            // Exporta o relatório preenchido para formato PDF, escrevendo no outputStream
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            // Retorna o PDF como um ByteArrayResource para o Spring poder enviar como resposta HTTP
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    private InputStream generateQRCode(String url, int width, int height) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE,width,height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
