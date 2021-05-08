package br.com.dixy.tdd.practice.batch_builder;

import java.util.List;
import java.util.Objects;

public class TypedElementsGroupSizeKey implements Comparable<TypedElementsGroupSizeKey> {

    private final Type type;
    private final Integer size;

    private TypedElementsGroupSizeKey(Type type, Integer size) {
        this.type = type;
        this.size = size;
    }

    public static TypedElementsGroupSizeKey of(Type type, List<TypedElement> elements) {
        return new TypedElementsGroupSizeKey(type, elements.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypedElementsGroupSizeKey that = (TypedElementsGroupSizeKey) o;
        return Objects.equals(size, that.size) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, size);
    }

    @Override
    public String toString() {
        return "HighestTypedElementsKey{" +
                "type=" + type +
                ", size=" + size +
                '}';
    }

    @Override
    public int compareTo(TypedElementsGroupSizeKey otherKey) {
        int comparison = this.size.compareTo(otherKey.size);
        if (comparison != 0) {
            return comparison;
        }
        return this.type.compareTo(otherKey.type);
    }

}
