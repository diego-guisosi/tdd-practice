package br.com.dixy.tdd.practice.batch_builder;

import java.util.Objects;

public class TypedElement {

    public final Type type;
    public final String name;

    public TypedElement(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypedElement that = (TypedElement) o;
        return type == that.type && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

}
