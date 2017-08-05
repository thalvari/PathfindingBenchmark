/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

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
        Grid grid = new Grid("dao", "lak110d");
        Algo algo = new Dijkstra(grid);
        Node s = new Node(14, 4, grid);
        Node t = new Node(27, 9, grid);
        algo.run(s, t);
        algo.printShortestPath();
        System.out.println(algo.getRoundedCost());
    }
}
