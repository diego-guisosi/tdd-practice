package br.com.dixy.tdd.practice.batch_builder;

import java.util.Iterator;
import java.util.List;

class TypedElementsGroup {

    private final TypedElementsGroupReversedIterator iterator;

    TypedElementsGroup(List<TypedElement> elements) {
        this.iterator = new TypedElementsGroupReversedIterator(elements);
    }

    public void complement(FairBatch batch) {
        while (iterator.hasNext() && !batch.isFull()) {
            addElementToBatchIfPossible(batch, iterator.next());
        }
    }

    private void addElementToBatchIfPossible(FairBatch batch, TypedElement element) {
        if (batch.contains(element)) {
            return;
        }
        batch.add(element);
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
