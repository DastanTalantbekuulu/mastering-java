package font.listener;

import font.model.FontContainer;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FamilySelectionListener implements ListSelectionListener {
    private FontContainer fontContainer;

    public FamilySelectionListener() {
        fontContainer = FontContainer.getInstance();
    }

    public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
            fontContainer.setDefaultStyle();
        }
    }
}
