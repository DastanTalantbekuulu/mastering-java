package font.dialog;

import font.listener.FamilySelectionListener;
import font.listener.FamilyTextFieldSearchListener;
import font.listener.FamilyTextFieldUpperCaseDocumentFilter;
import font.listener.FontChooserActionListener;
import font.listener.SizeSelectionListener;
import font.listener.SizeTextFieldListener;
import font.listener.StyleSelectionListener;
import font.model.FontContainer;
import viewer.Viewer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import java.awt.BorderLayout;
import java.awt.Font;

public class FontChooser {

    private static FontChooser INSTANCE;

    public static FontChooser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FontChooser();
        }
        return INSTANCE;
    }

    private JDialog fontChooser;
    private FontContainer fontContainer;
    private Viewer viewer;

    private FontChooser() {
        fontChooser = new JDialog();
        Position pos = new Position(700, 500);

        fontChooser.setTitle("Font Chooser");
        JLabel labelFamily = new JLabel("Family");
        labelFamily.setBounds(pos.labelFamily[0],
                pos.labelFamily[1],
                pos.labelFamily[2],
                pos.labelFamily[3]);

        JLabel labelStyle = new JLabel("Style");
        labelStyle.setBounds(pos.labelStyle[0],
                pos.labelStyle[1],
                pos.labelStyle[2],
                pos.labelStyle[3]);

        JLabel labelSize = new JLabel("Size");
        labelSize.setBounds(pos.labelSize[0],
                pos.labelSize[1],
                pos.labelSize[2],
                pos.labelSize[3]);

        fontContainer = FontContainer.getInstance();
        JLabel labelPreview = fontContainer.getLabelPreview();

        FamilyLabelListCellRenderer familyLabelListCellRenderer = new FamilyLabelListCellRenderer();
        fontContainer.getFamilyList().addListSelectionListener(new FamilySelectionListener());
        fontContainer.getFamilyList().setCellRenderer(familyLabelListCellRenderer);

        StyleLabelListCellRenderer styleLabelListCellRenderer = new StyleLabelListCellRenderer();
        fontContainer.getStyleList().addListSelectionListener(new StyleSelectionListener());
        fontContainer.getStyleList().setCellRenderer(styleLabelListCellRenderer);

        fontContainer.getSizeList().addListSelectionListener(new SizeSelectionListener());

        labelPreview.setText("AaBbCcDdEeFfJjKkLlNnOoPpQqSsTtUuVvWwXxYyZz");
        labelPreview.setBorder(new TitledBorder("Preview"));
        labelPreview.setBounds(pos.labelPreview[0],
                pos.labelPreview[1],
                pos.labelPreview[2],
                pos.labelPreview[3]);

        JScrollPane scrollPaneFamily = new JScrollPane(fontContainer.getFamilyList());
        fontContainer.getFamilyList().ensureIndexIsVisible(fontContainer.getFamilyList().getSelectedIndex());
        scrollPaneFamily.setBounds(pos.scrollPaneFamily[0],
                pos.scrollPaneFamily[1],
                pos.scrollPaneFamily[2],
                pos.scrollPaneFamily[3]);

        JScrollPane scrollPaneStyle = new JScrollPane(fontContainer.getStyleList());
        fontContainer.getStyleList().ensureIndexIsVisible(fontContainer.getStyleList().getSelectedIndex());
        scrollPaneStyle.setBounds(pos.scrollPaneStyle[0],
                pos.scrollPaneStyle[1],
                pos.scrollPaneStyle[2],
                pos.scrollPaneStyle[3]);

        JScrollPane scrollPaneSize = new JScrollPane(fontContainer.getSizeList());
        fontContainer.getSizeList().ensureIndexIsVisible(fontContainer.getSizeList().getSelectedIndex());
        scrollPaneSize.setBounds(pos.scrollPaneSize[0],
                pos.scrollPaneSize[1],
                pos.scrollPaneSize[2],
                pos.scrollPaneSize[3]);

        JTextField textFieldFamily = new JTextField();
        textFieldFamily.getDocument().addDocumentListener(new FamilyTextFieldSearchListener());
        ((AbstractDocument) textFieldFamily.getDocument()).setDocumentFilter(new FamilyTextFieldUpperCaseDocumentFilter());
        textFieldFamily.setBounds(pos.textFieldFamily[0],
                pos.textFieldFamily[1],
                pos.textFieldFamily[2],
                pos.textFieldFamily[3]);

        JTextField textFieldSize = new JTextField();
        textFieldSize.setDocument(new NumericDocument());
        textFieldSize.getDocument().addDocumentListener(
                new SizeTextFieldListener());
        textFieldSize.setBounds(pos.textFieldSize[0],
                pos.textFieldSize[1],
                pos.textFieldSize[2],
                pos.textFieldSize[3]);

        FontChooserActionListener fontChooserActionListener = new FontChooserActionListener(this);
        fontChooser.addWindowListener(fontChooserActionListener);

        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(fontChooserActionListener);
        buttonOK.setActionCommand("OK");
        buttonOK.setBounds(pos.buttonOK[0],
                pos.buttonOK[1],
                pos.buttonOK[2],
                pos.buttonOK[3]);

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(fontChooserActionListener);
        buttonCancel.setActionCommand("Cancel");
        buttonCancel.setBounds(pos.buttonCancel[0],
                pos.buttonCancel[1],
                pos.buttonCancel[2],
                pos.buttonCancel[3]);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(labelFamily);
        panel.add(labelStyle);
        panel.add(labelSize);
        panel.add(labelPreview);
        panel.add(textFieldFamily);
        panel.add(textFieldSize);
        panel.add(scrollPaneFamily);
        panel.add(scrollPaneStyle);
        panel.add(scrollPaneSize);
        panel.add(buttonOK);
        panel.add(buttonCancel);

        fontChooser.add(panel, BorderLayout.CENTER);
        fontChooser.setBounds(100, 100, pos.width, pos.height);
        fontChooser.setResizable(false);
        fontChooser.setVisible(false);
        fontChooser.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public Font getFont() {
        return fontContainer.getFont();
    }

    public void setComponent(Viewer viewer) {
        this.viewer = viewer;
        cancel();
    }

    public void set() {
        if (viewer != null) {
            viewer.getTextPane().setFont(fontContainer.getFont());
            viewer.change();
        }
    }

    public void cancel() {
        if (viewer != null) {
            fontContainer.setFont(viewer.getTextPane().getFont());
        }
    }

    public void setVisible(boolean b) {
        fontChooser.setVisible(b);
    }

}
