package org.california.buildergenerator.dialog.fieldslist.active;

import org.california.buildergenerator.dialog.FieldsDialog;
import org.california.buildergenerator.dialog.fieldslist.disabled.DisabledFieldsList;
import org.california.buildergenerator.fields.BuilderField;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ActiveFieldsList extends JList<BuilderField> {

    private FieldsDialog fieldsDialog;

    private DisabledFieldsList disabledFieldsList;


    public ActiveFieldsList() {
        super(new ActiveFieldsListModel(new HashSet<>()));
        setCellRenderer(new ActiveFieldsListCellRenderer());
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    public DisabledFieldsList getDisabledFieldsList() {
        return disabledFieldsList;
    }

    public void setDisabledFieldsList(DisabledFieldsList disabledFieldsList) {
        this.disabledFieldsList = disabledFieldsList;
    }

    public void setFieldsDialog(FieldsDialog fieldsDialog) {
        this.fieldsDialog = fieldsDialog;
        addListSelectionListener(new ActiveFieldsSelectionListener(this.fieldsDialog));
    }

    @Override
    public void setModel(ListModel<BuilderField> listModel) {
        if (!(listModel instanceof ActiveFieldsListModel))
            throw new IllegalArgumentException("Wrong model type");

        super.setModel(listModel);
    }

    @Override
    public ActiveFieldsListModel getModel() {
        return (ActiveFieldsListModel) super.getModel();
    }


    public List<BuilderField> getAllElements() {
        return IntStream.range(0, getModel().getSize()).mapToObj(getModel()::getElementAt).collect(Collectors.toList());
    }

}
