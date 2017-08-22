/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import pathfindingbenchmark.algorithms.AStar;
import pathfindingbenchmark.algorithms.AStarAbstract;
import pathfindingbenchmark.algorithms.Dijkstra;
import pathfindingbenchmark.algorithms.JPS;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class Wrapper {

    private static long cpuTimeSum;
    private static long usedMemorySum;
    private static final int SAMPLE_SIZE = 25;
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final ThreadMXBean BEAN = ManagementFactory
            .getThreadMXBean();

    private Grid grid;
    private AStarAbstract algo;

    public AStarAbstract getAlgo() {
        return algo;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setAlgo(String algoName) {
        if (algoName.equals("Dijkstra")) {
            algo = new Dijkstra(grid);
        } else if (algoName.equals("AStar")) {
            algo = new AStar(grid);
        } else {
            algo = new JPS(grid);
        }
    }

    public void setGrid(String mapName) {
        grid = new Grid(mapName);
        algo = null;
    }

    public List<String> getStyles(int startX, int startY, int goalX,
            int goalY) {

        char[][] mapData;
        if (algo == null) {
            mapData = grid.getMarkedMap(null);
        } else {
            mapData = algo.getMarkedMap();
        }

        List<String> styles = new ArrayList<>();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                char c = mapData[y][x];
                if (algo != null
                        && ((x == startX && y == startY)
                        || (x == goalX && y == goalY))) {

                    styles.add("endpoint");
                } else if (c == '.' || c == 'G') {
                    styles.add("passable");
                } else if (c == '@' || c == 'O') {
                    styles.add("outofbounds");
                } else if (c == 'T') {
                    styles.add("trees");
                } else if (c == 'S') {
                    styles.add("swamp");
                } else if (c == 'W') {
                    styles.add("water");
                } else if (c == 'o') {
                    styles.add("closed");
                } else {
                    styles.add("path");
                }
            }
        }

        return styles;
    }

    public int getHeight() {
        return grid.getHeight();
    }

    public int getWidth() {
        return grid.getWidth();
    }

    public boolean checkCoordinates(int startX, int startY, int goalX,
            int goalY) {

        return grid != null
                && grid.isInBounds(startX, startY)
                && grid.getNode(startX, startY).isPassable()
                && grid.isInBounds(goalX, goalY)
                && grid.getNode(goalX, goalY).isPassable();
    }

    public void runAlgo(int startX, int startY, int goalX, int goalY) {
        cpuTimeSum = 0;
        usedMemorySum = 0;
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            RUNTIME.gc();
            long startUsedMemory = RUNTIME.totalMemory() - RUNTIME.freeMemory();
            long startCpuTime = BEAN.getCurrentThreadCpuTime();
            algo.run(startX, startY, goalX, goalY);
            cpuTimeSum += BEAN.getCurrentThreadCpuTime() - startCpuTime;
            usedMemorySum += RUNTIME.totalMemory() - RUNTIME.freeMemory()
                    - startUsedMemory;
        }
    }

    public String getDist() {
        return algo.getRoundedDist(6);
    }

    public String getAvgSuccListSize() {
        return divideAndRound(algo.getSuccListTotalSize(),
                algo.getHeapDelMinOperCount() - 1);
    }

    public String getCpuTime() {
        return divideAndRound(cpuTimeSum, SAMPLE_SIZE * 1000000L) + " ms";
    }

    public String getUsedMemory() {
        return divideAndRound(usedMemorySum, SAMPLE_SIZE * 1024L * 1024L)
                + " MB";
    }

    private String divideAndRound(long numer, long denom) {
        if (denom == 0) {
            return "0";
        }

        return new BigDecimal(numer)
                .divide(BigDecimal.valueOf(denom), 3, RoundingMode.HALF_UP)
                .toString();
    }
}
