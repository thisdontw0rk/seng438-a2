# **SENG 438 - Software Testing, Reliability, and Quality**

# **Lab. Report \#2 – Requirements-Based Test Generation**

**Group:** Group 19  
**Student 1:** Haseeb Tahir  
**Student 2:** Mustafa Ayobi  
**Student 3:** Shaheer Shakir  
**Student 4:** Moyo Ogunjobi  

---

# 1 Introduction

This lab focused on developing a **requirements-based unit test suite** using **JUnit 5 (Jupiter)** for the modified JFreeChart framework under `org.jfree.data`. We tested two classes:

- `org.jfree.data.Range`
- `org.jfree.data.DataUtilities`

All tests were designed using the **Javadoc specifications as the requirements source (test oracle)**. We used a **black-box** approach, meaning the expected results were derived from the specification rather than from the implementation.

To design systematic tests with strong coverage and minimal redundancy, we applied:

- **Equivalence Class Partitioning (ECP):** choosing representative inputs from groups expected to behave similarly.
- **Boundary Value Analysis (BVA):** focusing on defect-prone edges (e.g., inclusive bounds, degenerate ranges, empty inputs).
- **Mockito mocking:** for `DataUtilities` methods that accept interface parameters (`Values2D`, `KeyedValues`), allowing controlled and isolated unit testing.

---

# 2 Detailed description of unit test strategy

We followed a requirements-based black-box testing workflow:

1. **Read Javadoc requirements** for each selected method in `Range` and each required method in `DataUtilities`.
2. **Identify input domains** for each parameter (numeric bounds, values, array sizes, table dimensions).
3. **Partition the domain** into equivalence classes:
   - Valid behavior partitions (normal cases)
   - Boundary partitions (edges like inclusive bounds and degenerate ranges)
   - Edge/robustness partitions (precision/stress and unusual but valid inputs)
4. **Apply BVA** where boundaries exist (range bounds, empty/single arrays).
5. **Implement one test per partition** with clear naming and a single focused assertion goal.
6. For interface-based inputs in `DataUtilities`, **mock the interface** using Mockito so we can control:
   - sizes (`getRowCount()`, `getColumnCount()`, `getItemCount()`)
   - returned values (`getValue(...)`, `getKey(...)`)

## 2.1 Input partitions designed

## A) `Range` (5 methods selected out of 15)

### 1) `getLowerBound()`
- **ECP:** positive range  
- **ECP:** negative range  
- **BVA:** degenerate range (`lower == upper`)

### 2) `getUpperBound()`
- **ECP:** positive range  
- **ECP:** mixed range (negative lower, positive upper)  
- **BVA:** degenerate range (`lower == upper`)

### 3) `getLength()`
- **ECP:** standard positive range  
- **ECP:** mixed range  
- **BVA:** degenerate range (length = 0)

### 4) `contains(double value)`
- **ECP:** value strictly inside range  
- **BVA:** value at lower bound (inclusive)  
- **BVA:** value at upper bound (inclusive)  
- **ECP:** value below lower bound  
- **ECP:** value above upper bound  
- **BVA/Edge:** degenerate range contains only one value

### 5) `getCentralValue()`
- **ECP:** standard range  
- **ECP:** negative-only range  
- **ECP:** mixed range  
- **BVA:** degenerate range  
- **Precision/Stress:** small decimals; large values

---

## B) `DataUtilities` (all 5 methods tested)

### 1) `calculateColumnTotal(Values2D, int)` *(mocked)*
- **ECP:** normal numeric values  
- **ECP:** all zeros  
- **ECP:** includes negatives  

### 2) `calculateRowTotal(Values2D, int)` *(mocked)*
- **ECP:** normal numeric values  
- **ECP:** mixed positive/negative  
- **ECP:** all zeros  

### 3) `createNumberArray(double[])`
- **ECP:** normal array  
- **BVA:** empty array  
- **BVA:** single-element array  
- **ECP:** includes negatives  

### 4) `createNumberArray2D(double[][])`
- **ECP:** rectangular 2D array  
- **BVA:** empty 2D array  
- **Edge:** jagged 2D array (different row lengths)  
- **ECP:** includes negatives  

### 5) `getCumulativePercentages(KeyedValues)` *(mocked)*
- **ECP:** two positive items  
- **BVA:** single item  
- **Edge:** duplicate keys  
- **Edge:** negative values  

## 2.2 Mocking (benefits and drawbacks)

**Benefits**
- isolates the unit under test and avoids reliance on external implementations
- enables precise edge-case construction
- makes tests repeatable and deterministic

**Drawbacks**
- less realistic than integration with concrete classes
- tests can be brittle if method interaction patterns change
- may miss integration-level issues

---

# 3 Test cases developed

This section lists the **test classes and test methods**, organized by the **source method** they test, and maps each test to the partition(s) from Section 2.

---

## 3.1 RangeTest (class: `org.jfree.data.test.RangeTest`)

### A) `getLowerBound()`
- `testGetLowerBound_PositiveRange()` — **ECP: positive range**
- `testGetLowerBound_NegativeRange()` — **ECP: negative range**
- `testGetLowerBound_DegenerateRange()` — **BVA: lower==upper**

### B) `getUpperBound()`
- `testGetUpperBound_PositiveRange()` — **ECP: positive range**
- `testGetUpperBound_MixedRange()` — **ECP: mixed range**
- `testGetUpperBound_DegenerateRange()` — **BVA: lower==upper**

### C) `getLength()`
- `testGetLength_StandardPositiveRange()` — **ECP: standard**
- `testGetLength_MixedRange()` — **ECP: mixed range**
- `testGetLength_DegenerateRange()` — **BVA: length=0**

### D) `contains(double value)`
- `testContains_ValueInsideRange()` — **ECP: inside**
- `testContains_ValueEqualsLowerBound()` — **BVA: at lower**
- `testContains_ValueEqualsUpperBound()` — **BVA: at upper**
- `testContains_ValueBelowLowerBound()` — **ECP: below**
- `testContains_ValueAboveUpperBound()` — **ECP: above**
- `testContains_DegenerateRange_Contains()` — **BVA: degenerate contains**
- `testContains_DegenerateRange_NotContains()` — **BVA/Edge: degenerate not contains**

### E) `getCentralValue()`
- `testGetCentralValue_StandardRange()` — **ECP: standard**
- `testGetCentralValue_NegativeRange()` — **ECP: negative-only**
- `testGetCentralValue_MixedRange()` — **ECP: mixed**
- `testGetCentralValue_DegenerateRange()` — **BVA: degenerate**
- `testGetCentralValue_SmallDecimals()` — **Precision**
- `testGetCentralValue_LargeNumbers()` — **Stress**

---

## 3.2 DataUtilitiesTest (class: `org.jfree.data.test.DataUtilitiesTest`)

### A) `calculateColumnTotal(Values2D data, int column)` *(mocked)*
- `testCalculateColumnTotal_NormalValues()` — **ECP: normal numeric**
- `testCalculateColumnTotal_AllZeros()` — **ECP: zeros**
- `testCalculateColumnTotal_WithNegatives()` — **ECP: negatives included**

### B) `calculateRowTotal(Values2D data, int row)` *(mocked)*
- `testCalculateRowTotal_NormalValues()` — **ECP: normal numeric**
- `testCalculateRowTotal_MixedValues()` — **ECP: mixed signs**
- `testCalculateRowTotal_AllZeros()` — **ECP: zeros**

### C) `createNumberArray(double[] data)`
- `testCreateNumberArray_NormalArray()` — **ECP: normal**
- `testCreateNumberArray_EmptyArray()` — **BVA: empty**
- `testCreateNumberArray_SingleElement()` — **BVA: single element**
- `testCreateNumberArray_WithNegatives()` — **ECP: negatives**

### D) `createNumberArray2D(double[][] data)`
- `testCreateNumberArray2D_Rectangular()` — **ECP: rectangular**
- `testCreateNumberArray2D_Empty2D()` — **BVA: empty 2D**
- `testCreateNumberArray2D_Jagged2D()` — **Edge: jagged**
- `testCreateNumberArray2D_WithNegatives()` — **ECP: negatives**

### E) `getCumulativePercentages(KeyedValues data)` *(mocked)*
- `testGetCumulativePercentages_TwoItems()` — **ECP: two positives**
- `testGetCumulativePercentages_SingleItem()` — **BVA: single item**
- `testGetCumulativePercentages_DuplicateKeys()` — **Edge: duplicate keys**
- `testGetCumulativePercentages_WithNegativeValues()` — **Edge: negative values**

---

# 4 How the team work/effort was divided and managed

We divided the work by class and then cross-reviewed to ensure consistency and shared understanding:

- **Range test design and implementation:** Shaheer Shakir, Mustafa Ayobi  
- **DataUtilities test design and Mockito mocking:** Haseeb Tahir, Moyo Ogunjobi  

Our workflow:
1. As a group, we reviewed Javadocs and agreed on the 5 Range methods to test.
2. Each sub-team derived partitions and drafted test cases.
3. We implemented tests with a consistent naming convention and one partition per test.
4. We ran the full suite together and reviewed failures to determine whether they were due to injected defects in the SUT or due to test/mocking configuration.
5. We peer-reviewed each other’s tests to ensure traceability between partitions (Section 2) and tests (Section 3).

---

# 5 Difficulties encountered, challenges overcome, and lessons learned

A major challenge was handling **floating-point comparisons**. To avoid false failures due to rounding error, we consistently used a **delta tolerance** in assertions (e.g., `1e-9`, tighter in precision tests such as `1e-12`).

Another challenge was correctly using **Mockito** for interface-based inputs. Methods such as `calculateRowTotal()` depend on `getColumnCount()` plus multiple calls to `getValue(row,col)`, while `calculateColumnTotal()` depends on `getRowCount()` plus calls to `getValue(row,col)`. If we failed to stub a required call, a test could fail for reasons unrelated to the SUT’s behavior. We solved this by stubbing every interface method that the SUT method invokes.

This lab reinforced that writing good unit tests requires careful interpretation of requirements, systematic partitioning (ECP/BVA), and disciplined test setup—especially when mocking.

---

# 6 Comments/feedback on the lab itself

This lab was effective for practicing requirements-based testing and learning how to use mocking frameworks in unit tests. The most valuable part was translating Javadoc requirements into equivalence classes and boundary tests, then implementing them as clear JUnit test methods.

One improvement would be providing an additional fully worked example showing (1) how to derive partitions from a Javadoc requirement and (2) how to set up Mockito stubs for `Values2D` and `KeyedValues`, since mocking was the steepest initial learning curve.

---
