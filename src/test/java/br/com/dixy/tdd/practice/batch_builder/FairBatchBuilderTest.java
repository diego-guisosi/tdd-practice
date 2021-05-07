package br.com.dixy.tdd.practice.batch_builder;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
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
    private static final TypedElement A2 = new TypedElement(Type.A, "A2");
    private static final TypedElement B1 = new TypedElement(Type.B, "B1");

    @Test
    public void buildsEmptyBatchWhenListIsEmpty() {
        int batchSize = 1;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> batch = builder.build(emptyList());
        assertEmpty(batch);
    }

    @Test
    public void buildsEmptyBatchWhenBatchSizeIsZero() {
        int batchSize = 0;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> batch = builder.build(singletonList(A1));
        assertEmpty(batch);
    }

    @Test
    public void buildsSingleElementBatchWhenListHasOneElementAndBatchSizeIsGreaterThanZero() {
        int batchSize = 2;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> batch = builder.build(singletonList(A1));
        assertSize(batch, 1);
        assertEquals("element", A1, batch.get(0));
    }

    @Test
    public void buildsSingleElementBatchWhenListHasMoreThanOneElementButBatchSizeIsOne() {
        int batchSize = 1;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> batch = builder.build(aList(A1, A2));
        assertSize(batch, 1);
        assertEquals("element", A1, batch.get(0));
    }

    @Test
    public void buildsBatchWithTheSameContentOfListWhenListSizeIsEqualToBatchSize() {
        int batchSize = 2;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> batch = builder.build(aList(A1, A2));
        assertSize(batch, 2);
        assertEquals("element", A1, batch.get(0));
        assertEquals("element", A2, batch.get(1));
    }

    @Test
    public void buildsBatchWithOneElementOfEachTypeWhenListContainsTwoTypesOfElementsAndBatchSizeIsTwo() {
        int batchSize = 2;
        FairBatchBuilder builder = new FairBatchBuilder(batchSize);
        List<TypedElement> batch = builder.build(aList(A1, A2, B1));
        assertSize(batch, 2);
        assertThat(batch, hasItems(A1, B1));
    }

    private void assertEmpty(List<TypedElement> batch) {
        assertTrue("batch", batch.isEmpty());
    }

    private void assertSize(List<TypedElement> batch, int expected) {
        assertEquals("batchSize", expected, batch.size());
    }

    private List<TypedElement> aList(TypedElement... element) {
        return asList(element);
    }

}
