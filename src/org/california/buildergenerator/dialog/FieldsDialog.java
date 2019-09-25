package org.california.buildergenerator.dialog;

import com.intellij.psi.PsiField;
import org.california.buildergenerator.creators.BuilderCreator;
import org.california.buildergenerator.dialog.fieldslist.active.ActiveFieldsList;
import org.california.buildergenerator.dialog.fieldslist.active.ActiveFieldsListModel;
import org.california.buildergenerator.dialog.fieldslist.disabled.DisabledFieldsList;
import org.california.buildergenerator.fields.BuilderFieldsList;

import javax.swing.*;
import java.util.Collection;

public class FieldsDialog extends JDialog {

    private JPanel contentPane;

    private JButton buttonOK;
    private JButton buttonCancel;

    private DisabledFieldsList disabledFields;
    private ActiveFieldsList activeFields;
    private MovePanel movePanel;


    public FieldsDialog(Collection<PsiField> initialFields, BuilderCreator builderCreator) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.activeFields.setModel(new ActiveFieldsListModel(initialFields));
        this.activeFields.setFieldsDialog(this);
        this.activeFields.setDisabledFieldsList(this.disabledFields);

        this.disabledFields.setFieldsDialog(this);
        this.disabledFields.setActiveFieldsList(this.activeFields);

        this.movePanel.setLists(this.activeFields, this.disabledFields);

        buttonOK.addActionListener(e -> {
            builderCreator.setFieldsAndCreate(activeFields.getAllElements(), movePanel.isInner());
            setVisible(false);
        });
    }


    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    private void createUIComponents() {
    }

    public MovePanel getMovePanel() {
        return movePanel;
    }
}
