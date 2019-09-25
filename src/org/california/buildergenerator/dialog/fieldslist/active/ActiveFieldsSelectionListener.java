package org.california.buildergenerator.dialog.fieldslist.active;

import org.california.buildergenerator.dialog.FieldsDialog;
import org.california.buildergenerator.dialog.MovePanel;
import org.california.buildergenerator.dialog.fieldslist.disabled.DisabledFieldsList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ActiveFieldsSelectionListener implements ListSelectionListener {

    MovePanel movePanel;

    public ActiveFieldsSelectionListener(FieldsDialog fieldsDialog) {
        this.movePanel = fieldsDialog.getMovePanel();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ActiveFieldsList activeFieldsList = (ActiveFieldsList) e.getSource();
        DisabledFieldsList disabledFieldsList = activeFieldsList.getDisabledFieldsList();
        disabledFieldsList.clearSelection();
        movePanel.setField(activeFieldsList.getSelectedValue());
    }


}
