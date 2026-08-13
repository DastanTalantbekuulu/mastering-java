package com.itsallbinary.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create PDF file without any library from scratch.
 * 
 * @author itsallbinary
 *
 */
public class MinimalPDF {

	public static void main(String[] args) throws IOException {

		/**
		 * Create font object with times roman font & give it name as F1.
		 */
		PDFObject fontName = new PDFObject("Font");
		fontName.addKey("Subtype", "/Type1");
		fontName.addKey("BaseFont", "/Times-Roman");
		PDFObject font = new PDFObject("Font", new PDFObject("F1", fontName));

		/**
		 * Create text object with above font with size 18 & coordinate position (10,10)
		 * with text "Hello World"
		 */
		PDFObject text = new PDFObject(4, 0);
		text.addKey("Length", Integer.toString("Hello World".getBytes().length));
		text.addTextStream("F1", 18, 10, 50, "Hello World");

		/**
		 * Create page object with above font & text
		 */
		PDFObject page = new PDFObject(3, 0, "Page");
		page.addObjectKey("Resources", font);
		page.addObjectReferenceKey("Contents", text);

		/**
		 * Create pages object with above page.
		 */
		PDFObject pages = new PDFObject(2, 0, "Pages");
		pages.addKey("Count", "1");
		pages.addKey("MediaBox", "[0 0 300 144]");
		pages.addObjectReferenceArrayKey("Kids", page);

		page.addObjectReferenceKey("Parent", pages);

		/**
		 * Create root object wrapping pages object.
		 */
		PDFObject root = new PDFObject(1, 0, "Catalog");
		root.addObjectReferenceKey("Pages", pages);

		/**
		 * Create PDF with abvoe root & all of the objects
		 */
		PDF pdf = new PDF(root, pages, page, text);

		/**
		 * Write PDF to a file.
		 */
		FileWriter fileWriter = new FileWriter("generatedPDF.pdf");
		fileWriter.write(pdf.build());
		fileWriter.close();

	}

}

class PDF {

	private List<PDFObject> pdfObjects = new ArrayList<>();

	public PDF(PDFObject root, PDFObject... objects) {
		pdfObjects.add(root);
		pdfObjects.addAll(Arrays.asList(objects));
	}

	public String build() {
		StringBuilder pdf = new StringBuilder();
		pdf.append("%PDF-1.1\n\n");

		for (PDFObject pdfObject : pdfObjects) {
			pdf.append(pdfObject.build());
		}

		pdf.append("trailer\n  << /Root " + pdfObjects.get(0).getObjectReference() + "\n   /Size "
				+ (pdfObjects.size() + 1) + "\n  >>\n" + "%%EOF");

		return pdf.toString();
	}

}

class PDFObject {

	private String type;

	private int objectNumber;

	private int generation;

	private List<String> keys = new ArrayList<>();

	private String stream;

	public PDFObject(int objectNumber, int generation, String type) {
		this.type = type;
		this.objectNumber = objectNumber;
		this.generation = generation;
	}

	public PDFObject(int objectNumber, int generation) {
		this.objectNumber = objectNumber;
		this.generation = generation;
	}

	public PDFObject(String type) {
		this.type = type;

	}

	public PDFObject(String key, PDFObject obj) {
		addObjectKey(key, obj);
	}

	public void addKey(String key, String value) {
		keys.add("/" + key + " " + value);
	}

	public void addObjectReferenceKey(String key, PDFObject value) {
		keys.add("/" + key + " " + value.getObjectReference());
	}

	public void addObjectKey(String key, PDFObject value) {
		keys.add("/" + key + "\n" + value.buildObject().toString());
	}

	public void addObjectReferenceArrayKey(String key, PDFObject... values) {
		String finalVal = "/" + key + " [";
		for (PDFObject obj : values) {
			finalVal = finalVal + obj.getObjectReference();
		}
		finalVal = finalVal + "]";
		keys.add(finalVal);
	}

	public void addTextStream(String font, int fontSize, int xPos, int yPos, String text) {
		this.stream = "\nstream \n BT \n  /" + font + " " + fontSize + " Tf \n " + xPos + " " + yPos + " Td\n (" + text
				+ ") Tj\nET\nendstream\n";

	}

	public String getObjectReference() {
		return objectNumber + " " + generation + " R";
	}

	public StringBuilder build() {
		StringBuilder pdfObject = new StringBuilder();
		pdfObject.append(objectNumber).append(" ").append(generation).append(" obj\n  ").append(buildObject())
				.append("\nendobj\n\n");

		return pdfObject;
	}

	public StringBuilder buildObject() {
		StringBuilder pdfObject = new StringBuilder();
		pdfObject.append("<< ");
		if (type != null) {
			pdfObject.append("/Type /").append(type).append("\n");
		}
		for (String key : keys) {
			pdfObject.append("     ").append(key).append("\n");
		}
		pdfObject.append("  >>");

		if (stream != null) {
			pdfObject.append(stream);
		}

		return pdfObject;
	}
}
