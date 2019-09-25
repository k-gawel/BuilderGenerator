package org.california.buildergenerator.dialog.fieldslist.disabled;

import com.intellij.psi.PsiField;
import org.california.buildergenerator.dialog.FieldsDialog;
import org.california.buildergenerator.dialog.fieldslist.active.ActiveFieldsList;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.HashSet;

public class DisabledFieldsList extends JList<PsiField> {

    private ActiveFieldsList activeFieldsList;

    public DisabledFieldsList() {
        super(new DisabledFieldsListModel());
        setCellRenderer(new DisabledFieldsListCellRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setFieldsDialog(FieldsDialog fieldsDialog) {
        addListSelectionListener(new DisabledFieldsSelectionListener(fieldsDialog));
    }

    public ActiveFieldsList getActiveFieldsList() {
        return activeFieldsList;
    }

    public void setActiveFieldsList(ActiveFieldsList activeFieldsList) {
        this.activeFieldsList = activeFieldsList;
    }

    @Override
    public void setModel(ListModel<PsiField> model) {
        if (!(model instanceof DisabledFieldsListModel))
            throw new IllegalArgumentException("Wrong type of model");

        super.setModel(model);
    }

    @Override
    public DisabledFieldsListModel getModel() {
        return (DisabledFieldsListModel) super.getModel();
    }


}
