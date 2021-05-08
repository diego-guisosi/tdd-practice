package br.com.dixy.tdd.practice.batch_builder;

import java.util.*;

class FairBatchComplementor {

    private final TypedElementsGroupSortedBySizeIterator typedElementsGroupSortedBySizeIterator;

    FairBatchComplementor(List<TypedElement> elements) {
        this.typedElementsGroupSortedBySizeIterator = new TypedElementsGroupSortedBySizeIterator(elements);
    }

    FairBatch complement(FairBatch batch) {
        while (typedElementsGroupSortedBySizeIterator.hasNext() && !batch.isFull()) {
            TypedElementsGroup group = typedElementsGroupSortedBySizeIterator.next();
            group.complement(batch);
        }
        return batch;
    }

}
