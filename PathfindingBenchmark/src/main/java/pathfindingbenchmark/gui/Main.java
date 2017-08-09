/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import pathfindingbenchmark.algorithms.AStar;
import pathfindingbenchmark.algorithms.Dijkstra;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid("arena");
        int startIdx = grid.getIdx(2, 2);
        int goalIdx = grid.getIdx(34, 46);
//        Grid grid = new Grid("maze512-32-6");
//        int startIdx = grid.getIdx(114, 464);
//        int goalIdx = grid.getIdx(289, 153);

        Dijkstra dijkstra = new Dijkstra(grid);
        dijkstra.run(startIdx, goalIdx);
        dijkstra.printShortestPath();
        System.out.println(dijkstra.getRoundedDist(6));

        AStar aStar = new AStar(grid);
        aStar.run(startIdx, goalIdx);
        aStar.printShortestPath();
        System.out.println(aStar.getRoundedDist(6));
    }
}
