/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.wrapper;

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
import pathfindingbenchmark.grid.Node;

/**
 * Luokka muuttaa algoritmiluokkien tulokset GUI:n haluamaan muotoon.
 *
 * @author thalvari
 */
public class Wrapper {

    /**
     * Yliskaalauskerroin kartan kuvalle, jotta rajat pysyvät terävinä.
     */
    protected static final int IMAGE_SCALE_FACTOR = 8;
    private static final int SAMPLE_SIZE = 25;
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final ThreadMXBean BEAN = ManagementFactory
            .getThreadMXBean();

    private Grid grid;
    private AStarAbstract algo;
    private long cpuTimeSum;
    private long usedMemorySum;
    private int passableNodeCount;
    private int closedNodeCount;

    /**
     * Palauttaa algoritmin.
     *
     * @return Algoritmi.
     */
    public AStarAbstract getAlgo() {
        return algo;
    }

    /**
     * Palauttaa verkon.
     *
     * @return Verkko.
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Asettaa algoritmin.
     *
     * @param algoName Algoritmin nimi.
     */
    public void setAlgo(String algoName) {
        if (algoName.equals("Dijkstra")) {
            algo = new Dijkstra(grid);
        } else if (algoName.equals("AStar")) {
            algo = new AStar(grid);
        } else {
            algo = new JPS(grid);
        }
    }

    /**
     * Asettaa verkon.
     *
     * @param mapName Kartan nimi.
     */
    public void setGrid(String mapName) {
        grid = new Grid(mapName);
        algo = null;
        calcPassableNodeCount();
    }

    /**
     * Palauttaa kartan kuvaoliona.
     *
     * @return Kuvaolio.
     */
    public WritableImage getMapAsWritableImage() {
        int height = IMAGE_SCALE_FACTOR * grid.getHeight();
        int width = IMAGE_SCALE_FACTOR * grid.getWidth();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        if (algo != null) {
            algo.markPath();
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelWriter.setColor(x, y, getColor(grid.getNode(
                        x / IMAGE_SCALE_FACTOR,
                        y / IMAGE_SCALE_FACTOR)));
            }
        }

        return writableImage;
    }

    private Color getColor(Node node) {
        switch (node.getSymbol()) {
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
            case 'c':
                return Color.YELLOW;
            default:
                return Color.RED;
        }
    }

    /**
     * Tarkastaa lähtö- ja maalikoordinaatit.
     *
     * @param startX Lähtösolmun x-koordinaatti.
     * @param startY Lähtösolmun y-koordinaatti.
     * @param goalX Maalisolmun x-koordinaatti.
     * @param goalY Maalisolmun y-koordinaatti.
     * @return Totuusarvo.
     */
    public boolean checkCoordinates(int startX, int startY, int goalX,
            int goalY) {

        return grid != null
                && grid.isInBounds(startX, startY)
                && grid.getNode(startX, startY).isPassable()
                && grid.isInBounds(goalX, goalY)
                && grid.getNode(goalX, goalY).isPassable();
    }

    /**
     * Ajaa algoritmin.
     *
     * @param startX Lähtösolmun x-koordinaatti.
     * @param startY Lähtösolmun y-koordinaatti.
     * @param goalX Maalisolmun x-koordinaatti.
     * @param goalY Maalisolmun y-koordinaatti.
     */
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

        calcClosedNodeCount();
    }

    /**
     * Palauttaa pienimmän etäisyyden lähdöstä maaliin.
     *
     * @return Etäisyys.
     */
    public String getMinDist() {
        if (algo.isSolved()) {
            return divideAndRound(algo.getMinDist(), Grid.HOR_VER_NODE_DIST);
        } else {
            return "inf";
        }
    }

    /**
     * Palauttaa seuraajalistojen keskimääräisen koon.
     *
     * @return Koko.
     */
    public String getAvgSuccListSize() {
        int succListCount = closedNodeCount;
        if (algo.isSolved()) {
            succListCount--;
        }

        return divideAndRound(algo.getSuccListTotalSize(), succListCount);
    }

    /**
     * Palauttaa suoritusajan.
     *
     * @return Suoritusaika.
     */
    public String getCpuTime() {
        return divideAndRound(cpuTimeSum, SAMPLE_SIZE * 1000000L) + " ms";
    }

    /**
     * Palauttaa käytetyn muistin.
     *
     * @return Käytetty muisti.
     */
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
                .stripTrailingZeros()
                .toString();
    }

    private void calcClosedNodeCount() {
        closedNodeCount = 0;
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                if (grid.getNode(x, y).isClosed()) {
                    closedNodeCount++;
                }
            }
        }
    }

    private void calcPassableNodeCount() {
        passableNodeCount = 0;
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                if (grid.getNode(x, y).isPassable()) {
                    passableNodeCount++;
                }
            }
        }
    }

    /**
     * Palauttaa suljettujen solmujen osuuden prosentteina.
     *
     * @return Osuus.
     */
    public String getClosedNodePercentage() {
        return divideAndRound(100L * closedNodeCount, passableNodeCount) + " %";
    }

    /**
     * Palauttaa läpikuljettavien solmujen määrän.
     *
     * @return Määrä.
     */
    public int getPassableNodeCount() {
        return passableNodeCount;
    }
}
