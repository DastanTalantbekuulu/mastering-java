package kg.nurtelecom.registration.api.service.export;

import jakarta.persistence.EntityNotFoundException;
import kg.nurtelecom.registration.common.payload.request.PersonRequest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Service
public class ExcelExportGenerator implements ExcelExportService {

    @Override
    public ByteArrayInputStream exportClientsToExcel(List<PersonRequest> clients) {
        if (clients.isEmpty()) {
            throw new EntityNotFoundException("No client found");
        }
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Clients");

            // Создаем заголовки
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "Фамилия", "Имя", "Отчество", "Пол", "Национальность", "Дата рождения",
                    "ИНН", "Дата выдачи", "Годен до",
                    "ID", "Орган выдачи", "Статус", "Почта"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Заполняем данные
            int rowIdx = 1;
            for (PersonRequest client : clients) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(client.lastName());
                row.createCell(1).setCellValue(client.firstName());
                row.createCell(2).setCellValue(client.middleName());
                row.createCell(3).setCellValue(client.personGender());
                row.createCell(4).setCellValue(client.nationality());
                row.createCell(5).setCellValue(client.dateOfBirth().toString());
                row.createCell(6).setCellValue(client.identificationNumber());
                row.createCell(7).setCellValue(client.dateOfIssue().toString());
                row.createCell(8).setCellValue(client.dateOfExpiry().toString());
                row.createCell(9).setCellValue(client.documentId());
                row.createCell(10).setCellValue(client.issuingAuthority());
                row.createCell(11).setCellValue(client.status().toString());
                row.createCell(12).setCellValue(client.email());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании Excel файла", e);
        }
    }
}
