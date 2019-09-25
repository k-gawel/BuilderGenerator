package org.california.buildergenerator.dialog.fieldslist.active;

import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.ui.JBColor;
import org.california.buildergenerator.dialog.fieldslist.FieldsListCellRenderer;
import org.california.buildergenerator.fields.BuilderField;

import javax.swing.*;
import java.awt.*;

public class ActiveFieldsListCellRenderer extends FieldsListCellRenderer {

    public ActiveFieldsListCellRenderer() {
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        BuilderField builderField = (BuilderField) value;
        JPanel panel = getPanel(builderField);
        setBackground(panel, isSelected);
        return panel;
    }

    private JPanel getPanel(BuilderField builderField) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setOpaque(true);

        JLabel nameLabel = getNameLabel(builderField.field);
        JLabel statusLabel = new JLabel(builderField.status.toString());

        panel.add(nameLabel);
        panel.add(statusLabel);
        return panel;
    }


}
