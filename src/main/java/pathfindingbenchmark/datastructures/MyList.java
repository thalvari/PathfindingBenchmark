/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

/**
 * Listan toteutus.
 *
 * @author thalvari
 * @param <T> Listan alkioiden tyyppi.
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
     * Lisää alkion listalle.
     *
     * @param t Alkio.
     */
    public void add(T t) {
        checkIfFull();
        array[length] = t;
        length++;
    }

    /**
     * Tarkastaa sisältääkö lista tietyn alkion.
     *
     * @param t Alkio.
     * @return Totuusarvo.
     */
    @SuppressWarnings("unchecked")
    public boolean contains(T t) {
        for (int i = 0; i < length; i++) {
            if (t.equals((T) array[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Palauttaa indeksiä vastaavan alkion listalta.
     *
     * @param i Indeksi.
     * @return Alkio.
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

    private void checkIfFull() {
        if (length == array.length) {
            Object[] newArray = new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, length);
            array = newArray;
        }
    }
}
