package org.california.buildergenerator.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

public class StringUtils {

    public static String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String getSetterName(PsiField field) {
        String fieldName = field.getName();
        fieldName = capitalizeFirstLetter(fieldName);
        return "set" + fieldName;
    }

    public static String getNoSetterName(PsiField field) {
        String fieldName = field.getName();
        fieldName = capitalizeFirstLetter(fieldName);
        return "no" + fieldName;
    }

    public static String getInterfaceName(PsiField field) {
        String fieldName = field.getName();
        fieldName = capitalizeFirstLetter(fieldName);
        return fieldName + "Setter";
    }

    public static String getBuilderName(PsiClass javaFile) {
        return javaFile.getName() + "Builder";
    }


}
