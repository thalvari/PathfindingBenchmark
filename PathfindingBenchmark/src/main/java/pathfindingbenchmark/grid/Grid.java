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

/**
 * Kartan esitys verkkona.
 *
 * @author thalvari
 */
public class Grid {

    private int height;
    private int width;
    private List<Node>[] adjList;
    private String[][] mapData;

    /**
     * Konstruktori lukee karttatiedoston ja ottaa talteen verkon esitykset
     * taulukkona ja vieruslistana.
     *
     * @param game Pelin nimen lyhenne.
     * @param map Kartan nimi.
     */
    public Grid(String game, String map) {
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
        for (int i = 1; i <= getN(); i++) {
            adjList[i] = new ArrayList();
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isPassable(x, y)) {
                    createAdjListNode(x, y);
                }
            }
        }
    }

    private boolean isPassable(int x, int y) {
        return mapData[y][x].equals(".");
    }

    private void createAdjListNode(int x, int y) {
        for (int y1 = y - 1; y1 <= y + 1; y1++) {
            for (int x1 = x - 1; x1 <= x + 1; x1++) {
                if (!isSameNode(x, y, x1, y1) && isGoodNode(x1, y1)) {
                    int idx = getIdx(x, y);
                    if (isHorVerAdjNode(x, y, x1, y1)) {
                        adjList[idx].add(new Node(x1, y1, 1, this));
                    } else if (isGoodDiagAdjNode(x, y, x1, y1)) {
                        adjList[idx].add(new Node(x1, y1, Math.sqrt(2), this));
                    }
                }
            }
        }
    }

    private boolean isSameNode(int x, int y, int x1, int y1) {
        return x1 == x && y1 == y;
    }

    private boolean isGoodNode(int x, int y) {
        return isInBounds(x, y) && isPassable(x, y);
    }

    private boolean isHorVerAdjNode(int x, int y, int x1, int y1) {
        return x1 == x || y1 == y;
    }

    private boolean isGoodDiagAdjNode(int x, int y, int x1, int y1) {
        return x1 != x && y1 != y && isPassable(x, y1) && isPassable(x1, y);
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
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
     * Palauttaa koordinaatteja vastaavan solmun indeksin kartalla.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @return Solmun indeksi.
     */
    public Integer getIdx(int x, int y) {
        return y * width + x + 1;
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
     * Palauttaa verkon taulukkoesityksen.
     *
     * @return Verkon taulukkoesitys.
     */
    public String[][] getMapData() {
        return mapData;
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
}
