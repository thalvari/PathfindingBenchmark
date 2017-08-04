/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Kartan esitys verkkona.
 *
 * @author thalvari
 */
public class Grid {

    private String game;
    private String map;
    private int height;
    private int width;
    private String[][] mapData;
    private List<Node>[] adjList;

    /**
     * Konstruktori lukee karttatiedoston ja ottaa talteen esitykset taulukkona
     * ja vieruslistana.
     *
     * @param game Pelin nimen lyhenne.
     * @param map Kartan nimi.
     */
    public Grid(String game, String map) {
        this.game = game;
        this.map = map;
        List<String> lines = readFile(game, map);
        if (!lines.isEmpty()) {
            parseMapData(lines);
            createAdjList();
        }
    }

    private List<String> readFile(String game, String map) {
        List<String> lines = new ArrayList();
        try {
            Files.lines(Paths.get("maps/" + game + "/" + map + ".map"))
                    .forEach(line -> lines.add(line));
        } catch (Exception e) {
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
        adjList = new ArrayList[height * width + 1];
        for (int u = 1; u <= height * width; u++) {
            adjList[u] = new ArrayList();
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!isPassable(x, y)) {
                    continue;
                }
                checkNeighbours(x, y);
            }
        }
    }

    private void checkNeighbours(int x, int y) {
        check(x, y, -1, 0);
        check(x, y, 1, 0);
        check(x, y, 0, -1);
        check(x, y, 0, 1);
        check(x, y, -1, -1);
        check(x, y, -1, 1);
        check(x, y, 1, -1);
        check(x, y, 1, 1);
    }

    private void check(int x, int y, int xDiff, int yDiff) {
        int x1 = x + xDiff;
        int y1 = y + yDiff;
        if (!isInBounds(x1, y1) || !isPassable(x1, y1)) {
            return;
        }

        int idx = getIdx(x, y);
        int idx1 = getIdx(x1, y1);
        if (xDiff == 0 || yDiff == 0) {
            adjList[idx].add(new Node(x1, y1, idx1, BigDecimal.valueOf(1)));
        } else if (isPassable(x, y1) && isPassable(x1, y)) {
            adjList[idx].add(
                    new Node(x1, y1, idx1, BigDecimal.valueOf(Math.sqrt(2))));
        }
    }

    /**
     * Palauttaa koordinaatteja vastaavan solmun indeksin kartalla.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @return Solmun indeksi.
     */
    public Integer getIdx(int x, int y) {
        return y * width + x + 1;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private boolean isPassable(int x, int y) {
        return mapData[y][x].equals(".");
    }

    /**
     * Palauttaa solmun vieruslistan kartalla.
     *
     * @param u Solmu.
     * @return Vieruslista.
     */
    public List<Node> getAdjList(Node u) {
        if (adjList == null) {
            return null;
        }
        return adjList[u.getIdx()];
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

    /**
     * Merkitsee polulla olevat solmut taulukkoesitykseen.
     *
     * @param nodesInPath Polun solmut.
     */
    public void markPath(List<Node> nodesInPath) {
        for (Node node : nodesInPath) {
            mapData[node.getY()][node.getX()] = "X";
        }
    }

    /**
     * Tulostaa taulukkoesityksen.
     */
    public void printGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(mapData[y][x]);
            }
            System.out.println();
        }
    }

    /**
     * Kloonaa verkon.
     *
     * @return Verkko.
     */
    public Grid cloneGrid() {
        return new Grid(game, map);
    }

    /**
     * Palauttaa solmujen määrän.
     *
     * @return Solmujen määrä.
     */
    public int getN() {
        return height * width;
    }

//    public void markVisited(boolean[] visited) {
//        for (int i = 1; i <= getN(); i++) {
//            if (visited[i]) {
//                mapData[getY(i)][getX(i)] = "o";
//            }
//        }
//    }
}
