package org.california.buildergenerator.dialog.fieldslist;

import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public abstract class FieldsListCellRenderer extends DefaultListCellRenderer {

    protected void setBackground(Component component, boolean isSelected) {
        if (isSelected) {
            component.setBackground(JBColor.CYAN);
            component.setForeground(JBColor.WHITE);
        } else {
            component.setBackground(JBColor.WHITE);
            component.setForeground(JBColor.BLACK);
        }
    }

    protected JLabel getNameLabel(PsiField field) {
        String name = field.getName();
        String type = field.getTypeElement().getText();
        Icon fieldIcon = AllIcons.Nodes.Field;
        Icon accessIcon = getIcon(field);

        JLabel fieldIconLabel = new JLabel();
        fieldIconLabel.setIcon(fieldIcon);
        JLabel accessIconLabel = new JLabel();
        accessIconLabel.setIcon(accessIcon);
        JLabel textLabel = new JLabel();
        textLabel.setText(name + " : " + type);

        JLabel nameLabel = new JLabel();
        nameLabel.setLayout(new BoxLayout(nameLabel, BoxLayout.X_AXIS));
        nameLabel.add(fieldIconLabel);
        nameLabel.add(accessIconLabel);
        nameLabel.add(textLabel);

        return nameLabel;
    }

    private Icon getIcon(PsiField field) {
        PsiModifierList list = field.getModifierList();

        if (list.hasModifierProperty(PsiModifier.PUBLIC))
            return AllIcons.Nodes.C_public;
        else if (list.hasModifierProperty(PsiModifier.PRIVATE))
            return AllIcons.Nodes.C_private;
        else if (list.hasModifierProperty(PsiModifier.PACKAGE_LOCAL))
            return AllIcons.Nodes.C_plocal;
        else if (list.hasModifierProperty(PsiModifier.PROTECTED))
            return AllIcons.Nodes.C_protected;
        else
            throw new IllegalStateException(field.toString() + " has no modifer on its list " + list.toString());
    }


}
