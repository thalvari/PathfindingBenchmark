/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.util;

/**
 * Kekoon laitettavien solmuolioiden toteutus. Solmujen keskinäinen järjestys
 * määräytyy ensin prioriteetin, sitten heuristisen arvon perusteella.
 *
 * @author thalvari
 */
public class Node implements Comparable<Node> {

    private final int idx;
    private int dist;
    private final int heuristic;

    /**
     * Konstruktori.
     *
     * @param idx Solmun indeksi.
     * @param dist Solmun etäisyys lähtösolmuun.
     * @param heuristic Solmun heuristinen arvo.
     */
    public Node(int idx, int dist, int heuristic) {
        this.idx = idx;
        this.dist = dist;
        this.heuristic = heuristic;
    }

    /**
     * Palauttaa solmun indeksin.
     *
     * @return Indeksi.
     */
    public int getIdx() {
        return idx;
    }

    @Override
    public int compareTo(Node o) {
        if (getPriority() == o.getPriority()) {
            return Integer.valueOf(heuristic).compareTo(o.heuristic);
        } else {
            return Integer.valueOf(getPriority()).compareTo(o.getPriority());
        }
    }

    private int getPriority() {
        return dist + heuristic;
    }

    /**
     * Päivittää solmun etäisyyden lähtösolmusta.
     *
     * @param dist Uusi etäisyys.
     */
    public void setDist(int dist) {
        this.dist = dist;
    }
}
