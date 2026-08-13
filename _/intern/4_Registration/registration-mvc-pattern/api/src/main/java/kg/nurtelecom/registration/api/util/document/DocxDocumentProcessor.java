package kg.nurtelecom.registration.api.util.document;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import kg.nurtelecom.registration.api.exception.TemplateException;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class DocxDocumentProcessor implements DocumentProcessor {
    public byte[] generateAgreementDocument(String documentPath, Map<String, String> replacements, String documentName) {
        try {
            Resource resource = new ClassPathResource(documentPath);
            try (InputStream fis = resource.getInputStream();
                 XWPFDocument document = new XWPFDocument(fis);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

                replaceTextInDocument(document, replacements);
                setDocumentMetadata(document, documentName);
                document.write(bos);

                return bos.toByteArray();
            }
        } catch (Exception e) {
            throw new TemplateException("Failed to get agreement file content");
        }
    }

    private void replaceTextInDocument(XWPFDocument document, Map<String, String> replacements) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            replaceTextInParagraph(paragraph, replacements);
        }
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replaceTextInParagraph(paragraph, replacements);
                    }
                }
            }
        }
    }

    private void replaceTextInParagraph(XWPFParagraph paragraph, Map<String, String> replacements) {
        StringBuilder fullText = new StringBuilder();
        for (XWPFRun run : paragraph.getRuns()) {
            if (run.getText(0) != null) {
                fullText.append(run.getText(0));
            }
        }
        String updatedText = fullText.toString();
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            updatedText = updatedText.replace(entry.getKey(), entry.getValue());
        }
        if (!updatedText.contentEquals(fullText)) {
            for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
                paragraph.removeRun(i);
            }
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(updatedText);
        }
    }

    private void setDocumentMetadata(XWPFDocument document, String fileName) {
        POIXMLProperties.CoreProperties coreProperties = document.getProperties().getCoreProperties();
        coreProperties.setTitle(fileName);
    }
}
