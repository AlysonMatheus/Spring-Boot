package br.com.Alyson.file.importer.impl;

import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {
    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)// pular o cabeçalho
                .setIgnoreEmptyLines(true)// ignorar linhas vazias
                .setTrim(true)// elimar os espaco antes/depois
                .build();


        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream)); // pegando cada linha da planinha e simplificando em uma lista de record
        return parseRecordsToPersonDTos(records);
    }

    private List<PersonDTO> parseRecordsToPersonDTos(Iterable<CSVRecord> records) {// criando lista de person
        List<PersonDTO> people = new ArrayList<>();
        for ( CSVRecord record: records){
            PersonDTO person = new PersonDTO();
            person.setFirstName(record.get("first_name"));
            person.setLastName(record.get("last_name"));
            person.setAddress(record.get("address"));
            person.setGender(record.get("gender"));
            person.setEnabled(true);
            people.add(person);
        }

        return people;

    }
}
