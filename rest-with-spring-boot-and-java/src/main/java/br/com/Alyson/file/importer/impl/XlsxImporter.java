package br.com.Alyson.file.importer.impl;

import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsxImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        try(XSSFWorkbook workbook = new XSSFWorkbook(inputStream))// carregar a planilha em um workbook
        {
            XSSFSheet sheet = workbook.getSheetAt(0);// pegar a partir da "aba"/planilha(armezenamos no objeto sheet"lista de linha das planilhas")
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext())
 rowIterator.next(); // pular a primeira linha da planilha
            return parseRowsToPersonDTOList(rowIterator);
        }
    }

    private List<PersonDTO> parseRowsToPersonDTOList(Iterator<Row> rowIterator)
    {
        List<PersonDTO> people = new ArrayList<>();//declara uma lista de people
        while (rowIterator.hasNext())// pra cada rowInterator ele executa ate terminar
        {
            Row row = rowIterator.next();
            if (isRowValid(row));//se a linha for valida ele converte o row para PersonDto
            people.add(parseRowToPersonDto(row));
        }
        return people;
    }

    private PersonDTO parseRowToPersonDto(Row row) {
        PersonDTO person = new PersonDTO();
        person.setFirstName(row.getCell(0).getStringCellValue());
        person.setLastName(row.getCell(1).getStringCellValue());
        person.setAddress(row.getCell(2).getStringCellValue());
        person.setGender(row.getCell(3).getStringCellValue());
        person.setEnabled(true);
        return person;
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;// se a celula nao for nula e nem vazia vai prosseguir, caso contrario vai ignorar
    }
}
