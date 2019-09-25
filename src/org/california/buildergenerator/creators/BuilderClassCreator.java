package org.california.buildergenerator.creators;

import com.intellij.psi.*;
import org.california.buildergenerator.utils.Utils;

import java.util.Map;

public class BuilderClassCreator extends AbstractCreator {

    private PsiJavaFile builderFile = null;
    private Map<PsiField, PsiClass> interfaces;

    public BuilderClassCreator(PsiJavaFile builderFile, AbstractCreator creator) {
        super(creator);
        logger.info("Creator with builderFile: {} and creator: {}", builderFile, creator);
        this.builderFile = builderFile;
    }

    public BuilderClassCreator(AbstractCreator creator) {
        super(creator);
        logger.info("Creator with creator: {}", creator);
    }


    public PsiClass createClass() {
        String className = getBuilderClassName();
        PsiClass psiClass = elementFactory.createClass(className);

        setModifiers(psiClass);

        createInterfaces().values().forEach(psiClass::add);

        psiClass.add(getInnerBuilder());
        psiClass.add(getInitialMethods());

        return psiClass;
    }

    private void setModifiers(PsiClass psiClass) {
        psiClass.getModifierList().setModifierProperty(PsiModifier.PUBLIC, true);
        psiClass.getModifierList().setModifierProperty(PsiModifier.STATIC, pros.inner);
    }

    private PsiMethod getInitialMethods() {
        PsiClass builderClass = interfaces.get(fields.getPsiField(0));
        PsiClassType returnType = Utils.getPsiClassType(pros, builderClass);
        PsiMethod method = elementFactory.createMethod("create", returnType);
        PsiStatement returnStatement = elementFactory.createStatementFromText("return new InnerBuilder();", method);
        method.getBody().add(returnStatement);
        return method;
    }

    private Map<PsiField, PsiClass> createInterfaces() {
        BuilderInterfaceCreator interfaceCreator = new BuilderInterfaceCreator(this);
        this.interfaces = interfaceCreator.createInterfaces();
        return this.interfaces;
    }

    private PsiClass getInnerBuilder() {
        InnerBuilderClassCreator innerBuilderCreator = new InnerBuilderClassCreator(interfaces, this);
        return innerBuilderCreator.createInnerBuilder();
    }

    private String getBuilderClassName() {
        if (builderFile == null)
            return "Builder";

        String builderFileName = builderFile.getName();
        logger.info("Getting builder class name from file: {}", builderFileName);
        String result = builderFileName.split("\\.")[0];
        logger.info("BuilderFileName: {}", result);
        return result;
    }

}
