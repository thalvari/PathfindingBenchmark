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
     * Kahden solmun etäisyys liikuttaessa viistoon.
     */
    public static final long DIAG_NODE_DIST = 941664;

    private int height;
    private int width;
    private IntList[] adjLists;
    private String[][] mapData;

    /**
     * Konstruktori lukee karttatiedoston ja ottaa talteen verkon esitykset
     * taulukkona ja vieruslistana.
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
        mapData = new String[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapData[y][x] = "" + lines.get(4 + y).charAt(x);
            }
        }
    }

    public void createAdjList() {
        if (mapData == null) {
            return;
        }

        adjLists = new IntList[height * width + 1];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isPassable(x, y)) {
                    int idx = getIdx(x, y);
                    adjLists[idx] = createAdjListForIdx(idx);
                }
            }
        }
    }

    public boolean isPassable(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height
                && (mapData[y][x].equals(".")
                || mapData[y][x].equals("G")
                || mapData[y][x].equals("S"));
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

    public IntList createAdjListForIdx(int idx) {
        IntList adjList = new IntList();
        for (int adjY = getY(idx) - 1; adjY <= getY(idx) + 1; adjY++) {
            for (int adjX = getX(idx) - 1; adjX <= getX(idx) + 1; adjX++) {
                int adjIdx = getIdx(adjX, adjY);
                if (!isSameNode(idx, adjIdx)
                        && isPassable(adjX, adjY)
                        && (isHorVerAdjNode(idx, adjIdx)
                        || isGoodDiagAdjNode(idx, adjIdx))) {

                    adjList.add(adjIdx);
                }
            }
        }

        return adjList;
    }

    private boolean isSameNode(int idx, int adjIdx) {
        return idx == adjIdx;
    }

    private boolean isHorVerAdjNode(int idx, int adjIdx) {
        return getX(idx) == getX(adjIdx) || getY(idx) == getY(adjIdx);
    }

    private boolean isGoodDiagAdjNode(int idx, int adjIdx) {
        int x = getX(idx);
        int y = getY(idx);
        int adjX = getX(adjIdx);
        int adjY = getY(adjIdx);
        return adjIdx != x && adjY != y && isPassable(x, adjY)
                && isPassable(adjX, y);
    }

    /**
     * Palauttaa listan solmun naapureiden indekseistä.
     *
     * @param idx Solmun indeksi.
     * @return Vieruslista.
     */
    public IntList getAdjList(int idx) {
        if (adjLists == null) {
            return null;
        }
        return adjLists[idx];
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
     * Palauttaa solmujen määrän.
     *
     * @return Solmujen määrä.
     */
    public int getN() {
        return height * width;
    }

    /**
     * Palauttaa taulukkoesityksen verkosta, johon lyhin polku ja käsitellyt
     * solmut on merkitty.
     *
     * @param pathIdxs Lyhimmällä polulla olevien solmujen indeksit.
     * @param closedIdxs Käsiteltyjen solmujen indeksit.
     * @return Taulukkoesitys verkosta.
     */
    public String[][] getMarkedGrid(IntList pathIdxs, IntList closedIdxs) {
        String[][] oldMapData = copyMapData();
        markMapData(closedIdxs, "o");
        markMapData(pathIdxs, "X");
        String[][] markedMapData = mapData;
        mapData = oldMapData;
        return markedMapData;
    }

    private String[][] copyMapData() {
        String[][] copy = mapData.clone();
        for (int i = 0; i < mapData.length; i++) {
            copy[i] = mapData[i].clone();
        }

        return copy;
    }

    private void markMapData(IntList idxs, String mark) {
        for (int i = 0; i < idxs.size(); i++) {
            mapData[getY(idxs.get(i))][getX(idxs.get(i))] = mark;
        }
    }
}
