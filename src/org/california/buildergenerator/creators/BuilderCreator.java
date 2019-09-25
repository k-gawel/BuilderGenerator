package org.california.buildergenerator.creators;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import org.california.buildergenerator.fields.BuilderField;
import org.california.buildergenerator.utils.PluginBasicProperties;

import java.util.List;

public class BuilderCreator extends AbstractCreator {

    public BuilderCreator(PluginBasicProperties properties) {
        super(properties);
    }

    public void setFields(List<BuilderField> fields) {
        this.fields.addAll(fields);
    }

    public void setFieldsAndCreate(List<BuilderField> fields, boolean inner) {
        logger.info("CREATE WITH FIELDS: ");
        fields.forEach(System.out::println);
        setFields(fields);
        pros.inner = inner;
        create();
    }

    private void create() {
        if (fields == null) throw new IllegalStateException("Fields are null");

        if (pros.inner)
            createInnerBuilder();
        else
            createStandAloneBuilder();
    }

    private void createInnerBuilder() {
        PsiClass builderClass = new BuilderClassCreator(this).createClass();

        WriteCommandAction.runWriteCommandAction(pros.project, () -> {
            CodeStyleManager.getInstance(pros.project).reformat(psiClass, true);
            psiClass.add(builderClass);
        });
    }

    private void createStandAloneBuilder() {
        PsiJavaFile builderClassFile = new BuilderFileCreator(this).createFile();
        PsiClass builderClass = new BuilderClassCreator(builderClassFile, this).createClass();
        builderClassFile.add(builderClass);

        WriteCommandAction.runWriteCommandAction(pros.project, () -> {
            CodeStyleManager.getInstance(pros.project).reformat(builderClassFile, true);
            getDirectory().add(builderClassFile);
        });
    }

    private PsiDirectory getDirectory() {
        return javaFile.getContainingDirectory();
    }

}
