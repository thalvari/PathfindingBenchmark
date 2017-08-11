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
        checkIfFull();
        arr[length] = i;
        length++;
    }

    private void checkIfFull() {
        if (length == arr.length) {
            int[] newArr = new int[arr.length * 2];
            for (int j = 0; j < length; j++) {
                newArr[j] = arr[j];
            }

            arr = newArr;
        }
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
}
