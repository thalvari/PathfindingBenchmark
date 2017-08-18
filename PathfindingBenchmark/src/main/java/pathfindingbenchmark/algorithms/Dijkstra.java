/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.grid.Grid;

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
    protected int heuristic(int idx) {
        return 0;
    }
}
