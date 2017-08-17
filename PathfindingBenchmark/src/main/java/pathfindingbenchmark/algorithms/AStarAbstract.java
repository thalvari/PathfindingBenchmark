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
import pathfindingbenchmark.util.Node;

/**
 * A*-pohjaista reitinhakualgoritmia kuvaava abstrakti luokka.
 *
 * @author thalvari
 */
public abstract class AStarAbstract {

    /**
     * Käytettävä verkko.
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
    private long dist[];

    /**
     * Edellisen solmun indeksi polulla lähtösolmuun.
     */
    protected int prev[];
    private int heapDecKeyOperCount;
    private int heapDelMinOperCount;
    private int heapInsertOperCount;

    /**
     * Konstruktori.
     *
     * @param grid Verkko.
     */
    public AStarAbstract(Grid grid) {
        this.grid = grid;
    }

    /**
     * Laskee lyhimmän polun pituuden lähtösolmusta maalisolmuun ja ottaa
     * kyseisen polun talteen.
     *
     * @param startX Lähtösolmun x-koordinaatti.
     * @param startY Lähtösolmun y-koordinaatti.
     * @param goalY Maalisolmun x-koordinaatti.
     * @param goalX Maalisolmun y-koordinaatti.
     */
    public void run(int startX, int startY, int goalX, int goalY) {
        init(startX, startY, goalX, goalY);
        heap.insert(new Node(startIdx, 0, heuristic(startIdx)));
        heapInsertOperCount++;
        while (!heap.empty()) {
            int idx = heap.delMin().getIdx();
            heapDelMinOperCount++;
            closed[idx] = true;
            if (idx == goalIdx) {
                break;
            }

            IntList succList = getSuccList(idx);
            for (int i = 0; i < succList.size(); i++) {
                int succIdx = succList.get(i);
                if (!closed[succIdx]) {
                    relax(idx, succIdx);
                }
            }
        }
    }

    private void init(int startX, int startY, int goalX, int goalY) {
        startIdx = grid.getIdx(startX, startY);
        goalIdx = grid.getIdx(goalX, goalY);
        heap = new NodeMinHeap(grid.getSize());
        closed = new boolean[grid.getSize() + 1];
        dist = new long[grid.getSize() + 1];
        prev = new int[grid.getSize() + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[startIdx] = 0;
        heapDecKeyOperCount = 0;
        heapDelMinOperCount = 0;
        heapInsertOperCount = 0;
    }

    /**
     * Palauttaa solmun heuristisena arvona käytettävän oktiilisen etäisyyden
     * maalisolmuun tai Dijkstran tapauksessa luvun 0.
     *
     * @param idx Solmun indeksi.
     * @return Heuristinen arvo.
     */
    protected long heuristic(int idx) {
        int xDif = Math.abs(grid.getX(idx) - grid.getX(goalIdx));
        int yDif = Math.abs(grid.getY(idx) - grid.getY(goalIdx));
        return Grid.HOR_VER_NODE_DIST * Math.max(xDif, yDif)
                + (Grid.DIAG_NODE_DIST - Grid.HOR_VER_NODE_DIST)
                * Math.min(xDif, yDif);
    }

    /**
     * Palauttaa listan solmun naapurisolmujen indekseistä tai JPS:n tapauksessa
     * listan hyppysolmujen indekseistä.
     *
     * @param idx Solmun indeksi.
     * @return Lista seuraajasolmujen indekseistä.
     */
    protected IntList getSuccList(int idx) {
        return grid.createAdjList(idx);
    }

    private void relax(int idx, int succIdx) {
        long newDist = dist[idx] + grid.getNodeDist(idx, succIdx);
        if (newDist < dist[succIdx]) {
            dist[succIdx] = newDist;
            prev[succIdx] = idx;
            if (heap.hasNode(succIdx)) {
                heap.decKey(succIdx, newDist);
                heapDecKeyOperCount++;
            } else {
                heap.insert(new Node(succIdx, newDist, heuristic(succIdx)));
                heapInsertOperCount++;
            }
        }
    }

    /**
     * Palauttaa taulukkoesityksen verkosta, johon lyhin polku ja käsitellyt
     * solmut on merkitty.
     *
     * @return Taulukkoesitys verkosta.
     */
    public String[][] getMarkedMap() {
        return grid.getMarkedMap(closed, getPathIdxs());
    }

    private IntList getPathIdxs() {
        IntList idxs = new IntList();
        int prevIdx = goalIdx;
        while (prevIdx != 0) {
            idxs.add(prevIdx);
            prevIdx = prev[prevIdx];
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
                .divide(BigDecimal.valueOf(Grid.HOR_VER_NODE_DIST),
                        new MathContext(n, RoundingMode.HALF_EVEN))
                .stripTrailingZeros()
                .toString();
    }

    /**
     * Palauttaa keon dec-key-operaatioiden määrän.
     *
     * @return Määrä.
     */
    public int getHeapDecKeyOperCount() {
        return heapDecKeyOperCount;
    }

    /**
     * Palauttaa keon del-min-operaatioiden määrän.
     *
     * @return Määrä.
     */
    public int getHeapDelMinOperCount() {
        return heapDelMinOperCount;
    }

    /**
     * Palauttaa keon insert-operaatioiden määrän.
     *
     * @return Määrä.
     */
    public int getHeapInsertOperCount() {
        return heapInsertOperCount;
    }

    /**
     * Palauttaa algoritmin käyttämän verkko-olion.
     *
     * @return Verkko-olio.
     */
    public Grid getGrid() {
        return grid;
    }
}
