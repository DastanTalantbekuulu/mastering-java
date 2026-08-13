package kg.nurtelecom.util.converter.excel;

import kg.nurtelecom.util.converter.Converter;

public interface ExcelConverter<W, I, R> extends Converter<I, R> {
    R convert(W w, int sheetNumber);
}
