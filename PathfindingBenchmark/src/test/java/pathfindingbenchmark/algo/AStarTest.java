/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algo;

import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class AStarTest {

    private Grid grid;
    private AStar aStar;
    private Node s;
    private Node t;

    @Before
    public void setUp() {
        grid = new Grid("dao", "arena");
        aStar = new AStar(grid);
    }

    @Test
    public void testRun() {
    }
}
