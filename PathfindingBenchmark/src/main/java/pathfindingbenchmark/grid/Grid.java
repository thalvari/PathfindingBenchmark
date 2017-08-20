/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import java.util.List;
import pathfindingbenchmark.datastructures.NodeList;
import pathfindingbenchmark.util.Direction;
import pathfindingbenchmark.util.Node;

/**
 * Kartan esitys verkkona.
 *
 * @author thalvari
 */
public class Grid {

    /**
     * Kahden solmun etäisyys liikuttaessa pysty- tai vaakatasossa.
     */
    public static final int HOR_VER_NODE_DIST = 13860;

    /**
     * Kahden solmun etäisyys liikuttaessa viistoon. Tämän ja ylläolevan luvun
     * suhde on noin sqrt(2).
     */
    public static final int DIAG_NODE_DIST = 19601;

    private int height;
    private int width;
    private Node[][] nodes;
    private int passableNodeCount;

    /**
     * Konstruktori lukee karttatiedoston ja ottaa talteen verkon esityksen
     * taulukkona.
     *
     * @param mapName Kartan nimi.
     */
    public Grid(String mapName) {
        MapReader reader = new MapReader();
        reader.readFile(mapName);
        if (!reader.isError()) {
            parseMapData(reader.getMapData());
        }
    }

    private void parseMapData(List<String> mapData) {
        height = Integer.parseInt(mapData.get(1).split(" ")[1]);
        width = Integer.parseInt(mapData.get(2).split(" ")[1]);
        nodes = new Node[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char symbol = mapData.get(4 + y).charAt(x);
                nodes[y][x] = new Node(x, y, symbol);
                if (nodes[y][x].isPassable()) {
                    passableNodeCount++;
                }
            }
        }
    }

    /**
     * Palauttaa listan solmun naapureiden indekseistä.
     *
     * @param idx Solmun indeksi.
     * @return Indeksilista.
     */
    public NodeList createAdjList(Node node) {
        NodeList adjList = new NodeList();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                Direction dir = new Direction(x, y);
                if ((x != 0 || y != 0)
                        && ((dir.isDiag()
                        && isNodeInDirAdj(node, dir))
                        || (!dir.isDiag()
                        && isNodeInDirPassable(node, dir)))) {

                    adjList.add(nodes[node.getY() + y][node.getX() + x]);
                }
            }
        }

        return adjList;
    }

    public boolean isNodeInDirAdj(Node node, Direction dir) {
        if (!isInBounds(node.getX() + dir.getX(), node.getY() + dir.getY())) {
            return false;
        }

        return getAdjNodeInDir(node, dir).isPassable()
                && (!dir.isDiag()
                || (nodes[node.getY() + dir.getY()][node.getX()].isPassable()
                && nodes[node.getY()][node.getX() + dir.getX()].isPassable()));
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Node getAdjNodeInDir(Node node, Direction dir) {
        return nodes[node.getY() + dir.getY()][node.getX() + dir.getX()];
    }

    public boolean isNodeInDirPassable(Node node, Direction dir) {
        if (!isInBounds(node.getX() + dir.getX(), node.getY() + dir.getY())) {
            return false;
        }

        return getAdjNodeInDir(node, dir).isPassable();
    }

    /**
     * Palauttaa solmujen oktiilisen etäisyyden.
     *
     * @param idx1 Ensimmäisen solmun indeksi.
     * @param idx2 Toisen solmun indeksi.
     * @return Etäisyys.
     */
    public int getNodeDist(Node node1, Node adj) {
        int xDif = Math.abs(node1.getX() - adj.getX());
        int yDif = Math.abs(node1.getY() - adj.getY());
        return HOR_VER_NODE_DIST * Math.max(xDif, yDif)
                + (DIAG_NODE_DIST - HOR_VER_NODE_DIST) * Math.min(xDif, yDif);
    }

    /**
     * Palauttaa taulukkoesityksen koon.
     *
     * @return Koko.
     */
    public int getSize() {
        return height * width;
    }

    /**
     * Palauttaa läpikuljettavien solmujen määrän kartalla.
     *
     * @return Määrä.
     */
    public int getPassableNodeCount() {
        return passableNodeCount;
    }

    /**
     * Palauttaa taulukkoesityksen, johon lyhin polku ja käsitellyt solmut on
     * merkitty.
     *
     * @param closedIdxs Lista, johon on merkitty käsitellyt solmut.
     * @param pathIdxs Indeksilista polulla olevista solmuista.
     * @return Taulukkoesitys verkosta.
     */
    public char[][] getMarkedMap(Node goal) {
        char[][] markedMap = new char[height][width];
        markPath(markedMap, goal);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (markedMap[y][x] != 'X' && nodes[y][x].isClosed()) {
                    markedMap[y][x] = 'o';
                } else if (markedMap[y][x] != 'X') {
                    markedMap[y][x] = nodes[y][x].getSymbol();
                }
            }
        }

        return markedMap;
    }

    private void markPath(char[][] markedMap, Node goal) {
        Node prev = goal;
        while (prev != null) {
            markedMap[prev.getY()][prev.getX()] = 'X';
            prev = prev.getPrev();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Node getNode(int x, int y) {
        return nodes[y][x];
    }

    public void initNodes() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                nodes[y][x].reset();
            }
        }
    }
}
