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
        return new FairBatch(list, batchSize).build();
    }

    private static class FairBatch {

        private final int batchSize;
        private final List<TypedElement> batch;
        private final Map<Type, List<TypedElement>> elementsByType;
        private final Set<Type> types;
        private final int allowedBatchSizeByType;

        public FairBatch(List<TypedElement> elements, int batchSize) {
            this.batch = new ArrayList<>();
            this.batchSize = Math.min(batchSize, elements.size());
            this.elementsByType = elements.stream().collect(Collectors.groupingBy(TypedElement::type));
            this.types = typesIn(elementsByType);
            this.allowedBatchSizeByType = batchSize / types.size();
        }

        public List<TypedElement> build() {
            selectAllowedElements();
            complementWithRemainingElementsIfPossible();
            return batch;
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

        private void complementWithRemainingElementsIfPossible() {
            if (!hasRoomToAddMoreElements()) {
                return;
            }
            Map<TypedElementsKey, List<TypedElement>> sortedBySize = groupAndSortBySize(elementsByType);
            Iterator<TypedElementsKey> highestKeys = getHighestKeys(sortedBySize);
            while (highestKeys.hasNext() && hasRoomToAddMoreElements()) {
                List<TypedElement> elements = sortedBySize.get(highestKeys.next());
                for (int i = lastIndexOf(elements); hasRoomToAddMoreElements() && i >= 0; i--) {
                    TypedElement element = elements.get(i);
                    if (batch.contains(element)) {
                        continue;
                    }
                    batch.add(element);
                }
            }
        }

        private Iterator<TypedElementsKey> getHighestKeys(Map<TypedElementsKey, List<TypedElement>> sortedBySize) {
            return sortedBySize.keySet().iterator();
        }

        private Map<TypedElementsKey, List<TypedElement>> groupAndSortBySize(Map<Type, List<TypedElement>> elementsByType) {
            Map<TypedElementsKey, List<TypedElement>> sortedBySize = new TreeMap<>(Comparator.reverseOrder());
            elementsByType.forEach((type, elements) -> {
                sortedBySize.put(TypedElementsKey.of(type, elements), elements);
            });
            return sortedBySize;
        }

        private boolean hasRoomToAddMoreElements() {
            return batch.size() < batchSize;
        }

        private int lastIndexOf(List<TypedElement> elements) {
            return elements.size() - 1;
        }

        private static class TypedElementsKey implements Comparable<TypedElementsKey> {

            private final Type type;
            private final Integer size;

            private TypedElementsKey(Type type, Integer size) {
                this.type = type;
                this.size = size;
            }

            public static TypedElementsKey of(Type type, List<TypedElement> elements) {
                return new TypedElementsKey(type, elements.size());
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TypedElementsKey that = (TypedElementsKey) o;
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
            public int compareTo(TypedElementsKey otherKey) {
                int comparison = this.size.compareTo(otherKey.size);
                if (comparison != 0) {
                    return comparison;
                }
                return this.type.compareTo(otherKey.type);
            }

        }

    }
}
