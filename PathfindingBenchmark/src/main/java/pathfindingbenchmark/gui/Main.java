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
//        Grid grid = new Grid("arena");
//        int startIdx = grid.getIdx(2, 2);
//        int goalIdx = grid.getIdx(34, 46);
//        Grid grid = new Grid("maze512-32-6");
//        int startIdx = grid.getIdx(114, 464);
//        int goalIdx = grid.getIdx(289, 153);
        Grid grid = new Grid("AR0011SR");
        int startIdx = grid.getIdx(65, 84);
        int goalIdx = grid.getIdx(203, 71);

        Dijkstra dijkstra = new Dijkstra(grid);
//        for (int i = 0; i < 1000; i++) {
//        dijkstra.run(startIdx, goalIdx);
//        }
//        printMarkedGrid(dijkstra);
//        printStatistics(dijkstra);
        AStar aStar = new AStar(grid);
        for (int i = 0; i < 2000; i++) {
            aStar.run(startIdx, goalIdx);
        }
//        printMarkedGrid(aStar);
//        printStatistics(aStar);
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
