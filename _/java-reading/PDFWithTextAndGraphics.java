package com.itsallbinary.utility.pdfgraphics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create PDF file without any library from scratch.
 * 
 * Include multiple pages, text and graphics
 * 
 * @author itsallbinary
 *
 */
public class PDFWithTextAndGraphics {

	public static void main(String[] args) throws IOException {

		/*
		 * Create text stream with few lines
		 */
		TextStreamObject textStreamObject = new TextStreamObject("F1", 18, 30, 100, "Hello World");
		textStreamObject.add("F1", 11, 30, 40, "Hope you all are enjoying Its All Binary articles!");
		textStreamObject.add("F1", 11, 30, 30, "Now let's create PDF with 3 pages, texts & graphics.");

		/*
		 * First page with above text stream
		 */
		PageObject page1 = new PageObject();
		page1.addAttribute("Resources", new FontObject("F1", "Times-Roman"));
		page1.addContent(textStreamObject);
		page1.addAttribute("MediaBox", "[0 0 300 200]");

		/*
		 * Create graphic stream with few graphics.
		 */
		GraphicStreamObject graphicStreamObject = new GraphicStreamObject();
		graphicStreamObject.addFilledRectangle(100, 600, 50, 75, "0.75 g");
		graphicStreamObject.addLine(100, 100, 400, 500);

		/*
		 * Second page with above graphics
		 */
		PageObject page2 = new PageObject();
		page2.addContent(graphicStreamObject);

		/*
		 * Create curve & color graphics.
		 */
		GraphicStreamObject graphicCurveStreamObject = new GraphicStreamObject();
		graphicCurveStreamObject.addBezierCurve(300, 300, 300, 400, 400, 400, 400, 300, "0.0 0.0 0.5", 10,
				"0.5 0.1 0.2");

		/*
		 * Third page with above curve & color graphics.
		 */
		PageObject page3 = new PageObject();
		page3.addContent(graphicCurveStreamObject);

		/*
		 * Prepare pages & catalog objects.
		 */
		PageCollectionObject pageCollectionObject = new PageCollectionObject();
		pageCollectionObject.addPages(page1, page2, page3);
		CatalogObject catalogObject = new CatalogObject(pageCollectionObject);

		/*
		 * Build final PDF.
		 */
		PDF pdf = new PDF(catalogObject);

		/*
		 * Write PDF to a file.
		 */
		FileWriter fileWriter = new FileWriter("generatedPDFWithGraphics.pdf");
		fileWriter.write(pdf.build());
		fileWriter.close();

	}

}

/**
 * Representation of entire PDF file.
 *
 */
class PDF {

	private CatalogObject catalogObject;

	private int objectCount = 0;

	public PDF(CatalogObject catalogObject) {
		this.catalogObject = catalogObject;
	}

	public String build() {
		populateObjectNumbers();
		StringBuilder pdf = new StringBuilder();
		pdf.append("%PDF-1.1\n\n");

		pdf.append(catalogObject.build());
		pdf.append(catalogObject.getPages().build());

		for (PageObject page : catalogObject.getPages().getPages()) {
			pdf.append(page.build());
			if (page.getContent() != null) {
				pdf.append(page.getContent().build());
			}
		}

		pdf.append("trailer\n  << /Root " + catalogObject.getReference().getObjectNumber() + " "
				+ catalogObject.getReference().getGeneration() + " R" + "\n   /Size " + (objectCount + 1) + "\n  >>\n"
				+ "%%EOF");

		return pdf.toString();
	}

	private void populateObjectNumbers() {
		catalogObject.setObjectNumber(++objectCount);
		catalogObject.getPages().setObjectNumber(++objectCount);

		for (PageObject page : catalogObject.getPages().getPages()) {
			page.setObjectNumber(++objectCount);

			if (page.getContent() != null) {
				page.getContent().setObjectNumber(++objectCount);

			}
		}
	}

}

/**
 * Representation of reference to any PDF object.
 *
 */
class PDFObjectReference {
	private int objectNumber;

	private int generation = 0; // Hardcode as it remains same always

	int getObjectNumber() {
		return objectNumber;
	}

	int getGeneration() {
		return generation;
	}

	void setObjectNumber(int objectNumber) {
		this.objectNumber = objectNumber;
	}

}

/**
 * Abstract Representation of PDF objects. All objects in PDF must extend this.
 *
 */
abstract class PDFObject {

	private PDFObjectReference reference = new PDFObjectReference();

	private Map<String, Object> attributes = new HashMap<>();

	public PDFObject(String type) {
		super();
		this.attributes.put("Type", type);
	}

	public void addAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}

	public abstract void addSpecificAttributes();

	public String build() {

		addSpecificAttributes();

		StringBuilder pdfObject = new StringBuilder();
		pdfObject.append(reference.getObjectNumber()).append(" ").append(reference.getGeneration()).append(" obj\n  ")
				.append(buildObject()).append("\nendobj\n\n");

		return pdfObject.toString();
	}

	public StringBuilder buildObject() {
		StringBuilder pdfObject = new StringBuilder();
		pdfObject.append("<< \n");

		for (String key : attributes.keySet()) {

			Object value = attributes.get(key);
			if (value instanceof String) {
				pdfObject.append("\n     /").append(key).append(" ").append(((String) value).contains("[") ? "" : "/")
						.append(value);
			} else if (value instanceof Integer) {
				pdfObject.append("\n     /").append(key).append(" ").append(value);
			} else if (value instanceof PDFObject) {
				pdfObject.append("\n     /").append(key).append(" \n").append(((PDFObject) value).buildObject());
			} else if (value instanceof PDFObjectReference[]) {

				pdfObject.append("\n     /").append(key).append(" [");
				for (PDFObjectReference ref : (PDFObjectReference[]) value) {
					pdfObject.append(ref.getObjectNumber() + " " + ref.getGeneration() + " R ");
				}
				pdfObject.append("]");
			} else if (value instanceof PDFObjectReference) {
				pdfObject.append("\n     /").append(key).append(" ")
						.append(((PDFObjectReference) value).getObjectNumber() + " "
								+ ((PDFObjectReference) value).getGeneration() + " R ");
			}
		}
		pdfObject.append("  >>");

		return pdfObject;
	}

	public void setObjectNumber(int objectNumber) {
		this.reference.setObjectNumber(objectNumber);
	}

	PDFObjectReference getReference() {
		return reference;
	}

}

/**
 * Representation of catalog object
 *
 */
class CatalogObject extends PDFObject {

	private PageCollectionObject pages;

	public CatalogObject(PageCollectionObject pageCollectionObject) {
		super("Catalog");
		this.pages = pageCollectionObject;
	}

	@Override
	public void addSpecificAttributes() {
		addAttribute("Pages", pages.getReference());
	}

	PageCollectionObject getPages() {
		return pages;
	}

}

/**
 * Representation of page object.
 *
 */
class PageObject extends PDFObject {

	private StreamObject content;

	public PageObject() {
		super("Page");
	}

	public void addContent(StreamObject streamObject) {
		content = streamObject;
	}

	@Override
	public void addSpecificAttributes() {
		addAttribute("Contents", content.getReference());
	}

	StreamObject getContent() {
		return content;
	}

}

/**
 * Representation of pages object
 *
 */
class PageCollectionObject extends PDFObject {

	private List<PageObject> pages = new ArrayList<>();

	public PageCollectionObject() {
		super("Pages");
	}

	public void addPages(PageObject... pageObjects) {
		for (PageObject pageObject : pageObjects) {
			addPage(pageObject);
		}
	}

	public void addPage(PageObject pageObject) {
		this.pages.add(pageObject);
		pageObject.addAttribute("Parent", getReference());
	}

	@Override
	public void addSpecificAttributes() {
		addAttribute("Count", Integer.valueOf(pages.size()));
		PDFObjectReference[] refArr = new PDFObjectReference[pages.size()];
		for (int i = 0; i < pages.size(); i++) {
			refArr[i] = pages.get(i).getReference();
		}
		addAttribute("Kids", refArr);
	}

	List<PageObject> getPages() {
		return pages;
	}

}

/**
 * Representation of font object
 *
 */
class FontObject extends PDFObject {

	public FontObject(String fontAliasName, String fontName) {
		super(null);

		PDFObject fontDef = new PDFObject("Font") {
			@Override
			public void addSpecificAttributes() {
				addAttribute("Subtype", "Type1");
				addAttribute("BaseFont", fontName);
			}
		};
		fontDef.addSpecificAttributes();

		PDFObject fontAlias = new PDFObject(null) {
			@Override
			public void addSpecificAttributes() {
				addAttribute(fontAliasName, fontDef);
			}
		};
		fontAlias.addSpecificAttributes();

		addAttribute("Font", fontAlias);
	}

	@Override
	public void addSpecificAttributes() {

	}

}

/**
 * Abstract Representation of stream object
 *
 */
abstract class StreamObject extends PDFObject {

	public StreamObject() {
		super(null);
	}

	public abstract String buildStream();

	public void addSpecificAttributes() {
		addAttribute("Length", Integer.valueOf(100));
	}

	@Override
	public StringBuilder buildObject() {
		StringBuilder sb = super.buildObject();
		sb.append("\nstream").append(buildStream()).append("\nendstream");
		return sb;
	}

}

/**
 * Representation of text stream object
 *
 */
class TextStreamObject extends StreamObject {

	private static final String BEGIN_TEXT = "BT";
	private static final String END_TEXT = "ET";
	private static final String TEXT_FONT = "Tf";
	private static final String TEXT_OFFSET = "Td";
	private static final String SHOW_TEXT = "Tj";

	private List<String> texts = new ArrayList<>();

	public TextStreamObject(String fontAlias, int fontSize, int xPos, int yPos, String text) {
		add(fontAlias, fontSize, xPos, yPos, text);

	}

	public void add(String fontAlias, int fontSize, int xPos, int yPos, String text) {
		this.texts.add(" \n " + BEGIN_TEXT + " \n  /" + fontAlias + " " + fontSize + " " + TEXT_FONT + " \n " + xPos
				+ " " + yPos + " " + TEXT_OFFSET + "\n (" + text + ") " + SHOW_TEXT + "\n" + END_TEXT + "\n");
	}

	@Override
	public String buildStream() {
		return texts.stream().collect(Collectors.joining());
	}
}

/**
 * Representation of graphics stream object
 *
 */
class GraphicStreamObject extends StreamObject {

	private static final String MOVE_POINTER = "m";
	private static final String LINE = "l";
	private static final String LINE_WIDTH = "w";
	private static final String RECTANGLE = "re";
	private static final String FILL = "f";
	private static final String BEZIER_CURVE = "c";
	private static final String BORDER_COLOR = "rg";
	private static final String FILL_COLOR = "RG";
	private static final String STROKE = "S";
	private static final String CLOSE_FILL_STROKE = "b";

	private List<String> graphics = new ArrayList<>();

	public void addLine(int xFrom, int yFrom, int xTo, int yTo) {
		this.graphics.add(
				"\n " + xFrom + " " + yFrom + " " + MOVE_POINTER + " " + xTo + " " + yTo + " " + LINE + " " + STROKE);
	}

	public void addRectangle(int a, int b, int c, int d) {
		this.graphics.add("\n " + a + " " + b + " " + c + " " + d + " " + RECTANGLE + " " + STROKE);
	}

	public void addFilledRectangle(int a, int b, int c, int d, String color) {
		this.graphics.add("\n" + color);
		this.graphics.add("\n " + a + " " + b + " " + c + " " + d + " " + RECTANGLE + " " + FILL + " " + STROKE);
	}

	public void addBezierCurve(int movex, int movey, int a, int b, int c, int d, int e, int f, String borderColor,
			int borderWidth, String fillColor) {
		this.graphics.add("\n" + borderWidth + " " + LINE_WIDTH);
		this.graphics.add("\n" + fillColor + " " + FILL_COLOR);
		this.graphics.add("\n" + borderColor + " " + BORDER_COLOR);
		this.graphics.add("\n" + movex + " " + movey + " " + MOVE_POINTER);
		this.graphics.add("\n " + a + " " + b + " " + c + " " + d + " " + e + " " + f + " " + BEZIER_CURVE + " \n "
				+ CLOSE_FILL_STROKE);
	}

	@Override
	public String buildStream() {
		return graphics.stream().collect(Collectors.joining());
	}

}
