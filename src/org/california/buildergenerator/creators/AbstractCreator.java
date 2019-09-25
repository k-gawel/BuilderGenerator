package org.california.buildergenerator.creators;

import com.intellij.psi.*;
import org.california.buildergenerator.fields.BuilderFieldsList;
import org.california.buildergenerator.utils.PluginBasicProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class AbstractCreator {

    protected Logger logger = LogManager.getLogger(getClass());
    protected final PluginBasicProperties pros;
    protected final PsiJavaFile javaFile;
    protected final PsiClass psiClass;
    protected final PsiElementFactory elementFactory;
    protected final BuilderFieldsList fields;

    public AbstractCreator(PluginBasicProperties properties) {
        logger.info("Constructor with properties {}", properties);
        this.pros = properties;
        this.elementFactory = JavaPsiFacade.getElementFactory(properties.project);
        this.javaFile = (PsiJavaFile) properties.psiFile;
        this.psiClass = properties.psiClass;
        this.fields = new BuilderFieldsList();
    }

    public AbstractCreator(AbstractCreator creator) {
        logger.info("Constructor with creator instance: {}", creator);
        this.pros = creator.pros;
        this.psiClass = creator.psiClass;
        this.elementFactory = creator.elementFactory;
        this.javaFile = creator.javaFile;
        this.fields = creator.fields;
    }


    protected void acceptForAllPairs(BiConsumer<PsiField, PsiField> biConsumer) {
        int index = 0;
        PsiField field;
        PsiField nextField;

        do {
            field = index < fields.size() ? fields.getPsiField(index) : null;
            nextField = field != null ? fields.next(field) : null;
            System.out.println("PAIR: " + field + " - " + nextField);
            biConsumer.accept(field, nextField);
            index++;
        } while (field != null || nextField != null);
    }

}
