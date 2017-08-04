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
    private int n;
    private String[][] mapData;
    private List<Node>[] adjList;

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
        n = height * width;
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

        if (xDiff == 0 || yDiff == 0) {
            adjList[getIdx(x, y)].add(new Node(x1, y1, getIdx(x1, y1), 1));
        } else if (isPassable(x, y1) || isPassable(x1, y)) {
            adjList[getIdx(x, y)].add(new Node(x1, y1, getIdx(x1, y1),
                    Math.sqrt(2)));
        }
    }

    public Integer getIdx(int x, int y) {
        return y * width + x + 1;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private boolean isPassable(int x, int y) {
        return mapData[y][x].equals(".");
    }

    public List<Node> getAdjList(Node u) {
        return adjList[u.getIdx()];
    }

    public String[][] cloneMapData() {
        return mapData.clone();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getN() {
        return n;
    }

    public int getX(int idx) {
        return (idx - 1) % width;
    }

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