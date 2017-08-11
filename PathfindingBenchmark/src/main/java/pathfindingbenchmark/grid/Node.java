/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

/**
 * Kekoon laitettavien solmuolioiden toteutus. Solmujen keskinäinen järjestys
 * määräytyy ensin prioriteetin, sitten heuristisen arvon mukaan.
 *
 * @author thalvari
 */
public class Node implements Comparable<Node> {

    private final int idx;
    private final long dist;
    private final long heuristic;

    /**
     * Konstruktori.
     *
     * @param idx Solmun indeksi.
     * @param dist Solmun prioriteettiarvon.
     * @param heuristic Solmun heuristinen arvo.
     */
    public Node(int idx, long dist, long heuristic) {
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
            return Long.valueOf(heuristic).compareTo(o.heuristic);
        } else {
            return Long.valueOf(getPriority()).compareTo(o.getPriority());
        }
    }

    private long getPriority() {
        return dist + heuristic;
    }
}
