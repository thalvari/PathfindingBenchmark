/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 * Reitinhakualgoritmia kuvaava abstrakti luokka.
 *
 * @author thalvari
 */
public abstract class Algo {

    /**
     * Verkko, jossa algoritmi ajetaan.
     */
    protected final Grid grid;

    /**
     * Päivittyvä lista etäisyyksistä lähtösolmuun.
     */
    protected final double cost[];

    /**
     * Päivittyvä lista edellisistä solmuista polulla.
     */
    protected final Node path[];

    /**
     * Päivittyvä lista vierailluista solmuista.
     */
    protected final boolean visited[];

    /**
     * Lähtösolmu.
     */
    protected Node s;

    /**
     * Maalisolmu.
     */
    protected Node t;

    /**
     * Konstruktori luo aputietorakenteet.
     *
     * @param grid Verkko.
     */
    public Algo(Grid grid) {
        this.grid = grid;
        cost = new double[grid.getN() + 1];
        path = new Node[grid.getN() + 1];
        visited = new boolean[grid.getN() + 1];
    }

    /**
     * Alustaa reitinhakualgon kentät ja aputietorakenteet.
     *
     * @param s Lähtösolmu.
     * @param t Maalisolmu.
     */
    protected void init(Node s, Node t) {
        this.s = s;
        this.t = t;
        for (int i = 1; i <= grid.getN(); i++) {
            cost[i] = Double.MAX_VALUE;
            path[i] = null;
            visited[i] = false;
        }

        cost[s.getIdx()] = 0;
    }

    /**
     * Päivittää aputietorakenteiden arvot.
     *
     * @param u Solmu.
     * @param v Naapurisolmu.
     */
    protected void relax(Node u, Node v) {
        if (cost[v.getIdx()] > cost[u.getIdx()] + v.getCost()) {
            cost[v.getIdx()] = cost[u.getIdx()] + v.getCost();
            path[v.getIdx()] = u;
        }
    }

    /**
     * Laskee lyhimmän polun pituuden lähtösolmusta maalisolmuun ja ottaa
     * kyseisen polun talteen.
     *
     * @param s Lähtösolmu.
     * @param t Maalisolmu.
     */
    public abstract void run(Node s, Node t);

    /**
     * Palauttaa solmun etäisyyden lähtösolmuun pyöristettynä kuten
     * scenario-tiedostoissa.
     *
     * @return Etäisyys.
     */
    public String getRoundedCost() {
        return BigDecimal.valueOf(getCost())
                .round(new MathContext(6, RoundingMode.HALF_EVEN))
                .stripTrailingZeros()
                .toString();
    }

    private double getCost() {
        return cost[t.getIdx()];
    }

    /**
     * Tulostaa verkon, jossa lyhin polku lähtösolmusta maalisolmuun on
     * merkitty.
     */
    public void printShortestPath() {
        String[][] mapData = cloneMapData();
        List<Node> nodesInPath = getShortestPath();
        markVisited(mapData);
        markPath(mapData, nodesInPath);
        printMapData(mapData);
    }

    private String[][] cloneMapData() {
        String[][] mapData = grid.getMapData().clone();
        for (int i = 0; i < mapData.length; i++) {
            mapData[i] = grid.getMapData()[i].clone();
        }

        return mapData;
    }

    private List<Node> getShortestPath() {
        List<Node> nodesInPath = new ArrayList<>();
        nodesInPath.add(t);
        Node u = path[t.getIdx()];
        while (u != null) {
            nodesInPath.add(u);
            u = path[u.getIdx()];
        }

        return nodesInPath;
    }

    private void markPath(String[][] mapData, List<Node> nodesInPath) {
        for (Node u : nodesInPath) {
            mapData[u.getY()][u.getX()] = "X";
        }
    }

    private void printMapData(String[][] mapData) {
        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[0].length; x++) {
                System.out.print(mapData[y][x]);
            }
            System.out.println();
        }
    }

    private void markVisited(String[][] mapData) {
        for (int i = 1; i <= grid.getN(); i++) {
            if (visited[i]) {
                mapData[grid.getY(i)][grid.getX(i)] = "o";
            }
        }
    }
}
