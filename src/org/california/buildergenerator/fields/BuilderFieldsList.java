package org.california.buildergenerator.fields;

import com.intellij.psi.PsiField;
import org.apache.maven.model.Build;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BuilderFieldsList extends ArrayList<BuilderField> {

    public BuilderFieldsList() {
        super();
    }

    public Optional<BuilderField> getBuilderField(PsiField psiField) {
        return this.stream().filter(f -> f.field.equals(psiField)).findFirst();
    }


    public int indexOf(BuilderField builderField) {
        return super.indexOf(builderField);
    }

    public PsiField getPsiField(int index) {
        return get(index).field;
    }

    public int indexOf(PsiField psiField) {
        return super.indexOf(getBuilderField(psiField));
    }

    public BuilderFieldsList(Collection<PsiField> fields) {
        super();
        fields.stream()
                .map(BuilderField::new)
                .forEach(this::add);
    }


    public BuilderField next(BuilderField field) {
        int index = indexOf(field);
        if (index < 0)
            throw new IllegalArgumentException("Field must be part of list: " + field);

        try {
            return get(index + 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public PsiField next(PsiField field) {
        BuilderField builderField = getBuilderField(field).orElse(null);
        BuilderField nextBuilderField = next(builderField);
        return nextBuilderField != null ? nextBuilderField.field : null;
    }

    public void switchFields(BuilderField field1, BuilderField field2) {
        int index1 = indexOf(field1);
        int index2 = indexOf(field2);

        if (index1 < 0 || index2 < 0)
            throw new IllegalArgumentException("List doesn't contains one of fields");

        set(index1, field2);
        set(index2, field1);
    }


    public void move(BuilderField field, int moveDirection) {
        int index = indexOf(field) + moveDirection;
        if (index < 0)
            index = 0;
        if (index >= size())
            index = size() - 1;

        BuilderField field2 = get(index);

        switchFields(field, field2);
    }

}
