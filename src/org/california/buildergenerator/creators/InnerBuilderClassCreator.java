package org.california.buildergenerator.creators;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.patterns.PsiMethodCallPattern;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.JavaDummyElement;
import com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import com.intellij.psi.impl.source.tree.java.PsiExpressionListImpl;
import com.intellij.psi.impl.source.tree.java.PsiKeywordImpl;
import com.intellij.psi.impl.source.tree.java.PsiNewExpressionImpl;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import org.california.buildergenerator.fields.BuilderField;
import org.california.buildergenerator.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class InnerBuilderClassCreator extends AbstractCreator {

    private final Map<PsiField, PsiClass> interfaces;
    private final BuilderMethodCreator methodCreator;
    private PsiClass innerBuilder;

    public InnerBuilderClassCreator(Map<PsiField, PsiClass> interfaces, AbstractCreator creator) {
        super(creator);
        logger.info("Creating InnerBuilder with interfaces: {}", interfaces);
        this.interfaces = interfaces;
        this.methodCreator = new BuilderMethodCreator(interfaces, creator);
    }

    public PsiClass createInnerBuilder() {
        innerBuilder = elementFactory.createClass("InnerBuilder");
        innerBuilder.getModifierList().setModifierProperty(PsiModifier.PUBLIC, true);
        innerBuilder.getModifierList().setModifierProperty(PsiModifier.STATIC, true);
        implementInterfaces(innerBuilder);
        innerBuilder.add(createClassField());
        setMethods();
        return innerBuilder;
    }

    private void setMethods() {
        logger.info("Setting methods to interfaces");
        acceptForAllPairs(this::setMethodsToClass);
    }

    private void setMethodsToClass(PsiField field, PsiField nextField) {
        innerBuilder.add(methodCreator.createSetterMethod(field, nextField));
    }


    private void implementInterfaces(PsiClass innerBuilder) {
        interfaces.values().stream()
                .map(i -> Utils.getPsiClassType(pros, i))
                .map(elementFactory::createReferenceElementByType)
                .forEach(innerBuilder.getImplementsList()::add);
    }

    private PsiField createClassField() {
        PsiClassType type = Utils.getPsiClassType(pros, pros.psiClass);
        PsiJavaCodeReferenceElement classReference = elementFactory.createClassReferenceElement(pros.psiClass);

        PsiField field = elementFactory
                .createField("result", type);
        PsiNewExpression initializer = (PsiNewExpression) elementFactory
                .createExpressionFromText("new ClassName()", pros.psiClass);

        initializer.getClassReference().replace(classReference);

        PsiElement child = initializer.getFirstChild();
        while (child != null) {
            logger.info("CHILD: {}", child);
            child = child.getNextSibling();
        }

        field.setInitializer(initializer);

        return field;
    }


}
