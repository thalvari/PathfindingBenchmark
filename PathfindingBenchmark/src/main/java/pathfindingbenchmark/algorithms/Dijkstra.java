/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 * Dijkstran algoritmin toteutus.
 *
 * @author thalvari
 */
public class Dijkstra extends AStarAbstract {

    /**
     * Konstruktori käyttää yläluokan konstruktoria.
     *
     * @param grid Verkko.
     */
    public Dijkstra(Grid grid) {
        super(grid);
    }

    @Override
    protected int heuristic(Node node) {
        return 0;
    }
}
