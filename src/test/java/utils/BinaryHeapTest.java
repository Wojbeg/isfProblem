package utils;

import org.example.utils.BinaryHeap;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.*;

public class BinaryHeapTest {

    @Test
    public void testInsert() {
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(5);
        heap.insert(3);
        heap.insert(2);
        heap.insert(4);
        heap.insert(1);
        heap.insert(5);

        assertEquals(valueOf(1), heap.extractMin());
        assertEquals(valueOf(2), heap.extractMin());
        assertEquals(valueOf(3), heap.extractMin());
        assertEquals(valueOf(4), heap.extractMin());
        assertEquals(valueOf(5), heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testSet() {
        BinaryHeap<Integer> heap = new BinaryHeap<>(Arrays.asList(5, 2, 4, 3, 1));
        heap.set(3, 0);

        assertEquals(valueOf(0), heap.min());
        assertEquals(valueOf(0), heap.extractMin());
        assertEquals(valueOf(1), heap.extractMin());
        assertEquals(valueOf(2), heap.extractMin());
        assertEquals(valueOf(4), heap.extractMin());
        assertEquals(valueOf(5), heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testHeapify() {
        List<Integer> list = Arrays.asList(5, 2, 4, 3, 1);
        BinaryHeap<Integer> heap = new BinaryHeap<>(list);

        assertEquals(valueOf(1), heap.extractMin());
        assertEquals(valueOf(2), heap.extractMin());
        assertEquals(valueOf(3), heap.extractMin());
        assertEquals(valueOf(4), heap.extractMin());
        assertEquals(valueOf(5), heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        BinaryHeap<Integer> heap = new BinaryHeap<>(5);
        assertTrue(heap.isEmpty());
        heap.insert(1);
        assertFalse(heap.isEmpty());
    }
}