/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class NodeMinHeap {

    private static final int INIT_ARR_LEN = 8;
    private Node[] arr;
    private int length;

    public NodeMinHeap(int n) {
        arr = new Node[INIT_ARR_LEN];
    }

    private int parent(int idx) {
        return idx / 2;
    }

    private int left(int idx) {
        return 2 * idx;
    }

    private int right(int idx) {
        return 2 * idx + 1;
    }

    private void heapify(int idx) {
        int left = left(idx);
        int right = right(idx);
        if (right <= length) {
            int min;
            if (getNode(left).compareTo(getNode(right)) < 0) {
                min = left;
            } else {
                min = right;
            }

            if (getNode(idx).compareTo(getNode(min)) > 0) {
                swap(idx, min);
                heapify(min);
            }
        } else if (left == length
                && getNode(idx).compareTo(getNode(left)) > 0) {

            swap(idx, left);
        }
    }

    private Node getNode(int idx) {
        return arr[idx - 1];
    }

    private void swap(int idx1, int idx2) {
        Node tmpNode = getNode(idx1);
        setNode(idx1, getNode(idx2));
        setNode(idx2, tmpNode);
    }

    private void setNode(int idx, Node u) {
        arr[idx - 1] = u;
    }

    public Node delMin() {
        Node max = getNode(1);
        setNode(1, getNode(length));
        length--;
        heapify(1);
        return max;
    }

    public void insert(Node u) {
        if (full()) {
            Node[] tempArray = new Node[arr.length * 2];
            for (int j = 0; j < length; j++) {
                tempArray[j] = arr[j];
            }

            arr = tempArray;
        }

        length++;
        int idx = length;
        while (idx > 1 && getNode(parent(idx)).compareTo(u) > 0) {
            setNode(idx, getNode(parent(idx)));
            idx = parent(idx);
        }
        setNode(idx, u);
    }

    private boolean full() {
        return length == arr.length;
    }

    public boolean empty() {
        return length == 0;
    }

    public void clear() {
        arr = new Node[INIT_ARR_LEN];
        length = 0;
    }
}
