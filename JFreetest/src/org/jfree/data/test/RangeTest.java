package org.jfree.data.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jfree.data.Range;
import org.junit.jupiter.api.Test;

public class RangeTest {

    // =========================
    // getLowerBound() partitions
    // =========================

    @Test
    void testGetLowerBound_PositiveRange() {
        // EP: Valid positive range
        Range r = new Range(2.0, 5.0);
        assertEquals(2.0, r.getLowerBound(), 1e-9);
    }

    @Test
    void testGetLowerBound_NegativeRange() {
        // EP: Valid negative range
        Range r = new Range(-10.0, -3.0);
        assertEquals(-10.0, r.getLowerBound(), 1e-9);
    }

    @Test
    void testGetLowerBound_DegenerateRange() {
        // BVA: Degenerate range (lower == upper)
        Range r = new Range(4.5, 4.5);
        assertEquals(4.5, r.getLowerBound(), 1e-9);
    }

    // =========================
    // getUpperBound() partitions
    // =========================

    @Test
    void testGetUpperBound_PositiveRange() {
        // EP: Valid positive range
        Range r = new Range(2.0, 5.0);
        assertEquals(5.0, r.getUpperBound(), 1e-9);
    }

    @Test
    void testGetUpperBound_MixedRange() {
        // EP: Mixed range (negative lower, positive upper)
        Range r = new Range(-2.0, 7.0);
        assertEquals(7.0, r.getUpperBound(), 1e-9);
    }

    @Test
    void testGetUpperBound_DegenerateRange() {
        // BVA: Degenerate range
        Range r = new Range(-1.25, -1.25);
        assertEquals(-1.25, r.getUpperBound(), 1e-9);
    }

    // =====================
    // getLength() partitions
    // =====================

    @Test
    void testGetLength_StandardPositiveRange() {
        // EP: Standard valid range
        Range r = new Range(2.0, 5.0);
        assertEquals(3.0, r.getLength(), 1e-9);
    }

    @Test
    void testGetLength_MixedRange() {
        // EP: Mixed range
        Range r = new Range(-2.0, 7.0);
        assertEquals(9.0, r.getLength(), 1e-9);
    }

    @Test
    void testGetLength_DegenerateRange() {
        // BVA: Degenerate range (length 0)
        Range r = new Range(4.5, 4.5);
        assertEquals(0.0, r.getLength(), 1e-9);
    }

    // =========================
    // contains(double) partitions
    // =========================

    @Test
    void testContains_ValueInsideRange() {
        // EP: Value strictly inside range
        Range r = new Range(1.0, 5.0);
        assertTrue(r.contains(3.0));
    }

    @Test
    void testContains_ValueEqualsLowerBound() {
        // BVA: value == lower bound
        Range r = new Range(1.0, 5.0);
        assertTrue(r.contains(1.0));
    }

    @Test
    void testContains_ValueEqualsUpperBound() {
        // BVA: value == upper bound
        Range r = new Range(1.0, 5.0);
        assertTrue(r.contains(5.0));
    }

    @Test
    void testContains_ValueBelowLowerBound() {
        // EP: Invalid value below range
        Range r = new Range(1.0, 5.0);
        assertFalse(r.contains(0.999999));
    }

    @Test
    void testContains_ValueAboveUpperBound() {
        // EP: Invalid value above range
        Range r = new Range(1.0, 5.0);
        assertFalse(r.contains(5.000001));
    }

    @Test
    void testContains_DegenerateRange_Contains() {
        // BVA: Degenerate range contains only the single value
        Range r = new Range(2.0, 2.0);
        assertTrue(r.contains(2.0));
    }

    @Test
    void testContains_DegenerateRange_NotContains() {
        // BVA: Degenerate range does not contain nearby values
        Range r = new Range(2.0, 2.0);
        assertFalse(r.contains(2.000001));
    }

    // =========================
    // getCentralValue() partitions
    // =========================

    @Test
    void testGetCentralValue_StandardRange() {
        // EP: Standard positive range
        Range r = new Range(2.0, 6.0);
        assertEquals(4.0, r.getCentralValue(), 1e-9);
    }

    @Test
    void testGetCentralValue_NegativeRange() {
        // EP: Negative range
        Range r = new Range(-10.0, -2.0);
        assertEquals(-6.0, r.getCentralValue(), 1e-9);
    }

    @Test
    void testGetCentralValue_MixedRange() {
        // EP: Mixed range
        Range r = new Range(-4.0, 6.0);
        assertEquals(1.0, r.getCentralValue(), 1e-9);
    }

    @Test
    void testGetCentralValue_DegenerateRange() {
        // BVA: Degenerate range
        Range r = new Range(3.3, 3.3);
        assertEquals(3.3, r.getCentralValue(), 1e-9);
    }

    @Test
    void testGetCentralValue_SmallDecimals() {
        // Precision partition: very small decimals
        Range r = new Range(0.0000001, 0.0000003);
        assertEquals(0.0000002, r.getCentralValue(), 1e-12);
    }

    @Test
    void testGetCentralValue_LargeNumbers() {
        // Stress partition: large values
        Range r = new Range(1_000_000_000.0, 1_000_000_010.0);
        assertEquals(1_000_000_005.0, r.getCentralValue(), 1e-6);
    }
}