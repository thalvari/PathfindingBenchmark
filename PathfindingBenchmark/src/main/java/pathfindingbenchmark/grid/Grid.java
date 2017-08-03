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
 *
 * @author thalvari
 */
public class Grid {

    private int height;
    private int width;
    private String[][] mapData;
    private List<Integer>[] adjList;

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
        for (int u = 1; u <= height * width; u++) {
            adjList[u] = new ArrayList();
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!isPassable(y, x)) {
                    continue;
                }
                checkHorVer(y, x);
                checkDiag(y, x);
            }
        }
    }

    private void checkHorVer(int y, int x) {
        check(y, x, y - 1, x);
        check(y, x, y + 1, x);
        check(y, x, y, x - 1);
        check(y, x, y, x + 1);
    }

    private void check(int y0, int x0, int y1, int x1) {
        if (isInBounds(y1, x1) && isPassable(y1, x1)) {
            adjList[getIdx(y0, x0)].add(getIdx(y1, x1));
        }
    }

    private boolean isInBounds(int y, int x) {
        return y >= 0 && y < height && x >= 0 && x < width;
    }

    private boolean isPassable(int y, int x) {
        return mapData[y][x].equals(".");
    }

    private int getIdx(int y, int x) {
        return y * width + x + 1;
    }

    private void checkDiag(int y, int x) {
        if (isInBounds(y - 1, x - 1) && isPassable(y - 1, x - 1)
                && (isPassable(y - 1, x) || isPassable(y, x - 1))) {
            adjList[getIdx(y, x)].add(getIdx(y - 1, x - 1));
        }
        if (isInBounds(y - 1, x + 1) && isPassable(y - 1, x + 1)
                && (isPassable(y - 1, x) || isPassable(y, x + 1))) {
            adjList[getIdx(y, x)].add(getIdx(y - 1, x + 1));
        }
        if (isInBounds(y + 1, x - 1) && isPassable(y + 1, x - 1)
                && (isPassable(y + 1, x) || isPassable(y, x - 1))) {
            adjList[getIdx(y, x)].add(getIdx(y + 1, x - 1));
        }
        if (isInBounds(y + 1, x + 1) && isPassable(y + 1, x + 1)
                && (isPassable(y + 1, x) || isPassable(y, x + 1))) {
            adjList[getIdx(y, x)].add(getIdx(y + 1, x + 1));
        }
    }

    public double getCost(int u, int v) {
        if (!adjList[u].contains(v)) {
            return Double.MAX_VALUE;
        } else if (getY(u) != getY(v) && getX(u) != getX(v)) {
            return Math.sqrt(2);
        } else {
            return 1;
        }
    }

    private int getY(int u) {
        int y = 0;
        while (u > 0) {
            u -= width;
            y++;
        }
        return y;
    }

    private int getX(int u) {
        return u % width;
    }

    public List<Integer> getAdjList(int u) {
        return adjList[u];
    }

    public List<Integer>[] getAdjList() {
        return adjList;
    }

    public String[][] getMapData() {
        return mapData;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
