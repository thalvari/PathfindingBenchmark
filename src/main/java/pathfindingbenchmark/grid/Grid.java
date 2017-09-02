/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import pathfindingbenchmark.datastructures.MyList;
import pathfindingbenchmark.util.Direction;
import pathfindingbenchmark.util.MapReader;

/**
 * Kartan esitys verkkona.
 *
 * @author thalvari
 */
public class Grid {

    /**
     * Kahden solmun etäisyys liikuttaessa viistoon. Tämän ja allaolevan luvun
     * suhde on noin sqrt(2).
     */
    public static final int DIAG_NODE_DIST = 19601;
    /**
     * Kahden solmun etäisyys liikuttaessa pysty- tai vaakasuuntaan.
     */
    public static final int HOR_VER_NODE_DIST = 13860;

    private final int height;
    private final Node[][] nodes;
    private final MapReader reader;
    private final int width;

    /**
     * Konstruktori käyttää kartanlukijaa verkon lukemiseen taulukoksi.
     *
     * @param mapName Kartan nimi.
     */
    public Grid(String mapName) {
        reader = new MapReader(mapName);
        height = reader.getHeight();
        width = reader.getWidth();
        nodes = reader.initNodes();
    }

    /**
     * Palauttaa listan naapurisolmuista.
     *
     * @param node Solmu.
     * @return Lista naapureista.
     */
    public MyList<Node> createAdjList(Node node) {
        MyList<Node> adjList = new MyList<>();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                Direction dir = new Direction(x, y);
                if ((x != 0 || y != 0)
                        && ((dir.isDiag()
                        && isNodeInDirAdj(node, dir))
                        || (!dir.isDiag()
                        && isNodeInDirPassable(node, dir)))) {

                    adjList.add(getNode(node.getX() + x, node.getY() + y));
                }
            }
        }

        return adjList;
    }

    /**
     * Palauttaa kartan korkeuden.
     *
     * @return Korkeus.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Palauttaa tietyissä koordinaateissa sijaitsevan solmun.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @return Solmu.
     */
    public Node getNode(int x, int y) {
        return nodes[y][x];
    }

    /**
     * Palauttaa solmujen oktiilisen etäisyyden.
     *
     * @param node1 Solmu.
     * @param node2 Toinen solmu.
     * @return Etäisyys.
     */
    public int getNodeDist(Node node1, Node node2) {
        int xDif = Math.abs(node1.getX() - node2.getX());
        int yDif = Math.abs(node1.getY() - node2.getY());
        return HOR_VER_NODE_DIST * Math.max(xDif, yDif)
                + (DIAG_NODE_DIST - HOR_VER_NODE_DIST) * Math.min(xDif, yDif);
    }

    /**
     * Palauttaa annetussa suunnassa olevan solmun.
     *
     * @param node Solmu.
     * @param dir Suunta.
     * @return Solmu.
     */
    public Node getNodeInDir(Node node, Direction dir) {
        return getNode(node.getX() + dir.getX(), node.getY() + dir.getY());
    }

    /**
     * Palauttaa kartan solmut sisältävän taulukon.
     *
     * @return Taulukko.
     */
    public Node[][] getNodes() {
        return nodes;
    }

    /**
     * Palauttaa läpikuljettavien solmujen määrän kartalla.
     *
     * @return Määrä.
     */
    public int getPassableNodeCount() {
        return reader.getPassableNodeCount();
    }

    /**
     * Palauttaa kartan leveyden.
     *
     * @return Leveys.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Alustaa kaikki verkon solmut.
     */
    public void initNodes() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                nodes[y][x].init(reader.getOrigSymbol(x, y));
            }
        }
    }

    /**
     * Tarkastaa sijaitsevatko annetut koordinaatit kartalla.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @return Totuusarvo.
     */
    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Tarkastaa voidaanko annetussa suunnassa olevaan solmuun siirtyä.
     *
     * @param node Solmu.
     * @param dir Suunta.
     * @return Totuusarvo.
     */
    public boolean isNodeInDirAdj(Node node, Direction dir) {
        return isNodeInDirPassable(node, dir)
                && (!dir.isDiag()
                || (getNode(node.getX(), node.getY() + dir.getY()).isPassable()
                && getNode(node.getX() + dir.getX(), node.getY())
                        .isPassable()));
    }

    /**
     * Kertoo onko annetussa suunnassa oleva solmu läpikuljettavissa.
     *
     * @param node Solmu.
     * @param dir Suunta.
     * @return Totuusarvo.
     */
    public boolean isNodeInDirPassable(Node node, Direction dir) {
        if (!isInBounds(node.getX() + dir.getX(), node.getY() + dir.getY())) {
            return false;
        }

        return getNodeInDir(node, dir).isPassable();
    }
}
