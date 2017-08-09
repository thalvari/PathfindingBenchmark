/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.PriorityQueue;
import pathfindingbenchmark.datastructures.IntList;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 * Reitinhakualgoritmia kuvaava abstrakti luokka.
 *
 * @author thalvari
 */
public abstract class AStarAbstract {

    /**
     * Verkko.
     */
    protected final Grid grid;

    /**
     * Pienin löydetty etäisyys lähtösolmuun.
     */
    protected final double dist[];

    /**
     * Edellisen solmun indeksi löydetyllä lyhimmällä polulla.
     */
    protected final int prev[];

    /**
     * Jo käsitellyt solmut.
     */
    protected final boolean closed[];

    /**
     * Minimikeko.
     */
    protected PriorityQueue<Node> heap;

    /**
     * Lähtösolmun indeksi.
     */
    protected int startIdx;

    /**
     * Maalisolmun indeksi.
     */
    protected int goalIdx;

    /**
     * Konstruktori luo aputietorakenteet.
     *
     * @param grid Verkko.
     */
    public AStarAbstract(Grid grid) {
        this.grid = grid;
        dist = new double[grid.getN() + 1];
        prev = new int[grid.getN() + 1];
        closed = new boolean[grid.getN() + 1];
        heap = new PriorityQueue<>();
    }

    /**
     * Laskee lyhimmän polun pituuden lähtösolmusta maalisolmuun ja ottaa
     * kyseisen polun talteen.
     *
     * @param startIdx Lähtösolmun indeksi.
     * @param goalIdx Maalisolmun indeksi.
     */
    public void run(int startIdx, int goalIdx) {
        init(startIdx, goalIdx);
        heap.add(new Node(startIdx, heuristic(startIdx)));
        while (!heap.isEmpty()) {
            int idx = heap.poll().getIdx();
            if (closed[idx]) {
                continue;
            }

            closed[idx] = true;
            if (idx == goalIdx) {
                break;
            }

            IntList adjList = grid.getAdjList(idx);
            for (int i = 0; i < adjList.size(); i++) {
                int adjIdx = adjList.get(i);
                if (!closed[adjIdx]) {
                    relax(idx, adjIdx);
                }
            }
        }
    }

    /**
     * Alustaa reitinhakualgon kentät ja aputietorakenteet.
     *
     * @param startIdx Lähtösolmun indeksi.
     * @param goalIdx Maalisolmun indeksi.
     */
    protected void init(int startIdx, int goalIdx) {
        heap = new PriorityQueue<>();
        this.startIdx = startIdx;
        this.goalIdx = goalIdx;
        for (int i = 1; i <= grid.getN(); i++) {
            dist[i] = Double.MAX_VALUE;
            prev[i] = 0;
            closed[i] = false;
        }

        dist[startIdx] = 0;
    }

    /**
     * Heuristinen funktio.
     *
     * @param idx Indeksi.
     * @return Heuristinen arvo.
     */
    protected abstract double heuristic(int idx);

    /**
     * Päivittää aputietorakenteiden arvot, jos on löydetty lyhyempi polku.
     *
     * @param idx Solmun indeksi.
     * @param adjIdx Naapurin indeksi.
     */
    protected void relax(int idx, int adjIdx) {
        double newDist = dist[idx] + grid.getAdjNodeDist(idx, adjIdx);
        if (newDist < dist[adjIdx]) {
            dist[adjIdx] = newDist;
            prev[adjIdx] = idx;
            heap.add(new Node(adjIdx, newDist + heuristic(adjIdx)));
        }
    }

    /**
     * Palauttaa solmun etäisyyden lähtösolmuun pyöristettynä haluttuun määrään
     * merkitseviä numeroita.
     *
     * @param n Merkitsevien numeroiden määrä.
     * @return Etäisyys.
     */
    public String getRoundedDist(int n) {
        return BigDecimal.valueOf(getDist())
                .round(new MathContext(n, RoundingMode.HALF_EVEN))
                .stripTrailingZeros()
                .toString();
    }

    private double getDist() {
        return dist[goalIdx];
    }

    /**
     * Tulostaa verkon, jossa lyhin polku lähtösolmusta maalisolmuun on
     * merkitty.
     */
    public void printShortestPath() {
        IntList pathIdxs = getPathIdxs();
        IntList closedIdxs = getClosedIdxs();
        grid.markPrintPathClosed(pathIdxs, closedIdxs);
    }

    private IntList getPathIdxs() {
        IntList idxs = new IntList();
        idxs.add(goalIdx);
        int prevIdx = prev[goalIdx];
        while (prevIdx != 0) {
            idxs.add(prevIdx);
            prevIdx = prev[prevIdx];
        }

        return idxs;
    }

    private IntList getClosedIdxs() {
        IntList idxs = new IntList();
        for (int idx = 1; idx <= grid.getN(); idx++) {
            if (closed[idx]) {
                idxs.add(idx);
            }
        }

        return idxs;
    }
}
