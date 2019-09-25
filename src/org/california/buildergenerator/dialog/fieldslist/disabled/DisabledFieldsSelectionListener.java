package org.california.buildergenerator.dialog.fieldslist.disabled;

import org.california.buildergenerator.dialog.FieldsDialog;
import org.california.buildergenerator.dialog.MovePanel;
import org.california.buildergenerator.dialog.fieldslist.active.ActiveFieldsList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DisabledFieldsSelectionListener implements ListSelectionListener {

    MovePanel movePanel;

    public DisabledFieldsSelectionListener(FieldsDialog fieldsDialog) {
        this.movePanel = fieldsDialog.getMovePanel();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        DisabledFieldsList disabledFieldsList = (DisabledFieldsList) e.getSource();
        ActiveFieldsList activeFieldsList = disabledFieldsList.getActiveFieldsList();
        activeFieldsList.clearSelection();
        movePanel.setField(disabledFieldsList.getSelectedValue());
    }
}
