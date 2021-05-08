package br.com.dixy.tdd.practice.batch_builder;

import java.util.*;
import java.util.stream.Collectors;

public class FairBatchBuilder {

    private final int batchSize;

    public FairBatchBuilder(int batchSize) {
        this.batchSize = batchSize;
    }

    public List<TypedElement> build(List<TypedElement> list) {
        if (batchSize == 0 || list.isEmpty()) {
            return Collections.emptyList();
        }
        return buildBatch(list, Math.min(batchSize, list.size()));
    }

    private ArrayList<TypedElement> buildBatch(List<TypedElement> list, int batchSize) {
        ArrayList<TypedElement> batch = new ArrayList<>();

        Map<Type, List<TypedElement>> elementsByType = list.stream().collect(Collectors.groupingBy(TypedElement::type));
        Set<Type> types = typesIn(elementsByType);
        int numberOfTypes = types.size();
        int batchSizeByType = batchSize / numberOfTypes;
        for (Type type : types) {
            List<TypedElement> elements = limitElementsByBatchSize(elementsByType.get(type), batchSizeByType);
            batch.addAll(elements);
        }

        if (!reachedBatchSize(batchSize, batch)) {
            complementBatchWithRemainingElements(batchSize, batch, elementsByType);
        }

        return batch;
    }

    private boolean reachedBatchSize(int batchSize, ArrayList<TypedElement> batch) {
        return batch.size() == batchSize;
    }

    private void complementBatchWithRemainingElements(int batchSize, ArrayList<TypedElement> batch, Map<Type, List<TypedElement>> elementsByType) {
        Map<Integer, List<TypedElement>> sortedBySize = groupAndSortBySize(elementsByType);
        Integer highest = sortedBySize.keySet().iterator().next();
        List<TypedElement> typedElements = sortedBySize.get(highest);
        for (int i = typedElements.size() - 1; !reachedBatchSize(batchSize, batch); i--) {
            batch.add(typedElements.get(i));
        }
    }

    private Map<Integer, List<TypedElement>> groupAndSortBySize(Map<Type, List<TypedElement>> elementsByType) {
        Map<Integer, List<TypedElement>> sortedBySize = new TreeMap<>(Comparator.reverseOrder());
        for (List<TypedElement> elements : elementsByType.values()) {
            sortedBySize.put(elements.size(), elements);
        }
        return sortedBySize;
    }

    private Set<Type> typesIn(Map<Type, List<TypedElement>> elementsByType) {
        return elementsByType.keySet();
    }

    private List<TypedElement> limitElementsByBatchSize(List<TypedElement> elements, int batchSizeByType) {
        return elements.size() <= batchSizeByType ? elements : elements.subList(0, batchSizeByType);
    }
}
