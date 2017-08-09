/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Kokonaislukulistan toteutus.
 *
 * @author thalvari
 */
public class IntList {

    private final List<Integer> list;

    /**
     * Konstruktori.
     */
    public IntList() {
        list = new ArrayList<>();
    }

    /**
     * Lisää kokonaisluvun listalle.
     *
     * @param i Kokonaisluku.
     */
    public void add(int i) {
        list.add(i);
    }

    /**
     * Palauttaa kokonaisluvun.
     *
     * @param idx Indeksi.
     * @return Kokonaisluku.
     */
    public int get(int idx) {
        return list.get(idx);
    }

    /**
     * Palauttaa listan koon.
     *
     * @return Koko.
     */
    public int size() {
        return list.size();
    }

    /**
     * Kertoo sisältääkö lista annetun kokonaisluvun.
     *
     * @param i Kokonaisluku.
     * @return Totuusarvo.
     */
    public boolean contains(int i) {
        return list.contains(i);
    }
}
