package org.california.buildergenerator.creators;

import com.intellij.psi.*;
import org.california.buildergenerator.fields.BuilderField;
import org.california.buildergenerator.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class BuilderInterfaceCreator extends AbstractCreator {

    private Map<PsiField, PsiClass> interfaces;

    public BuilderInterfaceCreator(AbstractCreator creator) {
        super(creator);
    }


    public Map<PsiField, PsiClass> createInterfaces() {
        logger.info("Creating interfaces for all fields and final interface");
        Map<PsiField, PsiClass> result = new HashMap<>();
        fields.stream().map(f -> f.field).forEach(f -> result.put(f, createInterface(f)));

        logger.info("Created fields interfaces");
        result.put(null, createInterface(null));
        logger.info("Created final interface");
        this.interfaces = result;
        setMethods();
        return result;
    }

    private void setMethods() {
        logger.info("Setting methods to interfaces");
        acceptForAllPairs(this::setMethodsToInterface);
    }

    private void setMethodsToInterface(PsiField field, PsiField nextField) {
        PsiClass psiInterface = interfaces.get(field);

        BuilderMethodCreator methodCreator = new BuilderMethodCreator(interfaces, this);
        BuilderField builderField = fields.getBuilderField(field).orElse(new BuilderField(null));

        logger.info("Builder field for {} is: {}", psiInterface, builderField);

        logger.info("Setting required setter");
        psiInterface.add(methodCreator.createSetterSignature(field, nextField));
        if (builderField.isOptional()) {
            logger.info("Setting optional setter");
            psiInterface.add(methodCreator.createNoSetterMethod(field, nextField));
        }
    }


    private PsiClass createInterface(PsiField field) {
        logger.info("Creating interface for field: {}", field);
        String interfaceName = getInterfaceName(field);
        PsiClass psiInterface = elementFactory.createInterface(interfaceName);
        logger.info("PsiInterface: {}", psiInterface);
        psiInterface.getModifierList().setModifierProperty(PsiModifier.PUBLIC, true);
        logger.info("Public modifer setted, modifers: {}", psiInterface.getModifierList());
        return psiInterface;
    }


    private String getInterfaceName(PsiField field) {
        logger.info("Getting interface name for field: {}", field);
        String interfaceName;
        if (field != null) {
            String fieldName = field.getName();
            logger.info("Field name: {}", fieldName);
            interfaceName = StringUtils.capitalizeFirstLetter(fieldName) + "Setter";
        } else
            interfaceName = "FinalBuilder";
        logger.info("Interface name: {}", interfaceName);
        return interfaceName;
    }


}
