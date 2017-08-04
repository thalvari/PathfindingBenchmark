/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algos;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 * Dijkstran algoritmin toteutus.
 *
 * @author thalvari
 */
public class Dijkstra {

    private final Grid grid;
    private final BigDecimal cost[];
    private final Node path[];
    private final boolean visited[];

    /**
     * Konstruktori luo aputietorakenteet.
     *
     * @param grid Verkko.
     */
    public Dijkstra(Grid grid) {
        this.grid = grid;
        cost = new BigDecimal[grid.getN() + 1];
        path = new Node[grid.getN() + 1];
        visited = new boolean[grid.getN() + 1];
    }

    /**
     * Itse algoritmi laskee jokaisen solmun etäisyyden lähtösolmuun.
     *
     * @param s Lähtösolmu.
     */
    public void run(Node s) {
        init(s);
        PriorityQueue<Node> heap = new PriorityQueue();
        heap.add(new Node(s.getIdx(), BigDecimal.valueOf(0), grid));
        while (!heap.isEmpty()) {
            Node u = heap.poll();
            if (visited[u.getIdx()]) {
                continue;
            }

            visited[u.getIdx()] = true;
            for (Node v : grid.getAdjList(u)) {
                if (!visited[v.getIdx()]) {
                    relax(u, v);
                    heap.add(new Node(v.getIdx(), cost[v.getIdx()], grid));
                }
            }
        }
    }

    private void init(Node s) {
        for (int i = 1; i <= grid.getN(); i++) {
            visited[i] = false;
            cost[i] = BigDecimal.valueOf(Double.MAX_VALUE);
            path[i] = null;
        }

        cost[s.getIdx()] = BigDecimal.valueOf(0);
    }

    private void relax(Node u, Node v) {
        if (cost[v.getIdx()].compareTo(cost[u.getIdx()].add(v.getCost())) > 0) {
            cost[v.getIdx()] = cost[u.getIdx()].add(v.getCost());
            path[v.getIdx()] = u;
        }
    }

    /**
     * Palauttaa solmun etäisyyden lähtösolmuun.
     *
     * @param t Solmu.
     * @return Etäisyys.
     */
    public BigDecimal getCost(Node t) {
        return cost[t.getIdx()];
    }

    /**
     * Palauttaa solmun etäisyyden lähtösolmuun pyöristettynä kuten
     * scenario-tiedostoissa.
     *
     * @param t Solmu.
     * @return Etäisyys.
     */
    public String getRoundedCost(Node t) {
        return getCost(t)
                .round(new MathContext(6, RoundingMode.HALF_EVEN))
                .stripTrailingZeros()
                .toString();
    }

    /**
     * Tulostaa verkon, jossa lyhin polku annettuun solmuun on merkitty.
     *
     * @param t Solmu.
     */
    public void printShortestPath(Node t) {
        Grid tempGrid = grid.cloneGrid();
        List<Node> nodesInPath = getShortestPath(t);
//        tempGrid.markVisited(visited);
        tempGrid.markPath(nodesInPath);
        tempGrid.printGrid();
    }

    private List<Node> getShortestPath(Node t) {
        List<Node> nodesInPath = new ArrayList();
        nodesInPath.add(t);
        Node u = path[t.getIdx()];
        while (u != null) {
            nodesInPath.add(u);
            u = path[u.getIdx()];
        }

        return nodesInPath;
    }
}
