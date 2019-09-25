package org.california.buildergenerator.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import org.california.buildergenerator.fields.BuilderField;
import org.california.buildergenerator.fields.BuilderFieldsList;

import java.util.Arrays;

public class PluginBasicProperties {

    public final Project project;
    public final PsiFile psiFile;
    public final PsiClass psiClass;
    public final BuilderFieldsList fields;
    public boolean inner = true;


    public static PluginBasicProperties fromActionEvent(AnActionEvent e) {
        Project project = e.getProject();
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        PsiClass psiClass = Utils.getClass(psiFile);
        BuilderFieldsList fields = new BuilderFieldsList();
        Arrays.stream(psiClass.getAllFields()).map(BuilderField::new).forEach(fields::add);
        return new PluginBasicProperties(project, psiFile, psiClass, fields);
    }


    public PluginBasicProperties(Project project, PsiFile psiFile, PsiClass psiClass, BuilderFieldsList fields) {
        this.project = project;
        this.psiFile = psiFile;
        this.psiClass = psiClass;
        this.fields = fields;
    }

}
