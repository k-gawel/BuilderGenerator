package org.california.buildergenerator.fields;

import com.intellij.psi.PsiField;

import java.util.Objects;

public class BuilderField {

    public PsiField field;
    public Status status;

    public BuilderField(PsiField field) {
        this.field = field;
        this.status = Status.REQUIRED;
    }

    public BuilderField(PsiField field, Status status) {
        this.field = field;
        this.status = status;

    }

    public void setOptional(boolean value) {
        this.status = value ? Status.OPTIONAL : Status.REQUIRED;
    }

    public boolean isOptional() {
        return this.status.equals(Status.OPTIONAL);
    }

    public enum Status {
        REQUIRED, OPTIONAL, ABSENT
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuilderField field1 = (BuilderField) o;
        return Objects.equals(field, field1.field) &&
                status == field1.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, status);
    }

    @Override
    public String toString() {
        return "BuilderField{" +
                "field=" + field +
                ", status=" + status +
                '}';
    }
}
