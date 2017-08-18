/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import pathfindingbenchmark.util.Node;

/**
 * Minimikeko solmu-olioille.
 *
 * @author thalvari
 */
public class NodeMinHeap {

    private static final int INIT_ARR_LEN = 8;
    private Node[] arr;
    private final int[] heapIdx;
    private int length;

    /**
     * Konstruktori.
     *
     * @param n Solmujen määrä.
     */
    public NodeMinHeap(int n) {
        arr = new Node[INIT_ARR_LEN];
        heapIdx = new int[n + 1];
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
        while (idx > 1 && u.compareTo(getNode(parent(idx))) < 0) {
            setNode(getNode(parent(idx)), idx);
            idx = parent(idx);
        }

        setNode(u, idx);
    }

    private void checkIfFull() {
        if (length == arr.length) {
            Node[] newArr = new Node[arr.length * 2];
            System.arraycopy(arr, 0, newArr, 0, length);
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
        heapIdx[u.getIdx()] = idx;
    }

    /**
     * Poistaa pienimmän prioriteetin solmun keosta.
     *
     * @return Solmu.
     */
    public Node delMin() {
        Node min = getNode(1);
        setNode(getNode(length), 1);
        length--;
        heapify(1);
        return min;
    }

    private void heapify(int idx) {
        int left = left(idx);
        int right = right(idx);
        int minIdx = idx;
        if (left <= length && getNode(left).compareTo(getNode(idx)) < 0) {
            minIdx = left;
        }

        if (right <= length && getNode(right).compareTo(getNode(minIdx)) < 0) {
            minIdx = right;
        }

        if (minIdx != idx) {
            swap(idx, minIdx);
            heapify(minIdx);
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
     * Pienentää keossa olevan solmun etäisyyttä lähtösolmusta.
     *
     * @param nodeIdx Solmun indeksi verkossa.
     * @param newDist Uusi etäisyys lähtösolmuun.
     */
    public void decKey(int nodeIdx, int newDist) {
        int idx = heapIdx[nodeIdx];
        Node u = getNode(idx);
        u.setDist(newDist);
        while (idx > 1 && u.compareTo(getNode(parent(idx))) < 0) {
            swap(idx, parent(idx));
            idx = parent(idx);
        }
    }

    /**
     * Kertoo onko solmu keossa.
     *
     * @param nodeIdx Solmun indeksi verkossa.
     * @return Totuusarvo.
     */
    public boolean hasNode(int nodeIdx) {
        return heapIdx[nodeIdx] != 0;
    }
}
