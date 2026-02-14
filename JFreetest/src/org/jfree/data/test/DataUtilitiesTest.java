package org.jfree.data.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.junit.jupiter.api.Test;

public class DataUtilitiesTest {

    // ==========================================
    // calculateColumnTotal(Values2D, int) [MOCK]
    // ==========================================

    @Test
    void testCalculateColumnTotal_NormalValues() {
        // EP: normal numeric values in a column
        Values2D values = mock(Values2D.class);
        when(values.getRowCount()).thenReturn(2);
        when(values.getValue(0, 0)).thenReturn(7.5);
        when(values.getValue(1, 0)).thenReturn(2.5);

        double result = DataUtilities.calculateColumnTotal(values, 0);
        assertEquals(10.0, result, 1e-9);
    }

    @Test
    void testCalculateColumnTotal_AllZeros() {
        // EP: column with all zeros
        Values2D values = mock(Values2D.class);
        when(values.getRowCount()).thenReturn(3);
        when(values.getValue(0, 1)).thenReturn(0.0);
        when(values.getValue(1, 1)).thenReturn(0.0);
        when(values.getValue(2, 1)).thenReturn(0.0);

        double result = DataUtilities.calculateColumnTotal(values, 1);
        assertEquals(0.0, result, 1e-9);
    }

    @Test
    void testCalculateColumnTotal_WithNegatives() {
        // EP: column includes negative values
        Values2D values = mock(Values2D.class);
        when(values.getRowCount()).thenReturn(3);
        when(values.getValue(0, 2)).thenReturn(-2.0);
        when(values.getValue(1, 2)).thenReturn(5.0);
        when(values.getValue(2, 2)).thenReturn(-1.0);

        double result = DataUtilities.calculateColumnTotal(values, 2);
        assertEquals(2.0, result, 1e-9);
    }

    // =======================================
    // calculateRowTotal(Values2D, int) [MOCK]
    // =======================================

    @Test
    void testCalculateRowTotal_NormalValues() {
        // EP: normal numeric values in a row
        Values2D values = mock(Values2D.class);
        when(values.getColumnCount()).thenReturn(2);
        when(values.getValue(0, 0)).thenReturn(1.0);
        when(values.getValue(0, 1)).thenReturn(2.0);

        double result = DataUtilities.calculateRowTotal(values, 0);
        assertEquals(3.0, result, 1e-9);
    }

    @Test
    void testCalculateRowTotal_MixedValues() {
        // EP: mixed positive and negative values
        Values2D values = mock(Values2D.class);
        when(values.getColumnCount()).thenReturn(3);
        when(values.getValue(1, 0)).thenReturn(-1.0);
        when(values.getValue(1, 1)).thenReturn(4.0);
        when(values.getValue(1, 2)).thenReturn(2.0);

        double result = DataUtilities.calculateRowTotal(values, 1);
        assertEquals(5.0, result, 1e-9);
    }

    @Test
    void testCalculateRowTotal_AllZeros() {
        // EP: row with all zeros
        Values2D values = mock(Values2D.class);
        when(values.getColumnCount()).thenReturn(2);
        when(values.getValue(2, 0)).thenReturn(0.0);
        when(values.getValue(2, 1)).thenReturn(0.0);

        double result = DataUtilities.calculateRowTotal(values, 2);
        assertEquals(0.0, result, 1e-9);
    }

    // =================================
    // createNumberArray(double[]) tests
    // =================================

    @Test
    void testCreateNumberArray_NormalArray() {
        // EP: normal non-empty array
        double[] input = new double[] { 1.0, 2.5, 3.0 };
        Number[] result = DataUtilities.createNumberArray(input);
        assertArrayEquals(new Number[] { 1.0, 2.5, 3.0 }, result);
    }

    @Test
    void testCreateNumberArray_EmptyArray() {
        // BVA: empty array
        double[] input = new double[] { };
        Number[] result = DataUtilities.createNumberArray(input);
        assertArrayEquals(new Number[] { }, result);
    }

    @Test
    void testCreateNumberArray_SingleElement() {
        // BVA: single-element array
        double[] input = new double[] { 7.25 };
        Number[] result = DataUtilities.createNumberArray(input);
        assertArrayEquals(new Number[] { 7.25 }, result);
    }

    @Test
    void testCreateNumberArray_WithNegatives() {
        // EP: array includes negative values
        double[] input = new double[] { -1.0, 0.0, 2.0 };
        Number[] result = DataUtilities.createNumberArray(input);
        assertArrayEquals(new Number[] { -1.0, 0.0, 2.0 }, result);
    }

    // ===================================
    // createNumberArray2D(double[][]) tests
    // ===================================

    @Test
    void testCreateNumberArray2D_Rectangular() {
        // EP: rectangular 2D array
        double[][] input = new double[][] {
            { 1.0, 2.0 },
            { 3.0, 4.0 }
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        Number[][] expected = new Number[][] {
            { 1.0, 2.0 },
            { 3.0, 4.0 }
        };

        assertArrayEquals(expected, result);
    }

    @Test
    void testCreateNumberArray2D_Empty2D() {
        // BVA: empty 2D array
        double[][] input = new double[][] { };
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertArrayEquals(new Number[][] { }, result);
    }

    @Test
    void testCreateNumberArray2D_Jagged2D() {
        // EP/Edge: jagged 2D array (rows of different lengths)
        double[][] input = new double[][] {
            { 1.0, 2.0, 3.0 },
            { 4.0 }
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        Number[][] expected = new Number[][] {
            { 1.0, 2.0, 3.0 },
            { 4.0 }
        };

        assertArrayEquals(expected, result);
    }

    @Test
    void testCreateNumberArray2D_WithNegatives() {
        // EP: includes negative values
        double[][] input = new double[][] {
            { -1.0, 2.0 },
            { 0.0, -3.0 }
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        Number[][] expected = new Number[][] {
            { -1.0, 2.0 },
            { 0.0, -3.0 }
        };

        assertArrayEquals(expected, result);
    }

    // ==========================================
    // getCumulativePercentages(KeyedValues) [MOCK]
    // ==========================================

    @Test
    void testGetCumulativePercentages_TwoItems() {
        // EP: normal positive values
        KeyedValues kv = mock(KeyedValues.class);
        when(kv.getItemCount()).thenReturn(2);
        when(kv.getKey(0)).thenReturn("A");
        when(kv.getKey(1)).thenReturn("B");
        when(kv.getValue(0)).thenReturn(1.0);
        when(kv.getValue(1)).thenReturn(3.0);

        KeyedValues result = DataUtilities.getCumulativePercentages(kv);

        assertEquals(0.25, result.getValue(0).doubleValue(), 1e-9);
        assertEquals(1.0, result.getValue(1).doubleValue(), 1e-9);
    }

    @Test
    void testGetCumulativePercentages_SingleItem() {
        // BVA: single item should always be 1.0
        KeyedValues kv = mock(KeyedValues.class);
        when(kv.getItemCount()).thenReturn(1);
        when(kv.getKey(0)).thenReturn("Only");
        when(kv.getValue(0)).thenReturn(5.0);

        KeyedValues result = DataUtilities.getCumulativePercentages(kv);

        assertEquals(1.0, result.getValue(0).doubleValue(), 1e-9);
    }

    @Test
    void testGetCumulativePercentages_DuplicateKeys() {
        // Edge: duplicate keys are allowed; cumulative percentages should still progress by index order
        KeyedValues kv = mock(KeyedValues.class);
        when(kv.getItemCount()).thenReturn(3);
        when(kv.getKey(0)).thenReturn("A");
        when(kv.getKey(1)).thenReturn("A");
        when(kv.getKey(2)).thenReturn("B");
        when(kv.getValue(0)).thenReturn(1.0);
        when(kv.getValue(1)).thenReturn(1.0);
        when(kv.getValue(2)).thenReturn(2.0);

        KeyedValues result = DataUtilities.getCumulativePercentages(kv);

        assertEquals(0.25, result.getValue(0).doubleValue(), 1e-9);
        assertEquals(0.50, result.getValue(1).doubleValue(), 1e-9);
        assertEquals(1.0, result.getValue(2).doubleValue(), 1e-9);
    }

    @Test
    void testGetCumulativePercentages_WithNegativeValues() {
        // Edge: includes negative values; still follows cumulative/total calculation
        KeyedValues kv = mock(KeyedValues.class);
        when(kv.getItemCount()).thenReturn(3);
        when(kv.getKey(0)).thenReturn("A");
        when(kv.getKey(1)).thenReturn("B");
        when(kv.getKey(2)).thenReturn("C");
        when(kv.getValue(0)).thenReturn(-1.0);
        when(kv.getValue(1)).thenReturn(2.0);
        when(kv.getValue(2)).thenReturn(3.0);

        KeyedValues result = DataUtilities.getCumulativePercentages(kv);

        assertEquals(-0.25, result.getValue(0).doubleValue(), 1e-9);
        assertEquals(0.25, result.getValue(1).doubleValue(), 1e-9);
        assertEquals(1.0, result.getValue(2).doubleValue(), 1e-9);
    }
}