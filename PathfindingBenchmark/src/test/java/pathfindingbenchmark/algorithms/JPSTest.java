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
public class JPSTest {

    private Grid grid1;
    private Grid grid2;
    private Grid grid3;
    private JPS jps1;
    private JPS jps2;
    private JPS jps3;
    private int startIdx;
    private int goalIdx;

    @Before
    public void setUp() {
        grid1 = new Grid("AR0011SR-512");
        grid2 = new Grid("AcrosstheCape");
        grid3 = new Grid("8room_000");
        jps1 = new JPS(grid1);
        jps2 = new JPS(grid2);
        jps3 = new JPS(grid3);
    }

    @Test
    public void testRun1() {
        startIdx = grid1.getIdx(327, 119);
        goalIdx = grid1.getIdx(403, 294);
        jps1.run(startIdx, goalIdx);
        assertEquals("510.99", jps1.getRoundedDist(5));
        assertEquals(103, jps1.getClosedNodeCount());
        assertEquals(217, jps1.getHeapOperCount());
    }

    @Test
    public void testRun2() {
        startIdx = grid1.getIdx(123, 236);
        goalIdx = grid1.getIdx(429, 143);
        jps1.run(startIdx, goalIdx);
        assertEquals("492.91", jps1.getRoundedDist(5));
        assertEquals(185, jps1.getClosedNodeCount());
        assertEquals(393, jps1.getHeapOperCount());
    }

    @Test
    public void testRun3() {
        startIdx = grid2.getIdx(690, 310);
        goalIdx = grid2.getIdx(8, 685);
        jps2.run(startIdx, goalIdx);
        assertEquals("1179.8", jps2.getRoundedDist(6));
        assertEquals(1266, jps2.getClosedNodeCount());
        assertEquals(2657, jps2.getHeapOperCount());
    }

    @Test
    public void testRun4() {
        startIdx = grid2.getIdx(666, 737);
        goalIdx = grid2.getIdx(10, 5);
        jps2.run(startIdx, goalIdx);
        assertEquals("1176.61", jps2.getRoundedDist(6));
        assertEquals(1239, jps2.getClosedNodeCount());
        assertEquals(2586, jps2.getHeapOperCount());
    }

    @Test
    public void testRun5() {
        startIdx = grid3.getIdx(7, 463);
        goalIdx = grid3.getIdx(484, 37);
        jps3.run(startIdx, goalIdx);
        assertEquals("778.955", jps3.getRoundedDist(6));
        assertEquals(9122, jps3.getClosedNodeCount());
        assertEquals(18993, jps3.getHeapOperCount());
    }

    @Test
    public void testRun6() {
        startIdx = grid3.getIdx(447, 502);
        goalIdx = grid3.getIdx(7, 59);
        jps3.run(startIdx, goalIdx);
        assertEquals("779.985", jps3.getRoundedDist(6));
        assertEquals(11266, jps3.getClosedNodeCount());
        assertEquals(23372, jps3.getHeapOperCount());
    }
}
