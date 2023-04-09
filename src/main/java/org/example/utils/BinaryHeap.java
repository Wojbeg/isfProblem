package org.example.utils;

import java.util.List;

/*
This class is no longer in use, it was created to help sort data im EDF (Earliest Deadline First).
Pickers with best time were sorted to help get smallest one. Now priority queue is used instead.
*/
@Deprecated
public class BinaryHeap<T extends Comparable<T>> {

    private T[] data;
    private int size;

    public BinaryHeap(int capacity) {
        data = (T[]) new Comparable[capacity];
        size = 0;
    }

    public BinaryHeap(List<T> list) {
        size = list.size();
        data = (T[]) new Comparable[size];
        for (int i = 0; i < size; i++) {
            data[i] = list.get(i);
        }
        heapify();
    }

    private void heapify() {
        for (int i = size / 2; i >= 0; i--) {
            siftDown(i);
        }
    }

    public void insert(T value) {
        if (size == data.length) {
            throw new IllegalStateException("Heap is full");
        }
        data[size] = value;
        siftUp(size);
        size++;
    }

    public void set(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        T oldValue = data[index];
        data[index] = value;
        if (value.compareTo(oldValue) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    public T extractMin() {
        if (isEmpty()) {
            return null;
        }
        T min = data[0];
        data[0] = data[size - 1];
        size--;
        siftDown(0);
        return min;
    }

    public T min() {
        if (isEmpty()) {
            return null;
        }
        return data[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void siftUp(int index) {
        while (index > 0 && data[parent(index)].compareTo(data[index]) > 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private void siftDown(int index) {
        while (leftChild(index) < size) {
            int childIndex = leftChild(index);
            if (rightChild(index) < size && data[rightChild(index)].compareTo(data[childIndex]) < 0) {
                childIndex = rightChild(index);
            }
            if (data[index].compareTo(data[childIndex]) > 0) {
                swap(index, childIndex);
                index = childIndex;
            } else {
                break;
            }
        }
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    private void swap(int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }


}

