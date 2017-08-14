/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class AStarTest {

    private Grid grid;
    private AStar aStar;
    private int startIdx;
    private int goalIdx;

    @Before
    public void setUp() {
        grid = new Grid("AR0011SR-512");
        aStar = new AStar(grid);
    }

    @Test
    public void testRun() {
        startIdx = grid.getIdx(210, 395);
        goalIdx = grid.getIdx(87, 201);
        aStar.run(startIdx, goalIdx);
        assertEquals("244.95", aStar.getRoundedDist(5));
        assertEquals(195, aStar.getClosedNodeCount());
    }

    @Test
    public void testRun2() {
        startIdx = grid.getIdx(324, 107);
        goalIdx = grid.getIdx(375, 360);
        aStar.run(startIdx, goalIdx);
        assertEquals("454.62", aStar.getRoundedDist(5));
        assertEquals(24924, aStar.getClosedNodeCount());
    }

    @Test
    public void testRun3() {
        startIdx = grid.getIdx(210, 54);
        goalIdx = grid.getIdx(382, 255);
        aStar.run(startIdx, goalIdx);
        assertEquals("455.66", aStar.getRoundedDist(5));
        assertEquals(25473, aStar.getClosedNodeCount());
    }

    @Test
    public void testRun4() {
        grid = new Grid("maze512-32-6");
        aStar = new AStar(grid);
        startIdx = grid.getIdx(114, 464);
        goalIdx = grid.getIdx(289, 153);
        aStar.run(startIdx, goalIdx);
        assertEquals("2275.04", aStar.getRoundedDist(6));
        assertEquals(220792, aStar.getClosedNodeCount());
    }

    @Test
    public void testHeuristic() {
        startIdx = grid.getIdx(210, 395);
        goalIdx = grid.getIdx(87, 201);
        aStar.run(startIdx, goalIdx);
        assertEquals(0, aStar.heuristic(goalIdx));
    }

    @Test
    public void testHeuristic2() {
        startIdx = grid.getIdx(210, 395);
        goalIdx = grid.getIdx(87, 201);
        aStar.run(startIdx, goalIdx);
        long octileDist = (194 - 123) * Grid.HOR_VER_NODE_DIST + 123
                * Grid.DIAG_NODE_DIST;

        assertEquals(octileDist, aStar.heuristic(startIdx));
    }
}
