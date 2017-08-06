/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import pathfindingbenchmark.algo.AStar;
import pathfindingbenchmark.algo.Algo;
import pathfindingbenchmark.algo.Dijkstra;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class Main {

    public static void main(String[] args) {
//        Grid grid = new Grid("dao", "arena");
//        Node s = new Node(2, 2, grid);
//        Node t = new Node(34, 46, grid);
        Grid grid = new Grid("dao", "lak110d");
        Node s = new Node(26, 15, grid);
        Node t = new Node(3, 11, grid);
        Algo dijkstra = new Dijkstra(grid);
        Algo aStar = new AStar(grid);
        dijkstra.run(s, t);
        aStar.run(s, t);
        dijkstra.printShortestPath();
        System.out.println(dijkstra.getRoundedCost());
        aStar.printShortestPath();
        System.out.println(aStar.getRoundedCost());
    }
}
