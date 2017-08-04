/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

/**
 * Solmuolio.
 *
 * @author thalvari
 */
public class Node implements Comparable<Node> {

    private final int x;
    private final int y;
    private final Integer idx;
    private Double cost;

    /**
     * Konstruktori.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @param idx Solmun indeksi.
     * @param cost Solmuun siirtymisen hinta.
     */
    public Node(int x, int y, int idx, double cost) {
        this.x = x;
        this.y = y;
        this.idx = idx;
        this.cost = cost;
    }

    /**
     * Vaihtoehtoinen konstruktori.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @param grid Verkko.
     */
    public Node(int x, int y, Grid grid) {
        this(x, y, grid.getIdx(x, y), 0);
    }

    /**
     * Vaihtoehtoinen konstruktori.
     *
     * @param idx Solmun indeksi.
     * @param cost Solmuun siirtymisen hinta.
     * @param grid Verkko.
     */
    public Node(int idx, double cost, Grid grid) {
        this(grid.getX(idx), grid.getY(idx), idx, cost);
    }

    /**
     * Palauttaa x-koordinaatin.
     *
     * @return X-koordinaatti.
     */
    public int getX() {
        return x;
    }

    /**
     * Palauttaa y-koordinaatin.
     *
     * @return Y-koordinaatti.
     */
    public int getY() {
        return y;
    }

    /**
     * Palauttaa indeksin.
     *
     * @return Indeksi.
     */
    public Integer getIdx() {
        return idx;
    }

    /**
     * Palauttaa solmuun siirtymisen hinnan.
     *
     * @return Hinta.
     */
    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(Node o) {
        if (cost.equals(o.cost)) {
            return getIdx().compareTo(o.getIdx());
        } else {
            return cost.compareTo(o.cost);
        }
    }

    @Override
    public boolean equals(Object obj) {
        Node o = (Node) obj;
        return idx.equals(o.idx);
    }
}
