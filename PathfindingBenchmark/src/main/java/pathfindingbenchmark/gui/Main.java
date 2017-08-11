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

    private static final int DIJKSTRA = 1;
    private static final int ASTAR = 2;

    public static void main(String[] args) {
//        run("arena", 2, 2, 34, 46, ASTAR, 30000, false);
//        run("maze512-32-6", 114, 464, 289, 153, ASTAR, 50, false);
        run("AR0011SR", 65, 84, 203, 71, ASTAR, 1500, false);
    }

    private static void run(String mapName, int startX, int startY, int goalX,
            int goalY, int algoIdx, int n, boolean print) {

        Grid grid = new Grid(mapName);
        int startIdx = grid.getIdx(startX, startY);
        int goalIdx = grid.getIdx(goalX, goalY);
        AStarAbstract algo;
        switch (algoIdx) {
            case DIJKSTRA:
                algo = new Dijkstra(grid);
                break;
            case ASTAR:
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

        printStatistics(algo, grid);
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
        }

        System.out.println("Lyhimmän polun pituus: " + algo.getRoundedDist(6)
                + ".");
        System.out.println("Solmujen määrä: " + grid.getN() + ".");
        System.out.println("Käsiteltyjen solmujen määrä: "
                + algo.getClosedNodeCount() + ".");
        System.out.println("Keko-operaatioiden määrä: "
                + algo.getHeapOperCount() + ".");
    }
}
