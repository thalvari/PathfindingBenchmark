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
    private final double cost;
    private final double h;
    private final Grid grid;

    /**
     * Konstruktori.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @param cost Solmuun siirtymisen hinta.
     * @param h Heuristinen arvo.
     * @param grid Verkko.
     */
    public Node(int x, int y, double cost, double h, Grid grid) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.h = h;
        this.grid = grid;
    }

    /**
     * Vaihtoehtoinen konstruktori.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @param cost Solmuun siirtymisen hinta.
     * @param grid Verkko.
     */
    public Node(int x, int y, double cost, Grid grid) {
        this(x, y, cost, 0, grid);
    }

    /**
     * Vaihtoehtoinen konstruktori.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @param grid Verkko.
     */
    public Node(int x, int y, Grid grid) {
        this(x, y, 0, 0, grid);
    }

    /**
     * Palauttaa indeksin.
     *
     * @return Indeksi.
     */
    public int getIdx() {
        return grid.getIdx(x, y);
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
     * Palauttaa solmuun siirtymisen hinnan.
     *
     * @return Hinta.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Palauttaa solmun heuristisen arvon.
     *
     * @return Heuristinen arvo.
     */
    public double getH() {
        return h;
    }

    @Override
    public int compareTo(Node o) {
        return Double.valueOf(cost + h).compareTo(o.cost + o.h);
    }
}
