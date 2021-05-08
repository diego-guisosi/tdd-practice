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
        Map<TypedElementsSizeKey, List<TypedElement>> sortedBySize = groupAndSortBySize(elementsByType);
        Iterator<TypedElementsSizeKey> highest = sortedBySize.keySet().iterator();
        while (highest.hasNext() && !reachedBatchSize(batchSize, batch)) {
            List<TypedElement> elements = sortedBySize.get(highest.next());
            for (int i = lastIndexOf(elements); !reachedBatchSize(batchSize, batch) && i >= 0; i--) {
                TypedElement element = elements.get(i);
                if (batch.contains(element)) {
                    continue;
                }
                batch.add(element);
            }
        }
    }

    private int lastIndexOf(List<TypedElement> elements) {
        return elements.size() - 1;
    }

    private Map<TypedElementsSizeKey, List<TypedElement>> groupAndSortBySize(Map<Type, List<TypedElement>> elementsByType) {
        Map<TypedElementsSizeKey, List<TypedElement>> sortedBySize = new TreeMap<>(Comparator.reverseOrder());
        elementsByType.forEach((type, elements) -> {
            sortedBySize.put(TypedElementsSizeKey.of(type, elements), elements);
        });
        return sortedBySize;
    }

    private Set<Type> typesIn(Map<Type, List<TypedElement>> elementsByType) {
        return elementsByType.keySet();
    }

    private List<TypedElement> limitElementsByBatchSize(List<TypedElement> elements, int batchSizeByType) {
        return elements.size() <= batchSizeByType ? elements : elements.subList(0, batchSizeByType);
    }

    private static class TypedElementsSizeKey implements Comparable<TypedElementsSizeKey> {

        private final Type type;
        private final Integer size;

        private TypedElementsSizeKey(Type type, Integer size) {
            this.type = type;
            this.size = size;
        }

        public static TypedElementsSizeKey of(Type type, List<TypedElement> elements) {
            return new TypedElementsSizeKey(type, elements.size());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TypedElementsSizeKey that = (TypedElementsSizeKey) o;
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
        public int compareTo(TypedElementsSizeKey otherKey) {
            int comparison = this.size.compareTo(otherKey.size);
            if (comparison != 0) {
                return comparison;
            }
            return this.type.compareTo(otherKey.type);
        }

    }
}
