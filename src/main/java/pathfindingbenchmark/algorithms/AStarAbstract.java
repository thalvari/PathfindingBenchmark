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
     * Maalisolmun indeksi.
     */
    protected Node goal;

    /**
     * Käytettävä verkko.
     */
    protected final Grid grid;

    /**
     * Lähtösolmun indeksi.
     */
    protected Node start;
    private int closedNodeCount;
    private NodeMinHeap heap;
    private int heapOperCount;
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
     * Palauttaa suljettuun joukoon kuuluvien solmujen määrän.
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

    /**
     * Palauttaa keon maksimikoon.
     *
     * @return Koko.
     */
    public int getMaxHeapSize() {
        return heap.getMaxHeapSize();
    }

    /**
     * Palauttaa polun pituuden.
     *
     * @return Pituus.
     */
    public int getPathLen() {
        return goal.getDist();
    }

    /**
     * Palauttaa kaikkien tutkittujen seuraajalistojen kokojen summan.
     *
     * @return Summa.
     */
    public int getSuccListTotalSize() {
        return succListTotalSize;
    }

    /**
     * Kertoo onko lyhin polku löytynyt.
     *
     * @return Totuusarvo.
     */
    public boolean isSolved() {
        return start.equals(goal) || goal.getPrev() != null;
    }

    /**
     * Merkitsee lyhimmän polun verkkoon.
     */
    public void markPath() {
        if (!isSolved()) {
            return;
        }

        Node prev = goal;
        while (prev != null) {
            prev.setPath();
            prev = prev.getPrev();
        }
    }

    /**
     * Laskee lyhimmän polun pituuden lähtösolmusta maalisolmuun ja ottaa
     * kyseisen polun talteen.
     *
     * @param startX Lähtösolmun x-koordinaatti.
     * @param startY Lähtösolmun y-koordinaatti.
     * @param goalX Maalisolmun x-koordinaatti.
     * @param goalY Maalisolmun y-koordinaatti.
     */
    public void run(int startX, int startY, int goalX, int goalY) {
        init(startX, startY, goalX, goalY);
        start.setDist(0);
        heap.insert(start);
        heapOperCount++;
        while (!heap.empty()) {
            Node node = heap.delMin();
            heapOperCount++;
            node.setClosed();
            closedNodeCount++;
            if (node.equals(goal)) {
                break;
            }

            MyList<Node> succList = getSuccList(node);
            succListTotalSize += succList.size();
            for (int i = 0; i < succList.size(); i++) {
                Node succ = succList.get(i);
                if (!succ.isClosed()) {
                    relax(node, succ);
                }
            }
        }
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

    /**
     * Palauttaa solmun heuristisena arvona käytettävän oktiilisen etäisyyden
     * maalisolmuun tai Dijkstran tapauksessa luvun 0.
     *
     * @param node Solmu.
     * @return Heuristinen arvo.
     */
    protected int heuristic(Node node) {
        return grid.getNodeDist(node, goal);
    }

    private void init(int startX, int startY, int goalX, int goalY) {
        grid.initNodes();
        start = grid.getNode(startX, startY);
        goal = grid.getNode(goalX, goalY);
        heap = new NodeMinHeap();
        closedNodeCount = 0;
        heapOperCount = 0;
        succListTotalSize = 0;
    }

    private void relax(Node node, Node succ) {
        int newDist = node.getDist() + grid.getNodeDist(node, succ);
        if (newDist < succ.getDist()) {
            succ.setDist(newDist);
            succ.setPrev(node);
            heapOperCount++;
            if (succ.getHeapIdx() != 0) {
                heap.decKey(succ, newDist);
            } else {
                succ.setHeuristic(heuristic(succ));
                heap.insert(succ);
            }
        }
    }
}
