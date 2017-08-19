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

    private boolean closed;
    private int dist;
    private int heapIdx;
    private int heuristic;
    private Node prev;
    private final char symbol;
    private final int x;
    private final int y;

    /**
     * Konstruktori.
     *
     * @param idx Solmun indeksi.
     * @param dist Solmun etäisyys lähtösolmuun.
     * @param heuristic Solmun heuristinen arvo.
     */
    public Node(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }

    public boolean isPassable() {
        return symbol == '.' || symbol == 'G' || symbol == 'S';
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

    public void reset() {
        closed = false;
        dist = Integer.MAX_VALUE;
        heapIdx = 0;
        heuristic = 0;
        prev = null;
    }

    public boolean isClosed() {
        return closed;
    }

    public int getDist() {
        return dist;
    }

    public int getHeapIdx() {
        return heapIdx;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public Node getPrev() {
        return prev;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /**
     * Päivittää solmun etäisyyden lähtösolmusta.
     *
     * @param dist Uusi etäisyys.
     */
    public void setDist(int dist) {
        this.dist = dist;
    }

    public void setHeapIdx(int heapIdx) {
        this.heapIdx = heapIdx;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public void setIsClosed(boolean closed) {
        this.closed = closed;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}
