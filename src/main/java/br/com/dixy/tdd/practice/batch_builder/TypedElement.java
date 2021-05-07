package br.com.dixy.tdd.practice.batch_builder;

import java.util.Objects;

public class TypedElement {

    private final Type type;
    private final String name;

    public TypedElement(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type type() {
        return type;
    }

    public String name() {
        return name;
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

    @Override
    public String toString() {
        return "TypedElement{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
