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
        Grid grid = new Grid("dao", "arena2");
        Dijkstra dijkstra = new Dijkstra(grid);
        Node s = new Node(101, 111, grid);
        Node t = new Node(90, 123, grid);
        dijkstra.run(s);
        dijkstra.printShortestPath(t);
        System.out.println(dijkstra.getRoundedCost(t));
    }
}
