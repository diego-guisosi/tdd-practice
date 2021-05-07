package br.com.dixy.tdd.practice.batch_builder;

import java.util.Collections;
import java.util.List;

public class FairBatchBuilder {

    private final int batchSize;

    public FairBatchBuilder(int batchSize) {
        this.batchSize = batchSize;
    }

    public List<TypedElement> build(List<TypedElement> list) {
        if (batchSize == 0) {
            return Collections.emptyList();
        }
        return list;
    }
}
