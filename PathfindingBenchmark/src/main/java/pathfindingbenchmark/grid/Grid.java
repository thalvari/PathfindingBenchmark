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
    protected static final long HOR_VER_DIST = 665857;

    /**
     * Kahden solmun etäisyys liikuttaessa viistoon.
     */
    protected static final long DIAG_DIST = 941664;

    private int height;
    private int width;
    private IntList[] adjList;
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
            createAdjList();
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

    private void createAdjList() {
        adjList = new IntList[height * width + 1];
        for (int i = 1; i <= getN(); i++) {
            adjList[i] = new IntList();
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int idx = getIdx(x, y);
                if (isPassable(idx)) {
                    createAdjListForIdx(idx);
                }
            }
        }
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

    private boolean isPassable(int idx) {
        return mapData[getY(idx)][getX(idx)].equals(".");
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
        int y = 0;
        while (true) {
            idx -= width;
            if (idx > 0) {
                y++;
            } else {
                break;
            }
        }
        return y;
    }

    private void createAdjListForIdx(int idx) {
        for (int adjY = getY(idx) - 1; adjY <= getY(idx) + 1; adjY++) {
            for (int adjX = getX(idx) - 1; adjX <= getX(idx) + 1; adjX++) {
                if (isInBounds(adjX, adjY)) {
                    addAdjIdxToAdjList(idx, getIdx(adjX, adjY));
                }
            }
        }
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private void addAdjIdxToAdjList(int idx, int adjIdx) {
        if (!isSameNode(idx, adjIdx)
                && isPassable(adjIdx)
                && (isHorVerAdjNode(idx, adjIdx)
                || isGoodDiagAdjNode(idx, adjIdx))) {

            adjList[idx].add(adjIdx);
        }
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
        return adjIdx != x && adjY != y && isPassable(getIdx(x, adjY))
                && isPassable(getIdx(adjX, y));
    }

    /**
     * Palauttaa listan solmun naapureiden indekseistä.
     *
     * @param idx Solmun indeksi.
     * @return Vieruslista.
     */
    public IntList getAdjList(int idx) {
        if (adjList == null) {
            return null;
        }
        return adjList[idx];
    }

    /**
     * Palauttaa solmun etäisyyden naapuriin.
     *
     * @param idx Solmun indeksi.
     * @param adjIdx Naapurin indeksi.
     * @return Etäisyys.
     */
    public long getAdjNodeDist(int idx, int adjIdx) {
        if (isHorVerAdjNode(idx, adjIdx)) {
            return HOR_VER_DIST;
        } else {
            return DIAG_DIST;
        }
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
