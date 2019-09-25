package org.california.buildergenerator.dialog.fieldslist.disabled;

import com.intellij.psi.PsiField;

import javax.swing.*;
import java.util.Collection;
import java.util.HashSet;

public class DisabledFieldsListModel extends DefaultListModel<PsiField> {

    public DisabledFieldsListModel() {
        super();
    }

    public DisabledFieldsListModel(Collection<PsiField> fields) {
        fields.forEach(this::addElement);
    }

}
