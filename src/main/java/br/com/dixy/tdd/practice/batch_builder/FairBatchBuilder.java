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
        ArrayList<TypedElement> batch = new ArrayList<>();
        batch.add(list.get(0));
        return batch;
    }
}
