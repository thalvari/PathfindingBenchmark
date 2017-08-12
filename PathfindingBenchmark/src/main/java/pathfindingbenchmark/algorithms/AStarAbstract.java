/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import pathfindingbenchmark.datastructures.IntList;
import pathfindingbenchmark.datastructures.NodeMinHeap;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 * A*-pohjaista reitinhakualgoritmia kuvaava abstrakti luokka.
 *
 * @author thalvari
 */
public abstract class AStarAbstract {

    /**
     * Verkko.
     */
    protected final Grid grid;

    /**
     * Lähtösolmun indeksi.
     */
    protected int startIdx;

    /**
     * Maalisolmun indeksi.
     */
    protected int goalIdx;
    private NodeMinHeap heap;
    private boolean closed[];
    private final long dist[];
    private int prev[];
    private int closedNodeCount;
    private int heapOperCount;

    /**
     * Konstruktori.
     *
     * @param grid Verkko.
     */
    public AStarAbstract(Grid grid) {
        this.grid = grid;
        heap = new NodeMinHeap(grid.getN());
        closed = new boolean[grid.getN() + 1];
        dist = new long[grid.getN() + 1];
        prev = new int[grid.getN() + 1];
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
        heap.insert(new Node(startIdx, 0, heuristic(startIdx)));
        heapOperCount++;
        while (!heap.empty()) {
            int idx = heap.delMin().getIdx();
            heapOperCount++;
            closed[idx] = true;
            closedNodeCount++;
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

    private void init(int startIdx, int goalIdx) {
        this.startIdx = startIdx;
        this.goalIdx = goalIdx;
        heap = new NodeMinHeap(grid.getN());
        closed = new boolean[grid.getN() + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[startIdx] = 0;
        prev = new int[grid.getN() + 1];
        closedNodeCount = 0;
        heapOperCount = 0;
    }

    /**
     * Heuristinen funktio.
     *
     * @param idx Indeksi.
     * @return Heuristinen arvo.
     */
    protected abstract long heuristic(int idx);

    private void relax(int idx, int adjIdx) {
        long newDist = dist[idx] + grid.getAdjNodeDist(idx, adjIdx);
        if (newDist < dist[adjIdx]) {
            dist[adjIdx] = newDist;
            prev[adjIdx] = idx;
            if (heap.hasNode(adjIdx)) {
                heap.decKey(adjIdx, newDist);
            } else {
                heap.insert(new Node(adjIdx, newDist, heuristic(adjIdx)));
            }

            heapOperCount++;
        }
    }

    /**
     * Palauttaa taulukkoesityksen verkosta, johon lyhin polku ja käsitellyt
     * solmut on merkitty.
     *
     * @return Taulukkoesitys kartasta.
     */
    public String[][] getMarkedGrid() {
        IntList pathIdxs = getPathIdxs();
        IntList closedIdxs = getClosedIdxs();
        return grid.getMarkedGrid(pathIdxs, closedIdxs);
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

    /**
     * Palauttaa solmun etäisyyden lähtösolmuun pyöristettynä haluttuun määrään
     * merkitseviä numeroita.
     *
     * @param n Merkitsevien numeroiden määrä.
     * @return Etäisyys.
     */
    public String getRoundedDist(int n) {
        return new BigDecimal(dist[goalIdx])
                .divide(BigDecimal.valueOf(Grid.HOR_VER_DIST),
                        new MathContext(n, RoundingMode.HALF_EVEN))
                .stripTrailingZeros()
                .toString();
    }

    /**
     * Palauttaa käsiteltyjen solmujen määrän.
     *
     * @return Määrä.
     */
    public int getClosedNodeCount() {
        return closedNodeCount;
    }

    /**
     * Palauttaa keko-operaatioiden määrän.
     *
     * @return Määrä.
     */
    public int getHeapOperCount() {
        return heapOperCount;
    }
}
