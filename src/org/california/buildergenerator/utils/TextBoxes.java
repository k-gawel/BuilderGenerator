package org.california.buildergenerator.utils;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.*;
import org.california.buildergenerator.creators.BuilderCreator;
import org.california.buildergenerator.dialog.FieldsDialog;

import java.util.*;

public class TextBoxes extends AnAction {

    private PluginBasicProperties properties;

    public TextBoxes() {
        super("BU I L D E R");
    }


    @Override
    public void actionPerformed(AnActionEvent e) {
        this.properties = PluginBasicProperties.fromActionEvent(e);
        BuilderCreator builderCreator = new BuilderCreator(properties);
        Collection<PsiField> initialFields = Arrays.asList(properties.psiClass.getAllFields());

        FieldsDialog dialog = new FieldsDialog(initialFields, builderCreator);
        dialog.pack();
        dialog.setVisible(true);
    }


}
