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
public class DijkstraTest {

    private Grid grid;
    private Dijkstra dijkstra;
    private int startIdx;
    private int goalIdx;

    @Before
    public void setUp() {
        grid = new Grid("arena");
        dijkstra = new Dijkstra(grid);
    }

    @Test
    public void testRun() {
        startIdx = grid.getIdx(1, 7);
        goalIdx = grid.getIdx(47, 46);
        dijkstra.run(startIdx, goalIdx);
        assertEquals("62.1543", dijkstra.getRoundedDist(6));
        assertEquals(2054, dijkstra.getClosedNodeCount());
    }

    @Test
    public void testRun2() {
        startIdx = grid.getIdx(1, 42);
        goalIdx = grid.getIdx(44, 5);
        dijkstra.run(startIdx, goalIdx);
        assertEquals("58.3259", dijkstra.getRoundedDist(6));
        assertEquals(2022, dijkstra.getClosedNodeCount());
    }

    @Test
    public void testRun3() {
        startIdx = grid.getIdx(1, 12);
        goalIdx = grid.getIdx(14, 12);
        dijkstra.run(startIdx, goalIdx);
        assertEquals("13", dijkstra.getRoundedDist(6));
        assertEquals(232, dijkstra.getClosedNodeCount());
    }

    @Test
    public void testRun4() {
        startIdx = grid.getIdx(1, 42);
        goalIdx = grid.getIdx(4, 43);
        dijkstra.run(startIdx, goalIdx);
        assertEquals("3.41421", dijkstra.getRoundedDist(6));
        assertEquals(22, dijkstra.getClosedNodeCount());
    }

    @Test
    public void testRun5() {
        grid = new Grid("ost100d");
        dijkstra = new Dijkstra(grid);
        startIdx = grid.getIdx(753, 420);
        goalIdx = grid.getIdx(137, 561);
        dijkstra.run(startIdx, goalIdx);
        assertEquals("1122.11", dijkstra.getRoundedDist(6));
        assertEquals(133251, dijkstra.getClosedNodeCount());
    }

    @Test
    public void testRun6() {
        grid = new Grid("empty_64");
        dijkstra = new Dijkstra(grid);
        startIdx = grid.getIdx(0, 0);
        goalIdx = grid.getIdx(63, 63);
        dijkstra.run(startIdx, goalIdx);
        assertEquals("89.0955", dijkstra.getRoundedDist(6));
        assertEquals(4096, dijkstra.getClosedNodeCount());
    }

    @Test
    public void testHeuristic() {
        assertEquals(0, dijkstra.heuristic(1));
    }
}
