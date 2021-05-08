package br.com.dixy.tdd.practice.batch_builder;

import java.util.*;

public class FairBatchBuilder {

    private final int batchSize;

    public FairBatchBuilder(int batchSize) {
        this.batchSize = batchSize;
    }

    public List<TypedElement> build(List<TypedElement> allElements) {
        if (batchSize == 0 || allElements.isEmpty()) {
            return Collections.emptyList();
        }
        FairBatch batch = new FairBatch(allElements, batchSize);
        if (batch.isFull()) {
            return batch.asList();
        }
        return new FairBatchComplementor(allElements).complement(batch).asList();
    }

}
