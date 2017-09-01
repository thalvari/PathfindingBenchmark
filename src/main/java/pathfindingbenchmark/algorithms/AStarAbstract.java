/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.datastructures.MyList;
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
     * Käytettävä verkko.
     */
    protected final Grid grid;

    /**
     * Lähtösolmun indeksi.
     */
    protected Node start;

    /**
     * Maalisolmun indeksi.
     */
    protected Node goal;
    private NodeMinHeap heap;
    private int heapDecKeyOperCount;
    private int heapDelMinOperCount;
    private int heapInsertOperCount;
    private int succListTotalSize;

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
        start.setDist(0);
        heap.insert(start);
        heapInsertOperCount++;
        while (!heap.empty()) {
            Node node = heap.delMin();
            heapDelMinOperCount++;
            node.setSymbol('c');
            if (node.equals(goal)) {
                break;
            }

            MyList<Node> succList = getSuccList(node);
            succListTotalSize += succList.size();
            for (int i = 0; i < succList.size(); i++) {
                Node succ = succList.get(i);
                if (succ.getSymbol() != 'c') {
                    relax(node, succ);
                }
            }
        }
    }

    private void init(int startX, int startY, int goalX, int goalY) {
        grid.initNodes();
        start = grid.getNode(startX, startY);
        goal = grid.getNode(goalX, goalY);
        heap = new NodeMinHeap();
        heapDecKeyOperCount = 0;
        heapDelMinOperCount = 0;
        heapInsertOperCount = 0;
        succListTotalSize = 0;
    }

    /**
     * Palauttaa solmun heuristisena arvona käytettävän oktiilisen etäisyyden
     * maalisolmuun tai Dijkstran tapauksessa luvun 0.
     *
     * @param node Solmu.
     * @return Heuristinen arvo.
     */
    protected int heuristic(Node node) {
        int xDif = Math.abs(node.getX() - goal.getX());
        int yDif = Math.abs(node.getY() - goal.getY());
        return Grid.HOR_VER_NODE_DIST * Math.max(xDif, yDif)
                + (Grid.DIAG_NODE_DIST - Grid.HOR_VER_NODE_DIST)
                * Math.min(xDif, yDif);
    }

    /**
     * Palauttaa listan naapurisolmuista tai JPS:n tapauksessa listan
     * hyppysolmuista.
     *
     * @param node Solmu.
     * @return Lista seuraajasolmuista.
     */
    protected MyList<Node> getSuccList(Node node) {
        return grid.createAdjList(node);
    }

    private void relax(Node node, Node succ) {
        int newDist = node.getDist() + grid.getNodeDist(node, succ);
        if (newDist < succ.getDist()) {
            succ.setDist(newDist);
            succ.setPrev(node);
            if (succ.getHeapIdx() != 0) {
                heap.decKey(succ, newDist);
                heapDecKeyOperCount++;
            } else {
                succ.setHeuristic(heuristic(succ));
                heap.insert(succ);
                heapInsertOperCount++;
            }
        }
    }

    /**
     * Palauttaa taulukkoesityksen kartasta, johon lyhin polku ja käsitellyt
     * solmut on merkitty.
     */
    public void markPath() {
        Node prev = goal;
        while (prev != null) {
            prev.setSymbol('P');
            prev = prev.getPrev();
        }
    }

    /**
     * Palauttaa solmun etäisyyden lähtösolmuun.
     *
     * @return Etäisyys.
     */
    public int getMinDist() {
        return goal.getDist();
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
     * Palauttaa kaikkien tutkittujen seuraajalistojen kokojen summan.
     *
     * @return Summa.
     */
    public int getSuccListTotalSize() {
        return succListTotalSize;
    }

    public int getMaxHeapSize() {
        return heap.getMaxHeapSize();
    }
}
