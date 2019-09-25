package org.california.buildergenerator.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

public class Utils {

    public static PsiElementFactory getElementFactory(PluginBasicProperties properties) {
        return getElementFactory(properties.project);
    }

    public static PsiElementFactory getElementFactory(Project project) {
        return JavaPsiFacade.getElementFactory(project);
    }


    public static PsiClass getClass(PsiFile file) {
        PsiElement element = file.getFirstChild();
        while (element != null) {
            if (element instanceof PsiClass)
                return (PsiClass) element;
            element = element.getNextSibling();
        }
        throw new IllegalStateException("No class was found");
    }

    public static PsiClassType getPsiClassType(PluginBasicProperties properties, PsiClass psiClass) {
        return getElementFactory(properties).createType(psiClass);
    }


    public static void printAllChildren(PsiElement element) {
        System.out.println("PRINT: " + element + " CHILDREN: START");
        PsiElement child = element.getFirstChild();

        while (child != null) {
            System.out.println("CHILD: " + child);
            child = child.getNextSibling();
        }

        System.out.println("PRINT: " + element + ": ENDED");
    }


}
