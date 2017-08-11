/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

/**
 * Kokonaislukulistan toteutus.
 *
 * @author thalvari
 */
public class IntList {

    private static final int INIT_ARR_LEN = 8;
    private int[] arr;
    private int length;

    /**
     * Konstruktori.
     */
    public IntList() {
        arr = new int[INIT_ARR_LEN];
    }

    /**
     * Lisää kokonaisluvun listalle.
     *
     * @param i Kokonaisluku.
     */
    public void add(int i) {
        if (full()) {
            int[] tempArray = new int[arr.length * 2];
            for (int j = 0; j < length; j++) {
                tempArray[j] = arr[j];
            }

            arr = tempArray;
        }

        arr[length] = i;
        length++;
    }

    private boolean full() {
        return length == arr.length;
    }

    /**
     * Palauttaa indeksiä vastaavan kokonaisluvun kokonaisluvun.
     *
     * @param idx Indeksi.
     * @return Kokonaisluku.
     */
    public int get(int idx) {
        return arr[idx];
    }

    /**
     * Palauttaa listan koon.
     *
     * @return Koko.
     */
    public int size() {
        return length;
    }

    /**
     * Kertoo sisältääkö lista annetun kokonaisluvun.
     *
     * @param i Kokonaisluku.
     * @return Totuusarvo.
     */
    public boolean contains(int i) {
        for (int j = 0; j < length; j++) {
            if (arr[j] == i) {
                return true;
            }
        }

        return false;
    }
}
