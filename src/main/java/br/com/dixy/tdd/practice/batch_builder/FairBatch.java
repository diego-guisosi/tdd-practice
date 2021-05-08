package br.com.dixy.tdd.practice.batch_builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class FairBatch {

    private final int batchSize;
    private final List<TypedElement> batch;
    private final Map<Type, List<TypedElement>> elementsByType;
    private final Set<Type> types;
    private final int allowedBatchSizeByType;

    FairBatch(List<TypedElement> elements, int batchSize) {
        this.batch = new ArrayList<>();
        this.batchSize = Math.min(batchSize, elements.size());
        this.elementsByType = elements.stream().collect(Collectors.groupingBy(TypedElement::type));
        this.types = typesIn(elementsByType);
        this.allowedBatchSizeByType = batchSize / types.size();
        selectAllowedElements();
    }

    private Set<Type> typesIn(Map<Type, List<TypedElement>> elementsByType) {
        return elementsByType.keySet();
    }

    private void selectAllowedElements() {
        for (Type type : types) {
            batch.addAll(selectAllowedElementsOf(type));
        }
    }

    private List<TypedElement> selectAllowedElementsOf(Type type) {
        List<TypedElement> elements = elementsByType.get(type);
        return elements.size() <= allowedBatchSizeByType ? elements : elements.subList(0, allowedBatchSizeByType);
    }

    List<TypedElement> asList() {
        return batch;
    }

    void add(TypedElement element) {
        batch.add(element);
    }

    boolean contains(TypedElement element) {
        return batch.contains(element);
    }

    boolean isFull() {
        return batch.size() == batchSize;
    }

}
