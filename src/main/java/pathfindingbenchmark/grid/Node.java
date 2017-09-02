/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import javafx.scene.paint.Color;

/**
 * Solmun toteutus. Solmujen keskinäinen järjestys määräytyy ensin prioriteetin,
 * sitten heuristisen arvon perusteella.
 *
 * @author thalvari
 */
public class Node implements Comparable<Node> {

    private int dist;
    private int heapIdx;
    private int heuristic;
    private Node prev;
    private char symbol;
    private final int x;
    private final int y;

    /**
     * Konstruktori.
     *
     * @param x Solmun x-koordinaatti.
     * @param y Solmun x-koordinaatti.
     * @param symbol Koordinaatteja vastaava merkki kartalla.
     */
    public Node(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        dist = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(Node o) {
        if (getPriority() == o.getPriority()) {
            return Integer.valueOf(heuristic).compareTo(o.heuristic);
        } else {
            return Integer.valueOf(getPriority()).compareTo(o.getPriority());
        }
    }

    @Override
    public boolean equals(Object obj) {
        Node o = (Node) obj;
        return x == o.x && y == o.y;
    }

    /**
     * Palauttaa solmun symbolia vastaavan värin.
     *
     * @return Väri.
     */
    public Color getColor() {
        switch (symbol) {
            case '.':
            case 'G':
                return Color.GREY;
            case '@':
            case 'O':
                return Color.BLACK;
            case 'T':
                return Color.GREEN;
            case 'S':
                return Color.PURPLE;
            case 'W':
                return Color.BLUE;
            case 'c':
                return Color.YELLOW;
            default:
                return Color.RED;
        }
    }

    /**
     * Palauttaa solmun etäisyyden lähtösolmuun.
     *
     * @return Etäisyys.
     */
    public int getDist() {
        return dist;
    }

    /**
     * Palauttaa solmun indeksin keossa.
     *
     * @return Indeksi.
     */
    public int getHeapIdx() {
        return heapIdx;
    }

    /**
     * Palauttaa edellisen solmun polulla lähtösolmusta.
     *
     * @return Edellinen solmu.
     */
    public Node getPrev() {
        return prev;
    }

    /**
     * Palauttaa solmun merkin.
     *
     * @return Merkki.
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Palauttaa solmun x-koordinaatin.
     *
     * @return X-koordinaatti.
     */
    public int getX() {
        return x;
    }

    /**
     * Palauttaa solmun y-koordinaatin.
     *
     * @return Y-koordinaatti.
     */
    public int getY() {
        return y;
    }

    /**
     * Alustaa solmun.
     *
     * @param origSymbol Solmua vastaava alkuperäinen merkki kartalla.
     */
    public void init(char origSymbol) {
        dist = Integer.MAX_VALUE;
        heapIdx = 0;
        heuristic = 0;
        prev = null;
        symbol = origSymbol;
    }

    /**
     * Kertoo kuuluuko solmu suljettuun joukkoon.
     *
     * @return Totuusarvo.
     */
    public boolean isClosed() {
        return symbol == 'c';
    }

    /**
     * Kertoo onko solmu läpikuljettavissa.
     *
     * @return Totuusarvo.
     */
    public boolean isPassable() {
        return symbol != '@' && symbol != 'O' && symbol != 'T' && symbol != 'W';
    }

    /**
     * Merkitsee solmun kuuluvaksi suljettuun joukkoon.
     */
    public void setClosed() {
        symbol = 'c';
    }

    /**
     * Päivittää solmun etäisyyden lähtösolmusta.
     *
     * @param dist Uusi etäisyys.
     */
    public void setDist(int dist) {
        this.dist = dist;
    }

    /**
     * Asettaa solmun kekoindeksin.
     *
     * @param heapIdx Indeksi.
     */
    public void setHeapIdx(int heapIdx) {
        this.heapIdx = heapIdx;
    }

    /**
     * Asettaa solmun heuristisen arvon.
     *
     * @param heuristic Heuristisen arvo.
     */
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Merkitsee solmun kuuluvaksi lyhimpään polkuun.
     */
    public void setPath() {
        symbol = 'P';
    }

    /**
     * Asettaa edeltävän solmun polulla lähtösolmusta.
     *
     * @param prev Solmu.
     */
    public void setPrev(Node prev) {
        this.prev = prev;
    }

    private int getPriority() {
        return dist + heuristic;
    }
}
