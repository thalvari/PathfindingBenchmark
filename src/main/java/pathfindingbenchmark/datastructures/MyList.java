/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

/**
 *
 * @author thalvari
 */
public class MyList<T> {

    private static final int INIT_ARR_LEN = 8;
    private Object[] array;
    private int length;

    /**
     * Konstruktori.
     */
    public MyList() {
        array = new Object[INIT_ARR_LEN];
    }

    /**
     * Lisää kokonaisluvun listalle.
     *
     * @param i Kokonaisluku.
     */
    public void add(T t) {
        checkIfFull();
        array[length] = t;
        length++;
    }

    private void checkIfFull() {
        if (length == array.length) {
            Object[] newArray = new Object[array.length * 2];
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
    @SuppressWarnings("unchecked")
    public T get(int i) {
        return (T) array[i];
    }

    /**
     * Palauttaa listan koon.
     *
     * @return Koko.
     */
    public int size() {
        return length;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        Object[] newArray = new Object[length];
        System.arraycopy(array, 0, newArray, 0, length);
        return (T[]) newArray;
    }
}
