package kg.nurtelecom.util.converter.excel;

import kg.nurtelecom.util.casting.DefaultCasting;
import kg.nurtelecom.util.parser.DefaultParser;
import kg.nurtelecom.util.parser.Parser;
import kg.nurtelecom.util.reflect.ReflectUtil;
import kg.nurtelecom.util.reflect.annotation.AnnotationUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @param <T> Тип объекта для преобразования данных из строки Excel.
 */
public class ColumnWorkbookExcelConverter<T> implements ExcelConverter<Workbook, InputStream, List<T>> {

    private final Class<T> clazz;
    private final Map<String, Field> headerToFieldMap;
    private final Map<String, Parser> headerToParserdMap;

    public ColumnWorkbookExcelConverter(Class<T> clazz) {
        this.clazz = clazz;
        headerToFieldMap = AnnotationUtil.mapValueToField(clazz, ExcelColumn.class, "value");
        headerToParserdMap = AnnotationUtil.mapMethodToReturn(clazz, ExcelColumn.class, "parser", Parser.class);
    }

    @Override
    public List<T> convert(Workbook workbook, int sheetNumber) throws ExcelConversionException {
        if (workbook == null || workbook.getNumberOfSheets() <= sheetNumber) {
            return Collections.emptyList();
        }

        Sheet sheet = workbook.getSheetAt(sheetNumber);
        Iterator<Row> rowIterator = sheet.iterator();

        if (!rowIterator.hasNext()) {
            return Collections.emptyList();
        }

        Row headerRow = rowIterator.next();
        Map<Integer, Field> columnIndexToFieldMap = parseHeader(headerRow);

        if (columnIndexToFieldMap.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> resultList = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row dataRow = rowIterator.next();
            try {
                if (dataRow == null || isRowEmpty(dataRow)) {
                    continue;
                }
                T newInstance = parseRow(dataRow, columnIndexToFieldMap);
                resultList.add(newInstance);
            } catch (Exception e) {
                String cellReference = new CellReference(dataRow.getRowNum(), 0).formatAsString();
                throw new ExcelConversionException("Ошибка при обработке строки " + (dataRow.getRowNum() + 1) + " (начиная с " + cellReference + ")", e);
            }
        }

        return resultList;
    }

    @Override
    public List<T> convert(InputStream inputStream) throws ExcelConversionException {
        if (inputStream == null) {
            return Collections.emptyList();
        }
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            List<T> combinedList = new ArrayList<>();
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                combinedList.addAll(convert(workbook, i));
            }
            return combinedList;
        } catch (IOException e) {
            throw new ExcelConversionException("Ошибка чтения файла Excel из потока.", e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Integer, Field> parseHeader(Row headerRow) {
        Map<Integer, Field> columnIndexToFieldMap = new HashMap<>();
        for (Cell cell : headerRow) {
            String headerText = cell.getStringCellValue().trim();
            if (headerToFieldMap.containsKey(headerText)) {
                columnIndexToFieldMap.put(cell.getColumnIndex(), headerToFieldMap.get(headerText));
            }
        }
        return columnIndexToFieldMap;
    }

    private T parseRow(Row dataRow, Map<Integer, Field> columnIndexToFieldMap) throws ReflectiveOperationException {
        T instance = ReflectUtil.createObject(clazz);

        for (Map.Entry<Integer, Field> entry : columnIndexToFieldMap.entrySet()) {
            Integer columnIndex = entry.getKey();
            Field field = entry.getValue();
            Cell cell = dataRow.getCell(columnIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            if (cell != null) {
                cell.setCellType(CellType.STRING);
                String cellValue = cell.getStringCellValue().trim();
                if (!cellValue.isEmpty()) {
                    Parser<?> parser = headerToParserdMap.get(field.getName());
                    field.setAccessible(true);
                    if (parser instanceof DefaultParser) {
                        field.set(instance, DefaultCasting.getInstance().cast(field.getType(), cellValue));
                    } else {
                        field.set(instance, parser.parse(cellValue));
                    }
                }
            }
        }
        return instance;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell != null) {
                cell.setCellType(CellType.STRING);
                if (cell.getStringCellValue() != null && !cell.getStringCellValue().trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}