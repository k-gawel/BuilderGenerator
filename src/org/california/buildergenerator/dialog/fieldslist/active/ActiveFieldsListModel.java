package org.california.buildergenerator.dialog.fieldslist.active;

import com.intellij.psi.PsiField;
import org.california.buildergenerator.fields.BuilderField;
import org.california.buildergenerator.fields.BuilderFieldsList;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.Collection;

public class ActiveFieldsListModel extends DefaultListModel<BuilderField> {


    public ActiveFieldsListModel(BuilderFieldsList fields) {
        fields.stream().filter(f -> f.field != null).forEach(this::addElement);
    }

    public ActiveFieldsListModel(Collection<PsiField> fields) {
        this(new BuilderFieldsList(fields));
    }

}
