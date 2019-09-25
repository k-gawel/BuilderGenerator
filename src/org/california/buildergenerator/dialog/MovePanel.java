package org.california.buildergenerator.dialog;

import com.intellij.psi.PsiField;
import org.california.buildergenerator.dialog.fieldslist.active.ActiveFieldsList;
import org.california.buildergenerator.dialog.fieldslist.disabled.DisabledFieldsList;
import org.california.buildergenerator.fields.BuilderField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovePanel extends JComponent {

    private JPanel contentPanel;

    private BuilderField activeField;
    private PsiField disabledField;

    private ActiveFieldsList activeFieldsList;
    private DisabledFieldsList disabledFieldsList;

    private JButton activateButton;
    private JButton disableButton;
    private JCheckBox required;
    private JCheckBox innerBuilderCheckBox;
    private JPanel outsidePanel;

    public MovePanel() {
        innerBuilderCheckBox.setSelected(true);

        setEnableButtonsProperty();
        activateButton.addActionListener(e -> {
            disabledFieldsList.getModel().removeElement(disabledField);
            activeFieldsList.getModel().addElement(new BuilderField(disabledField));
            clearField();
        });

        disableButton.addActionListener(e -> {
            System.out.println("Disabling " + activeField + " list Size: " + activeFieldsList.getModel().size());
            disabledFieldsList.getModel().addElement(activeField.field);
            activeFieldsList.getModel().removeElement(activeField);
            System.out.println("Disabled " + activeField + " list size " + activeFieldsList.getModel().size());
            clearField();
        });

        required.addItemListener(i -> {
            System.out.println("CHECKBOX CLICKED value: " + required.isSelected());
            activeField.setOptional(!required.isSelected());
            System.out.println(activeField);
        });
    }

    public boolean isInner() {
        return innerBuilderCheckBox.isSelected();
    }

    public void setLists(ActiveFieldsList activeFieldsList, DisabledFieldsList disabledFieldsList) {
        this.activeFieldsList = activeFieldsList;
        this.disabledFieldsList = disabledFieldsList;
    }

    public void clearField() {
        this.activeField = null;
        this.disabledField = null;
        setEnableButtonsProperty();
    }

    public void setField(BuilderField builderField) {
        activeField = builderField;
        disabledField = null;
        setEnableButtonsProperty();
    }

    public void setField(PsiField psiField) {
        activeField = null;
        disabledField = psiField;
        setEnableButtonsProperty();
    }

    private void setEnableButtonsProperty() {
        activateButton.setEnabled(false);
        disableButton.setEnabled(false);
        required.setEnabled(false);
        if (activeField != null) {
            disableButton.setEnabled(true);
            required.setEnabled(true);
            required.setSelected(!activeField.isOptional());
        }
        if (disabledField != null) {
            activateButton.setEnabled(true);
            required.setSelected(true);
        }
    }


}
