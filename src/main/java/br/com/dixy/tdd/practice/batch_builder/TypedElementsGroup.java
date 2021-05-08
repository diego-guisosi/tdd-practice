package br.com.dixy.tdd.practice.batch_builder;

import java.util.Iterator;
import java.util.List;

class TypedElementsGroup {

    private final List<TypedElement> elements;

    public TypedElementsGroup(List<TypedElement> elements) {
        this.elements = elements;
    }

    public void complement(FairBatch batch) {
        TypedElementsGroupReversedIterator iterator = new TypedElementsGroupReversedIterator(elements);
        while (iterator.hasNext() && !batch.isFull()) {
            TypedElement element = iterator.next();
            if (batch.contains(element)) {
                continue;
            }
            batch.add(element);
        }
    }

    private static class TypedElementsGroupReversedIterator implements Iterator<TypedElement> {

        private final List<TypedElement> elements;
        private int currentIndex;

        public TypedElementsGroupReversedIterator(List<TypedElement> elements) {
            this.elements = elements;
            this.currentIndex = elements.size();
        }

        @Override
        public boolean hasNext() {
            return currentIndex > 0;
        }

        @Override
        public TypedElement next() {
            return elements.get(--currentIndex);
        }

    }
}
