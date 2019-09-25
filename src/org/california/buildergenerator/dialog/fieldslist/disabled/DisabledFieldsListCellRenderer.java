package org.california.buildergenerator.dialog.fieldslist.disabled;

import com.intellij.psi.PsiField;
import org.california.buildergenerator.dialog.fieldslist.FieldsListCellRenderer;

import javax.swing.*;
import java.awt.*;

public class DisabledFieldsListCellRenderer extends FieldsListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        PsiField psiField = (PsiField) value;
        JLabel jLabel = getNameLabel(psiField);
        setBackground(jLabel, isSelected);
        return jLabel;
    }

}
