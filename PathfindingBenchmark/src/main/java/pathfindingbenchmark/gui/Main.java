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
import javafx.application.Application;
import javafx.stage.Stage;
import pathfindingbenchmark.algorithms.AStar;
import pathfindingbenchmark.algorithms.AStarAbstract;
import pathfindingbenchmark.algorithms.Dijkstra;
import pathfindingbenchmark.algorithms.JPS;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class Main extends Application {

    private static final String[] ALGOS = {"Dijkstra", "AStar", "JPS"};
    private static final int SAMPLE_SIZE = 50;
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final ThreadMXBean BEAN =
            ManagementFactory.getThreadMXBean();

    private static long cpuTimeSum;
    private static long usedMemorySum;

    public static void main(String[] args) {
        launch();

//        test("AR0011SR", 65, 84, 203, 71, true);
//        test("lak505d", 171, 152, 135, 178, true);
//        test("rmtst01", 176, 22, 1, 23, true);
//        test("maze512-1-0", 497, 89, 467, 44, false);
//        test("maze512-32-0", 59, 434, 101, 194, false);
//        test("random512-10-0", 19, 44, 509, 436, false);
//        test("random512-40-5", 114, 240, 82, 322, false);
//        test("8room_000", 7, 463, 484, 37, false);
//        test("64room_000", 496, 505, 48, 17, false);
    }

    private static void test(String mapName, int startX, int startY, int goalX,
            int goalY, boolean print) {

        Grid grid = new Grid(mapName);
        printMapInfo(mapName, startX, startY, goalX, goalY, grid);
        AStarAbstract algo;
        for (String algoName : ALGOS) {
            if (algoName.equals("Dijkstra")) {
                algo = new Dijkstra(grid);
            } else if (algoName.equals("AStar")) {
                algo = new AStar(grid);
            } else {
                algo = new JPS(grid);
            }

            cpuTimeSum = 0;
            usedMemorySum = 0;
            runAlgo(algo, startX, startY, goalX, goalY);
            printStatistics(algo, grid);
            if (print) {
                printMarkedGrid(algo);
            }

            System.out.println("");
        }
    }

    private static void printMapInfo(String mapName, int startX, int startY,
            int goalX, int goalY, Grid grid) {
        for (int i = 0; i < mapName.length(); i++) {
            System.out.print("-");
        }

        System.out.println("");
        System.out.println("Kartan nimi: " + mapName);
        System.out.println("Kartan mitat: " + grid.getWidth() + "x"
                + grid.getHeight());

        System.out.println("Solmujen määrä: "
                + grid.getPassableNodeCount());

        System.out.println("Lähtösolmu: (" + startX + ", " + startY + ")");
        System.out.println("Maalisolmu: (" + goalX + ", " + goalY + ")");
        for (int i = 0; i < mapName.length(); i++) {
            System.out.print("-");
        }

        System.out.println("\n");
    }

    private static void runAlgo(AStarAbstract algo, int startX, int startY,
            int goalX, int goalY) {

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

    private static void printMarkedGrid(AStarAbstract algo) {
        System.out.println("");
        char[][] mapData = algo.getMarkedMap();
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

        System.out.println("Seuraajalistan koon ka: "
                + divideAndRound(algo.getSuccListTotalSize(),
                        algo.getHeapDelMinOperCount() - 1)
                + ".");

        System.out.println("Keon insert-operaatioiden määrä: "
                + algo.getHeapInsertOperCount() + ".");

        System.out.println("Keon del-min-operaatioiden määrä: "
                + algo.getHeapDelMinOperCount() + ".");

        System.out.println("Keon dec-key-operaatioiden määrä: "
                + algo.getHeapDecKeyOperCount() + ".");

        System.out.println("Suoritusaika: "
                + divideAndRound(cpuTimeSum, SAMPLE_SIZE * 1000000L)
                + " ms.");

        System.out.println("Käytetty muisti: "
                + divideAndRound(usedMemorySum, SAMPLE_SIZE * 1024L * 1024L)
                + " MB.");
    }

    private static String divideAndRound(long numer, long denom) {
        if (denom == 0) {
            return "0";
        }

        return new BigDecimal(numer)
                .divide(BigDecimal.valueOf(denom), 3, RoundingMode.HALF_UP)
                .toString();
    }

    @Override
    public void start(Stage stage) {
        try {
            new Window(stage).show();
        } catch (Exception e) {
        }
    }

    @Override
    public void stop() {
    }
}
