/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import pathfindingbenchmark.datastructures.IntList;

/**
 * Kartan esitys verkkona.
 *
 * @author thalvari
 */
public class Grid {

    /**
     * Kahden solmun etäisyys liikuttaessa pysty- tai vaakatasossa.
     */
    public static final long HOR_VER_NODE_DIST = 665857;

    /**
     * Kahden solmun etäisyys liikuttaessa viistoon. Tämän ja ylläolevan luvun
     * suhde on noin sqrt(2).
     */
    public static final long DIAG_NODE_DIST = 941664;

    private int height;
    private int width;
    private String[][] map;
    private int passableNodeCount;

    /**
     * Konstruktori lukee karttatiedoston ja ottaa talteen verkon esityksen
     * taulukkona.
     *
     * @param mapName Kartan nimi.
     */
    public Grid(String mapName) {
        List<String> lines = readFile(mapName);
        if (lines != null) {
            parseMapData(lines);
        }
    }

    private List<String> readFile(String mapName) {
        List<String> lines = new ArrayList<>();
        try {
            Files.lines(Paths.get("maps/" + mapName + ".map"))
                    .forEach(lines::add);
        } catch (Exception e) {
            return null;
        }

        return lines;
    }

    private void parseMapData(List<String> lines) {
        height = Integer.parseInt(lines.get(1).substring("height ".length()));
        width = Integer.parseInt(lines.get(2).substring("width ".length()));
        map = new String[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = "" + lines.get(4 + y).charAt(x);
                if (isPassableTerrain(x, y)) {
                    passableNodeCount++;
                }
            }
        }
    }

    private boolean isPassableTerrain(int x, int y) {
        return map[y][x].equals(".") || map[y][x].equals("G")
                || map[y][x].equals("S");
    }

    /**
     * Palauttaa listan solmun naapureiden indekseistä.
     *
     * @param idx Solmun indeksi.
     * @return Indeksilista.
     */
    public IntList createAdjList(int idx) {
        int x = getX(idx);
        int y = getY(idx);
        if (!isPassable(x, y)) {
            return null;
        }

        IntList adjList = new IntList();
        for (int adjY = y - 1; adjY <= y + 1; adjY++) {
            for (int adjX = x - 1; adjX <= x + 1; adjX++) {
                if (!isSameNode(x, y, adjX, adjY)
                        && isPassable(adjX, adjY)
                        && (isHorVerAdjNode(x, y, adjX, adjY)
                        || isGoodDiagAdjNode(x, y, adjX, adjY))) {

                    adjList.add(getIdx(adjX, adjY));
                }
            }
        }

        return adjList;
    }

    /**
     * Palauttaa indeksia vastaavan solmun x-koordinaatin.
     *
     * @param idx Indeksi.
     * @return X-koordinaatti.
     */
    public int getX(int idx) {
        return (idx - 1) % width;
    }

    /**
     * Palauttaa indeksia vastaavan solmun y-koordinaatin.
     *
     * @param idx Indeksi.
     * @return Y-koordinaatti.
     */
    public int getY(int idx) {
        return (idx - 1) / width;
    }

    /**
     * Kertoo sijaitsevatko annetut koordinaatit kartalla ja onko niitä vastaava
     * solmu läpikuljettava.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @return Totuusarvo.
     */
    public boolean isPassable(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height
                && isPassableTerrain(x, y);
    }

    private boolean isSameNode(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y1 == y2;
    }

    private boolean isHorVerAdjNode(int x, int y, int adjX, int adjY) {
        return x == adjX || y == adjY;
    }

    private boolean isGoodDiagAdjNode(int x, int y, int adjX, int adjY) {
        return x != adjX && y != adjY && isPassable(x, adjY)
                && isPassable(adjX, y);
    }

    /**
     * Palauttaa koordinaatteja vastaavan solmun indeksin.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @return Solmun indeksi.
     */
    public Integer getIdx(int x, int y) {
        return y * width + x + 1;
    }

    /**
     * Palauttaa solmujen oktiilisen etäisyyden.
     *
     * @param idx1 Ensimmäisen solmun indeksi.
     * @param idx2 Toisen solmun indeksi.
     * @return Etäisyys.
     */
    public long getNodeDist(int idx1, int idx2) {
        int xDif = Math.abs(getX(idx1) - getX(idx2));
        int yDif = Math.abs(getY(idx1) - getY(idx2));
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
    public String[][] getMarkedMap(boolean[] closedIdxs, IntList pathIdxs) {
        String[][] markedMap = new String[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int idx = getIdx(x, y);
                if (closedIdxs[idx]) {
                    markedMap[y][x] = "o";
                } else {
                    markedMap[y][x] = map[y][x];
                }
            }
        }

        for (int i = 0; i < pathIdxs.size(); i++) {
            markedMap[getY(pathIdxs.get(i))][getX(pathIdxs.get(i))] = "X";
        }

        return markedMap;
    }
}
