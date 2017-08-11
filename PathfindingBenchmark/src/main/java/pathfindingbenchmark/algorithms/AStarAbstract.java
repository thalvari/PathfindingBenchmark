/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import pathfindingbenchmark.datastructures.IntList;
import pathfindingbenchmark.datastructures.NodeMinHeap;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 * Reitinhakualgoritmia kuvaava abstrakti luokka.
 *
 * @author thalvari
 */
public abstract class AStarAbstract {

    protected static final long HOR_VER_DIST = 1000000000000L;
    protected static final long DIAG_DIST = 1414213562373L;

    /**
     * Verkko.
     */
    protected final Grid grid;
    private final long dist[];
    private final int prev[];
    private final boolean closed[];
//    private final PriorityQueue<Node> heap;
    private NodeMinHeap heap;
    private int startIdx;

    /**
     * Maalisolmun indeksi.
     */
    protected int goalIdx;
    private int closedCounter;
    private int heapAddCounter;
    private int heapDelCounter;

    /**
     * Konstruktori luo aputietorakenteet.
     *
     * @param grid Verkko.
     */
    public AStarAbstract(Grid grid) {
        this.grid = grid;
        dist = new long[grid.getN() + 1];
        prev = new int[grid.getN() + 1];
        closed = new boolean[grid.getN() + 1];
//        heap = new PriorityQueue<>();
        heap = new NodeMinHeap(grid.getN());
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
        while (!heap.empty()) {
            int idx = heap.delMin().getIdx();
            heapDelCounter++;
            if (closed[idx]) {
                continue;
            }

            closed[idx] = true;
            closedCounter++;
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
        heap.clear();
        this.startIdx = startIdx;
        this.goalIdx = goalIdx;
        for (int i = 1; i <= grid.getN(); i++) {
            dist[i] = Long.MAX_VALUE;
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
    protected abstract long heuristic(int idx);

    private void relax(int idx, int adjIdx) {
        long newDist = dist[idx] + grid.getAdjNodeDist(idx, adjIdx);
        if (newDist < dist[adjIdx]) {
            dist[adjIdx] = newDist;
            prev[adjIdx] = idx;
            heap.insert(new Node(adjIdx, newDist, heuristic(adjIdx)));
            heapAddCounter++;
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

    public String getRoundedDist(int n) {
        BigDecimal d = new BigDecimal(dist[goalIdx]);
        d = d.divide(BigDecimal.valueOf(HOR_VER_DIST));
        return d.round(new MathContext(n, RoundingMode.HALF_EVEN))
                .stripTrailingZeros()
                .toString();
    }

    public int getClosedCounter() {
        return closedCounter;
    }

    public int getHeapAddCounter() {
        return heapAddCounter;
    }

    public int getHeapDelCounter() {
        return heapDelCounter;
    }
}
