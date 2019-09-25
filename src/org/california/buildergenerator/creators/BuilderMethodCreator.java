package org.california.buildergenerator.creators;

import com.intellij.psi.*;
import com.intellij.psi.formatter.java.CodeBlockBlock;
import org.california.buildergenerator.utils.StringUtils;
import org.california.buildergenerator.utils.Utils;

import java.util.Arrays;
import java.util.Map;

public class BuilderMethodCreator extends AbstractCreator {

    private final Map<PsiField, PsiClass> interfaces;

    public BuilderMethodCreator(Map<PsiField, PsiClass> interfaces, AbstractCreator creator) {
        super(creator);
        this.interfaces = interfaces;
    }


    public PsiMethod createSetterMethod(PsiField field, PsiField nextField) {
        logger.info("Creating setter method for field: {} with next field: {}", field, nextField);

        PsiClassType returnType = getReturnType(field, nextField);
        logger.info("Return type: {}", returnType);

        PsiParameter[] parameters = getParameters(field);
        logger.info("Parameters: {}", parameters);

        String setterName = getSetterName(field);
        logger.info("Setter name: {}", setterName);

        PsiMethod method = elementFactory.createMethod(setterName, returnType);
        Arrays.stream(parameters).forEach(method.getParameterList()::add);
        logger.info("Setter method: {}", method);

        method.getBody().replace(createMethodBody(method, field));

        return method;
    }

    private PsiCodeBlock createMethodBody(PsiMethod method, PsiField field) {
        return field != null ?
                createSetterMethodBody(method, field) : createBuildMethodBody(method);
    }

    private PsiCodeBlock createSetterMethodBody(PsiMethod method, PsiField field) {
        logger.info("Creating setter method body for method: {} with field: {}", method, field);
        PsiCodeBlock body = elementFactory.createCodeBlock();

        PsiExpressionStatement assignment = createFieldAssignment(field, method.getParameterList().getParameters()[0]);
        PsiReturnStatement returnStatement = (PsiReturnStatement) elementFactory.createStatementFromText("return this;", method);

        body.add(assignment);
        body.add(returnStatement);

        return body;
    }

    private PsiCodeBlock createBuildMethodBody(PsiMethod method) {
        logger.info("Creating build method body for method: {}", method);
        PsiCodeBlock body = elementFactory.createCodeBlock();

        PsiReturnStatement returnStatement = (PsiReturnStatement) elementFactory.createStatementFromText("return result;", body);
        body.add(returnStatement);


        return body;
    }

    private PsiExpressionStatement createFieldAssignment(PsiField field, PsiParameter parameter) {
        String parameterName = parameter.getName();
        String fieldName = field.getName();
        String assignmentText = "result" + '.' + fieldName + " = " + parameterName + ';';


        PsiExpressionStatement assignment = (PsiExpressionStatement) elementFactory
                .createStatementFromText(assignmentText, field.getContext());
        return assignment;
    }

    public PsiMethod createSetterSignature(PsiField field, PsiField nextField) {
        logger.info("Creating setter signature for field: {} with next: {}", field, nextField);
        PsiMethod method = createSetterMethod(field, nextField);
        return extractAbstractSingature(method);
    }

    public PsiMethod createNoSetterMethod(PsiField field, PsiField nextField) {
        logger.info("Creating noSetter method for {}, with next field: {}", field, nextField);

        PsiClassType returnType = getReturnType(field, nextField);
        logger.info("Return type: {}", returnType);

        String noSetterName = getNoSetterName(field);
        logger.info("NoSetter name: {}", noSetterName);

        PsiMethod method = elementFactory.createMethod(noSetterName, returnType);
        method.getModifierList().setModifierProperty(PsiModifier.DEFAULT, true);
        logger.info("Method created: {}", method);

        PsiCodeBlock body = createNoSetterBody(returnType);
        method.getBody().replace(body);
        logger.info("NoSetter body injected: {}", body);


        return method;
    }

    private PsiCodeBlock createNoSetterBody(PsiClassType returnType) {
        PsiCodeBlock body = elementFactory.createCodeBlock();
        PsiStatement statement = elementFactory.createStatementFromText("return (" + returnType.getName() + ") this;", body);
        body.add(statement);
        return body;
    }

    private PsiClassType getReturnType(PsiField field, PsiField nextField) {
        PsiClass psiClass = field == null && nextField == null
                ? pros.psiClass : interfaces.get(nextField);

        logger.info("GETTING RETURN TYPE FOR: {} - {}. RESULT: {}", field, nextField, psiClass);
        return Utils.getPsiClassType(pros, psiClass);
    }

    private PsiParameter[] getParameters(PsiField field) {
        return field != null ?
                new PsiParameter[]{elementFactory.createParameter(field.getName(), field.getType())} :
                new PsiParameter[]{};
    }

    private PsiMethod extractAbstractSingature(PsiMethod method) {
        logger.info("Extracting abstract signature of: {}", method);
        PsiMethod copierdMethod = (PsiMethod) method.copy();
        copierdMethod.getModifierList().setModifierProperty(PsiModifier.ABSTRACT, true);
        logger.info("Abstract modifer setted true");
        copierdMethod.getBody().delete();
        logger.info("Body deleted");
        return copierdMethod;
    }


    private String getSetterName(PsiField field) {
        String setterName;
        logger.info("Getting setter name for: {}", field);
        if (field == null)
            setterName = "build";
        else
            setterName = "with" + StringUtils.capitalizeFirstLetter(field.getName());
        logger.info("Setter name: {}", setterName);
        return setterName;
    }

    private String getNoSetterName(PsiField field) {
        logger.info("Getting nosetter name for {}", field);
        String noSetterName = "withNo" + StringUtils.capitalizeFirstLetter(field.getName());
        logger.info("NoSetter name: {}", noSetterName);
        return noSetterName;
    }

}
