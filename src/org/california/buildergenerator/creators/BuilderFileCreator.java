package org.california.buildergenerator.creators;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.psi.util.PsiTreeUtil;
import org.california.buildergenerator.utils.PluginBasicProperties;

import java.util.Collection;
import java.util.HashSet;

public class BuilderFileCreator extends AbstractCreator {


    public BuilderFileCreator(PluginBasicProperties properties) {
        super(properties);
    }

    public BuilderFileCreator(AbstractCreator creator) {
        super(creator);
    }

    public PsiJavaFile createFile() {
        logger.info("Creating builder class file PsiJavaFile: {}", javaFile);
        PsiFileFactory factory = PsiFileFactory.getInstance(pros.project);
        logger.info("PsiFileFactory: {} ", factory);
        String fileName = getBuilderFileName();
        PsiJavaFile builderFile = (PsiJavaFile) factory.createFileFromText(fileName, JavaFileType.INSTANCE, "");
        logger.info("BuilderField: {}", builderFile);
        getImports().forEach(builderFile.getImportList()::add);
        return builderFile;
    }

    private String getBuilderFileName() {
        logger.info("Getting builder name from class: {}", psiClass);
        String className = psiClass.getName();
        logger.info("Class anme: {}", className);
        String builderClassName = className + "Builder";
        logger.info("BuilderClassName: {}", builderClassName);
        String builderFileName = builderClassName + ".java";
        logger.info("BuilderFileName: {}", builderFileName);
        return builderFileName;
    }

    private Collection<PsiImportStatement> getImports() {
        Collection<PsiImportStatement> result = new HashSet<>();

        JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(pros.project);

        PsiClass fieldClass = psiFacade.findClass("java.lang.reflect.Field", GlobalSearchScope.allScope(pros.project));
        result.add(elementFactory.createImportStatement(fieldClass));

        return result;
    }


}
