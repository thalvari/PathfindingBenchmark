/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.grid.Grid;

/**
 * Algoritmin A* toteutus.
 *
 * @author thalvari
 */
public class AStar extends AStarAbstract {

    /**
     * Konstruktori käyttää yläluokan konstruktoria.
     *
     * @param grid Verkko.
     */
    public AStar(Grid grid) {
        super(grid);
    }

    @Override
    protected long heuristic(int idx) {
        int xDif = Math.abs(grid.getX(idx) - grid.getX(goalIdx));
        int yDif = Math.abs(grid.getY(idx) - grid.getY(goalIdx));
        return Grid.HOR_VER_DIST * Math.max(xDif, yDif)
                + (Grid.DIAG_DIST - Grid.HOR_VER_DIST) * Math.min(xDif, yDif);
    }
}
