package br.com.dixy.tdd.practice.batch_builder;

import java.util.*;
import java.util.stream.Collectors;

class TypedElementsGroupSortedBySizeIterator implements Iterator<TypedElementsGroup> {

    private final Map<TypedElementsGroupSizeKey, TypedElementsGroup> groupsSortedByKey;
    private final Iterator<TypedElementsGroupSizeKey> keysSortedByTypedElementsGroupSize;

    TypedElementsGroupSortedBySizeIterator(List<TypedElement> elements) {
        this.groupsSortedByKey = new TreeMap<>(Comparator.reverseOrder());
        groupAndSort(elements);
        this.keysSortedByTypedElementsGroupSize = groupsSortedByKey.keySet().iterator();
    }

    private void groupAndSort(List<TypedElement> elements) {
        elements.stream()
                .collect(Collectors.groupingBy(TypedElement::type))
                .forEach(this::groupByTypeAndSize);
    }

    private void groupByTypeAndSize(Type type, List<TypedElement> typedElements) {
        groupsSortedByKey.put(TypedElementsGroupSizeKey.of(type, typedElements), new TypedElementsGroup(typedElements));
    }

    @Override
    public boolean hasNext() {
        return keysSortedByTypedElementsGroupSize.hasNext();
    }

    @Override
    public TypedElementsGroup next() {
        return groupsSortedByKey.get(keysSortedByTypedElementsGroupSize.next());
    }

}
