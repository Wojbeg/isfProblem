package algorithms;

import org.example.algorithms.BruteForce;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BruteForceTest {

    @Test
    public void testPermutationsWithEmptyList() {
        List<Integer> emptyList = Arrays.asList();
        List<List<Integer>> result = BruteForce.generatePermutations(emptyList);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isEmpty());
    }

    @Test
    public void testPermutationsWithOneElement() {
        List<Integer> list = Arrays.asList(1);
        List<List<Integer>> result = BruteForce.generatePermutations(list);
        assertEquals(1, result.size());
        assertEquals(list, result.get(0));
    }

    @Test
    public void testPermutationsWithTwoElements() {
        List<Integer> list = Arrays.asList(1, 2);
        List<List<Integer>> result = BruteForce.generatePermutations(list);
        assertEquals(2, result.size());
        assertTrue(result.contains(Arrays.asList(1, 2)));
        assertTrue(result.contains(Arrays.asList(2, 1)));
    }

    @Test
    public void testPermutationsWithThreeElements() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<List<Integer>> result = BruteForce.generatePermutations(list);
        assertEquals(6, result.size());
        assertTrue(result.contains(Arrays.asList(1, 2, 3)));
        assertTrue(result.contains(Arrays.asList(1, 3, 2)));
        assertTrue(result.contains(Arrays.asList(2, 1, 3)));
        assertTrue(result.contains(Arrays.asList(2, 3, 1)));
        assertTrue(result.contains(Arrays.asList(3, 2, 1)));
        assertTrue(result.contains(Arrays.asList(3, 1, 2)));
    }
}