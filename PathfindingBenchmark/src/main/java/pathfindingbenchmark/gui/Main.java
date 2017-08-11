/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import pathfindingbenchmark.algorithms.AStar;
import pathfindingbenchmark.algorithms.AStarAbstract;
import pathfindingbenchmark.algorithms.Dijkstra;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class Main {

    public static void main(String[] args) {
//        run("arena", 2, 2, 34, 46, 1, 30000, false);
//        run("maze512-32-6", 114, 464, 289, 153, 1, 50, false);
        run("AR0011SR", 65, 84, 203, 71, 2, 1500, true);
    }

    private static void run(String mapName, int startX, int startY, int goalX,
            int goalY, int algoIdx, int n, boolean print) {

        Grid grid = new Grid(mapName);
        int startIdx = grid.getIdx(startX, startY);
        int goalIdx = grid.getIdx(goalX, goalY);
        AStarAbstract algo;
        switch (algoIdx) {
            case 1:
                algo = new Dijkstra(grid);
                break;
            case 2:
                algo = new AStar(grid);
                break;
            default:
                return;
        }

        for (int i = 0; i < n; i++) {
            algo.run(startIdx, goalIdx);
        }

        if (print) {
            printMarkedGrid(algo);
        }

        printStatistics(algo);
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

    private static void printStatistics(AStarAbstract algo) {
        if (algo instanceof Dijkstra) {
            System.out.println("Dijkstra:");
        } else if (algo instanceof AStar) {
            System.out.println("A*:");
        }

        System.out.println("Lyhimmän polun pituus: " + algo.getRoundedDist(6)
                + ".");
        System.out.println("Käsitellyt solmut: " + algo.getClosedCounter()
                + ".");
        System.out.println("Keon lisäysoperaatiot: " + algo.getHeapAddCounter()
                + ".");
        System.out.println("Keon poisto-operaatiot: " + algo.getHeapDelCounter()
                + ".");
    }
}
