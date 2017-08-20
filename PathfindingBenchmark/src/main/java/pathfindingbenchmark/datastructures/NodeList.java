/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import pathfindingbenchmark.util.Node;

/**
 *
 * @author thalvari
 */
public class NodeList {

    private static final int INIT_ARR_LEN = 8;
    private Node[] array;
    private int length;

    /**
     * Konstruktori.
     */
    public NodeList() {
        array = new Node[INIT_ARR_LEN];
    }

    /**
     * Lisää kokonaisluvun listalle.
     *
     * @param i Kokonaisluku.
     */
    public void add(Node node) {
        checkIfFull();
        array[length] = node;
        length++;
    }

    private void checkIfFull() {
        if (length == array.length) {
            Node[] newArray = new Node[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, length);
            array = newArray;
        }
    }

    /**
     * Palauttaa indeksiä vastaavan kokonaisluvun kokonaisluvun.
     *
     * @param idx Indeksi.
     * @return Kokonaisluku.
     */
    public Node get(int i) {
        return array[i];
    }

    /**
     * Palauttaa listan koon.
     *
     * @return Koko.
     */
    public int size() {
        return length;
    }
}
