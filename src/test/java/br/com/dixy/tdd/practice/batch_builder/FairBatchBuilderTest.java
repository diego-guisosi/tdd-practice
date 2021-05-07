package br.com.dixy.tdd.practice.batch_builder;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Given a list of elements of different types, build a batch of N elements containing a fair amount of
 * elements by type
 * <p>
 * e.g:
 * Providing a list -> [
 * TypeA:ElementA1, TypeA:ElementA2, TypeA:ElementA3, TypeA:ElementA4,
 * TypeB:ElementB1, TypeB:ElementB2, TypeB:ElementB3, TypeB:ElementB4,
 * TypeC:ElementC1, TypeC:ElementC2, TypeC:ElementC3, TypeC:ElementC4
 * ]
 * <p>
 * Providing a batch size of 6
 * <p>
 * Returns a list -> [
 * TypeA:ElementA1, TypeA:ElementA2,
 * TypeB:ElementB1, TypeB:ElementB2,
 * TypeC:ElementC1, TypeC:ElementC2
 * ]
 */
public class FairBatchBuilderTest {

    private static final TypedElement A1 = new TypedElement(Type.A, "A1");

    @Test
    public void buildsEmptyBatchWhenListIsEmpty() {
        int batchSize = 1;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> batch = builder.build(Collections.emptyList());
        assertEmpty(batch);
    }

    @Test
    public void buildsEmptyBatchWhenBatchSizeIsZero() {
        int batchSize = 0;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> list = Collections.singletonList(A1);
        List<TypedElement> batch = builder.build(list);
        assertEmpty(batch);
    }

    @Test
    public void buildsSingleElementBatchWhenListHasOneElementAndBatchSizeIsGreaterThanZero() {
        int batchSize = 2;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> list = Collections.singletonList(A1);
        List<TypedElement> batch = builder.build(list);
        assertSize(batch, 1);
        assertEquals("element", A1, batch.get(0));
    }

    private void assertEmpty(List<TypedElement> batch) {
        assertTrue("batch", batch.isEmpty());
    }

    private void assertSize(List<TypedElement> batch, int expected) {
        assertEquals("batchSize", expected, batch.size());
    }

}
