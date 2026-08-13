package print;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintDocument implements Printable {
    private String content;
    private Font fontContent;
    private Font fontPage;
    private int[] pageBreaks;
    private List<String> textLines;
    private Map<Integer, ImageIcon> images;
    private int pageWidth;
    private int pageHeight;
    private int linesPerPage;
    private int lineHeight;

    public PrintDocument(String content, Font fontContent) {
        this.content = content;
        this.fontContent = fontContent;
        fontPage = new Font("Arial", Font.ITALIC, 12);
        images = new HashMap<>();
    }

    public int print(Graphics graphics, PageFormat pageFormat, int page) {

        graphics.setFont(fontContent);

        if (pageBreaks == null) {
            FontMetrics fontMetrics = graphics.getFontMetrics(fontContent);
            lineHeight = fontMetrics.getHeight();
            pageWidth = (int) pageFormat.getImageableWidth() - 60;
            pageHeight = (int) pageFormat.getImageableHeight() - 150;
            linesPerPage = pageHeight / lineHeight;

            initTextLines(fontMetrics);
            int numBreaks = textLines.size() / linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b = 0; b < numBreaks; b++) {
                pageBreaks[b] = (b + 1) * linesPerPage;
            }
        }
        if (page > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }
        int x = 50, y = 50;
        int start = (page == 0) ? 0 : pageBreaks[page - 1];
        int end = (page == pageBreaks.length) ? textLines.size() : pageBreaks[page];
        for (int line = start; line < end; line++) {
            y = y + lineHeight;
            if (textLines.get(line) != null) {
                if (textLines.get(line).equals("image://")) {
                    ImageIcon image = images.get(line);
                    graphics.drawImage(image.getImage(), x, y, null);
                } else {
                    graphics.drawString(textLines.get(line), x, y);
                }
            }
        }
        graphics.setFont(fontPage);
        graphics.setColor(Color.BLUE);
        graphics.drawString("Page " + (page + 1), pageWidth, pageHeight + 120);
        return PAGE_EXISTS;
    }

    private void initTextLines(FontMetrics fontMetrics) {
        if (textLines == null) {
            textLines = new ArrayList<>(Arrays.asList(content.split("\n")));
        }
        String standard = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz +-*/_0123456789;:,.<>/?!@#$%^&*()";
        int standardWidth = fontMetrics.stringWidth(standard);
        int standardLength = standard.length();
        int pageLength = pageWidth * standardLength / standardWidth;
        for (int index = 0; index < textLines.size(); index++) {
            if (textLines.get(index).contains("image://")) {
                index = processImage(textLines, index);
            } else if (textLines.get(index).length() > pageLength) {
                int flag = textLines.get(index).length() / pageLength +
                        (textLines.get(index).length() % pageLength > 0 ? 1 : 0);
                if (flag > 1) {
                    newline(index, textLines, pageLength, flag);
                    index = index + flag - 1;
                }
            }
        }
    }

    private void newline(int index, List<String> textLines, int pageLength, int flag) {
        if (textLines.get(index).length() > pageLength) {
            int indexSpace = -1;
            for (int i = pageLength; i > 3; i--) {
                if (textLines.get(index).charAt(i) == ' ') {
                    indexSpace = i;
                    break;
                }
            }
            indexSpace = (indexSpace > -1 && !isAllSpace(textLines.get(index), indexSpace)) ?
                    indexSpace : pageLength;
            textLines.add(index, textLines.get(index).substring(0, indexSpace));
            textLines.set(++index, textLines.get(index).substring(indexSpace));
            if (--flag > 0) {
                newline(index, textLines, pageLength, flag);
            }
        }
    }

    private boolean isAllSpace(String text, int indexSpace) {
        for (int i = 0; i < indexSpace; i++) {
            if (text.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    private int processImage(List<String> textLines, int index) {
        int i = textLines.get(index).indexOf("image://");
        if (i > 0) {
            String path = textLines.get(index).substring(i);
            textLines.set(index, textLines.get(index).substring(0, i));
            textLines.add(index + 1, path);
            index = index - 1;
            return index;
        }

        String path = textLines.get(index).substring(8, textLines.get(index).length() - 1);
        textLines.set(index, null);
        ImageIcon imageIcon = new ImageIcon(path);
        if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            return index;
        }
        if (imageIcon.getIconWidth() > pageWidth || imageIcon.getIconHeight() > pageHeight) {
            imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(pageWidth - 40, pageHeight, Image.SCALE_FAST));
        }
        int linesForImage = (imageIcon.getIconHeight() / lineHeight);
        int startImage = index;
        int endImage = index + linesForImage;
        int numberPage = index / linesPerPage;
        int endPage = (numberPage + 1) * linesPerPage;
        int offset = endImage - endPage;
        if (offset > -1) {
            startImage = startImage + linesForImage - offset;
            linesForImage = linesForImage + linesForImage - offset;
        }
        textLines.addAll(index + 1, Arrays.asList(new String[linesForImage]));
        textLines.set(startImage, "image://");
        images.put(startImage, imageIcon);
        return index + linesForImage;

    }
}
