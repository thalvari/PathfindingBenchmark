/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

/**
 * Solmuolion toteutus.
 *
 * @author thalvari
 */
public class Node implements Comparable<Node> {

    private final int idx;
    private final double priority;

    /**
     * Konstruktori.
     *
     * @param idx Solmun indeksi.
     * @param priority Solmun prioriteettiarvon.
     */
    public Node(int idx, double priority) {
        this.idx = idx;
        this.priority = priority;
    }

    /**
     * Vaihtoehtoinen konstruktori.
     *
     * @param idx Solmun indeksi.
     */
    public Node(int idx) {
        this(idx, 0);
    }

    /**
     * Palauttaa solmun indeksin.
     *
     * @return Indeksi.
     */
    public int getIdx() {
        return idx;
    }

    /**
     * Palauttaa solmun prioriteettiarvon.
     *
     * @return Prioriteettiarvo.
     */
    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Node o) {
        return Double.valueOf(priority).compareTo(o.priority);
    }
}
