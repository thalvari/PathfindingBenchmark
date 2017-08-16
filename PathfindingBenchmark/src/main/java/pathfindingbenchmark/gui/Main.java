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
import pathfindingbenchmark.algorithms.AStar;
import pathfindingbenchmark.algorithms.AStarAbstract;
import pathfindingbenchmark.algorithms.Dijkstra;
import pathfindingbenchmark.algorithms.JPS;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class Main {

    private static final String[] ALGOS = {"Dijkstra", "AStar", "JPS"};
    private static final int SAMPLE_SIZE = 100;
    private static final ThreadMXBean BEAN =
            ManagementFactory.getThreadMXBean();

    public static void main(String[] args) {
        test("arena2", 275, 206, 4, 98, false);
        test("AR0011SR", 65, 84, 203, 71, false);
        test("lak505d", 171, 152, 135, 178, false);
    }

    private static void test(String mapName, int startX, int startY, int goalX,
            int goalY, boolean print) {

        Grid grid = new Grid(mapName);
        printMapName(mapName);
        AStarAbstract algo;
        for (String algoName : ALGOS) {
            if (algoName.equals("Dijkstra")) {
                algo = new Dijkstra(grid);
            } else if (algoName.equals("AStar")) {
                algo = new AStar(grid);
            } else {
                algo = new JPS(grid);
            }

            long cpuTimeSum = runAlgo(algo, startX, startY, goalX, goalY);
            if (print) {
                printMarkedGrid(algo);
            }

            printStatistics(algo, grid);
            printCpuTime(cpuTimeSum);
            System.out.println("");
        }
    }

    private static void printMapName(String mapName) {
        for (int i = 0; i < mapName.length(); i++) {
            System.out.print("-");
        }

        System.out.println("");
        System.out.println(mapName);
        for (int i = 0; i < mapName.length(); i++) {
            System.out.print("-");
        }

        System.out.println("\n");
    }

    private static long runAlgo(AStarAbstract algo, int startX, int startY,
            int goalX, int goalY) {

        long cpuTimeSum = 0;
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            long startCpuTime = BEAN.getCurrentThreadCpuTime();
            algo.run(startX, startY, goalX, goalY);
            cpuTimeSum += BEAN.getCurrentThreadCpuTime() - startCpuTime;
        }

        return cpuTimeSum;
    }

    private static void printMarkedGrid(AStarAbstract algo) {
        String[][] mapData = algo.getMarkedGrid();
        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[0].length; x++) {
                System.out.print(mapData[y][x]);
            }
            System.out.println();
        }
    }

    private static void printStatistics(AStarAbstract algo, Grid grid) {
        if (algo instanceof Dijkstra) {
            System.out.println("Dijkstra:");
        } else if (algo instanceof AStar) {
            System.out.println("A*:");
        } else if (algo instanceof JPS) {
            System.out.println("JPS:");
        }

        System.out.println("Lyhimmän polun pituus: " + algo.getRoundedDist(6)
                + ".");

        System.out.println("Solmujen määrä: " + grid.getPassableNodeCount()
                + ".");

        System.out.println("Käsiteltyjen solmujen määrä: "
                + algo.getClosedNodeCount() + ".");

        System.out.println("Keko-operaatioiden määrä: "
                + algo.getHeapOperCount() + ".");
    }

    private static void printCpuTime(long cpuTimeSum) {
        System.out.println("Suoritusaika: " + new BigDecimal(cpuTimeSum)
                .divide(BigDecimal.valueOf(SAMPLE_SIZE * 1000000))
                .setScale(3, RoundingMode.HALF_UP)
                .toString() + " ms.");
    }
}
