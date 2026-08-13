package model;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import java.awt.Font;

public class StylepadDocument extends DefaultStyledDocument {
    private static final long serialVersionUID = 3718542859403270487L;
    private Font font;

    public StylepadDocument(StyleContext styleContext) {
        super(styleContext);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
