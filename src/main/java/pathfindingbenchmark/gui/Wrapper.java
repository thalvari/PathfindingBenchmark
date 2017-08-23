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
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
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

    private static final int IMAGE_SCALE_FACTOR = 6;
    private static final int SAMPLE_SIZE = 25;
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final ThreadMXBean BEAN = ManagementFactory
            .getThreadMXBean();

    private static long cpuTimeSum;
    private static long usedMemorySum;
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

    public WritableImage getMapAsWritebleImage() {
        WritableImage writableImage = new WritableImage(
                IMAGE_SCALE_FACTOR * grid.getWidth(),
                IMAGE_SCALE_FACTOR * grid.getHeight());

        PixelWriter pixelWriter = writableImage.getPixelWriter();
        char[][] map;
        if (algo == null) {
            map = grid.getMarkedMap(null);
        } else {
            map = algo.getMarkedMap();
        }

        for (int y = 0; y < IMAGE_SCALE_FACTOR * grid.getHeight(); y++) {
            for (int x = 0; x < IMAGE_SCALE_FACTOR * grid.getWidth(); x++) {
                pixelWriter.setColor(x, y, getColor(
                        map[y / IMAGE_SCALE_FACTOR][x / IMAGE_SCALE_FACTOR]));
            }
        }

        return writableImage;
    }

    private Color getColor(char c) {
        switch (c) {
            case '.':
            case 'G':
                return Color.GREY;
            case '@':
            case 'O':
                return Color.BLACK;
            case 'T':
                return Color.GREEN;
            case 'S':
                return Color.PURPLE;
            case 'W':
                return Color.BLUE;
            case 'o':
                return Color.YELLOW;
            default:
                return Color.RED;
        }
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
