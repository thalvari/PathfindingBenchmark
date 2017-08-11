/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import pathfindingbenchmark.grid.Node;

/**
 * Minimikeko solmu-olioille.
 *
 * @author thalvari
 */
public class NodeMinHeap {

    private static final int INIT_ARR_LEN = 8;
    private Node[] arr;
    private int length;

    /**
     * Konstruktori.
     */
    public NodeMinHeap() {
        arr = new Node[INIT_ARR_LEN];
    }

    /**
     * Lisää solmun kekoon.
     *
     * @param u Solmu.
     */
    public void insert(Node u) {
        checkIfFull();
        length++;
        int idx = length;
        while (idx > 1 && getNode(parent(idx)).compareTo(u) > 0) {
            setNode(getNode(parent(idx)), idx);
            idx = parent(idx);
        }
        setNode(u, idx);
    }

    private void checkIfFull() {
        if (length == arr.length) {
            Node[] newArr = new Node[arr.length * 2];
            for (int j = 0; j < length; j++) {
                newArr[j] = arr[j];
            }

            arr = newArr;
        }
    }

    private Node getNode(int idx) {
        return arr[idx - 1];
    }

    private int parent(int idx) {
        return idx / 2;
    }

    private void setNode(Node u, int idx) {
        arr[idx - 1] = u;
    }

    /**
     * Poistaa pienimmän prioriteetin solmun keosta.
     *
     * @return
     */
    public Node delMin() {
        Node max = getNode(1);
        setNode(getNode(length), 1);
        length--;
        heapify(1);
        return max;
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

    private int left(int idx) {
        return 2 * idx;
    }

    private int right(int idx) {
        return 2 * idx + 1;
    }

    private void swap(int idx1, int idx2) {
        Node u = getNode(idx1);
        setNode(getNode(idx2), idx1);
        setNode(u, idx2);
    }

    /**
     * Kertoo onko keko tyhjä.
     *
     * @return Totuusarvo.
     */
    public boolean empty() {
        return length == 0;
    }

    /**
     * Tyhjentää keon.
     */
    public void clear() {
        arr = new Node[INIT_ARR_LEN];
        length = 0;
    }
}
