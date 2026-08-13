package stylepad;

import stylepad.model.Paragraph;
import stylepad.model.Run;

import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Wonderland {
    private DefaultStyledDocument doc;
    private StyleContext styles;
    private HashMap<String, Style> runAttr;
    public Wonderland(DefaultStyledDocument doc, StyleContext styles) {
        this.doc = doc;
        this.styles = styles;
        runAttr = new HashMap<>();
    }
    public void loadDocument() {
        createStyles();
        for (int i = 0; i < Book.wonderland.length; i++) {
            Paragraph p = Book.wonderland[i];
            addParagraph(p);
        }
    }
    public void addParagraph(Paragraph p) {
        try {
            Style s;
            for (int i = 0; i < p.data.length; i++) {
                Run run = p.data[i];
                s = runAttr.get(run.attribute);
                doc.insertString(doc.getLength(), run.content, s);
            }
            Style ls = styles.getStyle(p.logical);
            doc.setLogicalStyle(doc.getLength() - 1, ls);
            doc.insertString(doc.getLength(), "\n", null);
        } catch (BadLocationException e) {
            System.err.println("Internal error: " + e);
        }
    }
    public void createStyles() {
        Style style = styles.addStyle(null, null);
        runAttr.put("none", style);
        style = styles.addStyle(null, null);
        StyleConstants.setItalic(style, true);
        StyleConstants.setForeground(style, new Color(153, 153, 102));
        runAttr.put("cquote", style);
        style = styles.addStyle(null, null);
        StyleConstants.setItalic(style, true);
        StyleConstants.setForeground(style, new Color(51, 102, 153));
        runAttr.put("aquote", style);
        try {
            ResourceBundle resources = ResourceBundle.getBundle("resources.Stylepad", Locale.getDefault());
            style = styles.addStyle(null, null);
            Icon alice = new ImageIcon(Wonderland.class.getResource(resources.getString("aliceGif")));
            StyleConstants.setIcon(style, alice);
            runAttr.put("alice", style);
            style = styles.addStyle(null, null);
            Icon caterpillar = new ImageIcon(Wonderland.class.getResource(resources.getString("caterpillarGif")));
            StyleConstants.setIcon(style, caterpillar);
            runAttr.put("caterpillar", style);
            style = styles.addStyle(null, null);
            Icon hatter = new ImageIcon(Wonderland.class.getResource(resources.getString("hatterGif")));
            StyleConstants.setIcon(style, hatter);
            runAttr.put("hatter", style);
        } catch (MissingResourceException mre) {
        }
        Style defaultStyle = styles.getStyle(StyleContext.DEFAULT_STYLE);
        Style heading = styles.addStyle("heading", defaultStyle);
        StyleConstants.setFontFamily(heading, "SansSerif");
        StyleConstants.setBold(heading, true);
        StyleConstants.setAlignment(heading, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceAbove(heading, 10);
        StyleConstants.setSpaceBelow(heading, 10);
        StyleConstants.setFontSize(heading, 18);
        Style sty = styles.addStyle("title", heading);
        StyleConstants.setFontSize(sty, 32);
        sty = styles.addStyle("edition", heading);
        StyleConstants.setFontSize(sty, 16);
        sty = styles.addStyle("author", heading);
        StyleConstants.setItalic(sty, true);
        StyleConstants.setSpaceBelow(sty, 25);
        sty = styles.addStyle("subtitle", heading);
        StyleConstants.setSpaceBelow(sty, 35);
        sty = styles.addStyle("normal", defaultStyle);
        StyleConstants.setLeftIndent(sty, 10);
        StyleConstants.setRightIndent(sty, 10);
        StyleConstants.setFontFamily(sty, "SansSerif");
        StyleConstants.setFontSize(sty, 14);
        StyleConstants.setSpaceAbove(sty, 4);
        StyleConstants.setSpaceBelow(sty, 4);
    }
}
