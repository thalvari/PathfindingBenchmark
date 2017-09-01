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
    private Node[] nodes;
    private int length;
    private int maxHeapSize;

    /**
     * Konstruktori.
     */
    public NodeMinHeap() {
        nodes = new Node[INIT_ARR_LEN];
    }

    /**
     * Lisää solmun kekoon.
     *
     * @param node Solmu.
     */
    public void insert(Node node) {
        checkIfFull();
        length++;
        if (length > maxHeapSize) {
            maxHeapSize = length;
        }

        int idx = length;
        while (idx > 1 && node.compareTo(getNode(parent(idx))) < 0) {
            setNode(getNode(parent(idx)), idx);
            idx = parent(idx);
        }

        setNode(node, idx);
    }

    private void checkIfFull() {
        if (length == nodes.length) {
            Node[] newNodes = new Node[nodes.length * 2];
            System.arraycopy(nodes, 0, newNodes, 0, length);
            nodes = newNodes;
        }
    }

    private Node getNode(int idx) {
        return nodes[idx - 1];
    }

    private int parent(int idx) {
        return idx / 2;
    }

    private void setNode(Node node, int idx) {
        node.setHeapIdx(idx);
        nodes[idx - 1] = node;
    }

    /**
     * Poistaa pienimmän prioriteetin solmun keosta.
     *
     * @return Solmu.
     */
    public Node delMin() {
        Node node = getNode(1);
        setNode(getNode(length), 1);
        length--;
        heapify(1);
        return node;
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
        Node node = getNode(idx1);
        setNode(getNode(idx2), idx1);
        setNode(node, idx2);
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
     * @param node Solmu.
     * @param newDist Uusi etäisyys lähtösolmuun.
     */
    public void decKey(Node node, int newDist) {
        node.setDist(newDist);
        int idx = node.getHeapIdx();
        while (idx > 1 && node.compareTo(getNode(parent(idx))) < 0) {
            swap(idx, parent(idx));
            idx = parent(idx);
        }
    }

    public int getMaxHeapSize() {
        return maxHeapSize;
    }
}
