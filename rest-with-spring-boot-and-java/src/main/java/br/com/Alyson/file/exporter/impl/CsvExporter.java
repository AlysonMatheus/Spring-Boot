package br.com.Alyson.file.exporter.impl;

import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.file.exporter.contract.FileExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component // Torna essa classe um componente do Spring (pode ser injetado com @Autowired)
public class CsvExporter implements FileExporter { // Implementa a interface FileExporter

    @Override
    public Resource exportFile(List<PersonDTO> people) throws Exception {
        // Cria um stream de saída em memória (em vez de salvar em arquivo físico)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Writer para escrever caracteres no outputStream com codificação UTF-8
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        // Cria a configuração do formato CSV
        CSVFormat cvsFormat = CSVFormat.Builder.create()
                .setHeader("ID", "First Name", "Last Name", "Address", "Gender", "Enabled") // Define os nomes das colunas (cabeçalho)
                .setSkipHeaderRecord(false) // Não pula o cabeçalho (ele será escrito no início do arquivo)
                .build();

        // Bloco try-with-resources para garantir que o CSVPrinter será fechado corretamente
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, cvsFormat)) {
            // Para cada pessoa da lista, imprime uma linha no CSV com seus dados
            for (PersonDTO person : people) {
                csvPrinter.printRecord(
                        person.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getGender(),
                        person.getEnabled()
                );
            }
        }

        // Converte os dados escritos no outputStream para um array de bytes
        // e retorna como um recurso (Resource) que pode ser enviado numa resposta HTTP
        return new ByteArrayResource(outputStream.toByteArray());
    }

    @Override
    public Resource exportPerson(PersonDTO person) throws Exception {
        return null;
    }
}
