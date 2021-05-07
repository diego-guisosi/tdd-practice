package br.com.dixy.tdd.practice.batch_builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        for (int i = 0; i < batchSize; i++) {
            batch.add(list.get(i));
        }
        return batch;
    }
}
