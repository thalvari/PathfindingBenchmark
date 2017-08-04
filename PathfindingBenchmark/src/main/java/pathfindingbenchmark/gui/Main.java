/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import pathfindingbenchmark.algos.Dijkstra;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid("dao", "arena");
        Dijkstra dijkstra = new Dijkstra(grid);
//        Node s = new Node(1, 10, grid);
//        Node t = new Node(46, 15, grid);
        Node s = new Node(1, 7, grid);
        Node t = new Node(47, 46, grid);
        double cost = dijkstra.run(s, t);
        dijkstra.printShortestPath(s, t);
        System.out.println(cost);
    }
}
