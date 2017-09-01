/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

/**
 * Kekoon laitettavien solmuolioiden toteutus. Solmujen keskinäinen järjestys
 * määräytyy ensin prioriteetin, sitten heuristisen arvon perusteella.
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
     * @param symbol Koordinaatteja vastaava symboli kartalla.
     */
    public Node(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        dist = Integer.MAX_VALUE;
    }

    /**
     * Kertoo onko solmu läpikuljettavissa.
     *
     * @return Totuusarvo.
     */
    public boolean isPassable() {
        return symbol != '@' && symbol != 'O' && symbol != 'T' && symbol != 'W';
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

    @Override
    public boolean equals(Object obj) {
        Node o = (Node) obj;
        return x == o.x && y == o.y;
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
     * Palauttaa edellisen solmun polulla lähtösolmuun.
     *
     * @return Solmu.
     */
    public Node getPrev() {
        return prev;
    }

    /**
     * Palauttaa solmun koordinaatteja vastaavan symbolin kartalla.
     *
     * @return Symboli.
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
     * Asettaa edeltävän solmun polulla lähtösolmuun.
     *
     * @param prev Solmu.
     */
    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isClosed() {
        return symbol == 'c';
    }

    public void setClosed() {
        symbol = 'c';
    }

    public void setPath() {
        symbol = 'P';
    }

    public void init(char origSymbol) {
        dist = Integer.MAX_VALUE;
        heapIdx = 0;
        heuristic = 0;
        prev = null;
        symbol = origSymbol;
    }
}
