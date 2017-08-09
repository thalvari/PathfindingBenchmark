/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class AStarAbstractTest {

    private Grid grid;
    private AStarAbstract algo;
    private int startIdx;
    private int goalIdx;

    @Before
    public void setUp() {
        grid = new Grid("lak110d");
        algo = new Dijkstra(grid);
    }

    @Test
    public void testGetRoundedCost() {
        startIdx = grid.getIdx(10, 10);
        goalIdx = grid.getIdx(10, 6);
        algo.run(startIdx, goalIdx);
        assertEquals("4", algo.getRoundedDist(6));
    }

    @Test
    public void testGetRoundedCost2() {
        startIdx = grid.getIdx(26, 15);
        goalIdx = grid.getIdx(3, 11);
        algo.run(startIdx, goalIdx);
        assertEquals("24.6569", algo.getRoundedDist(6));
    }

    @Test
    public void testPrintShortestPath() {
        grid = new Grid("empty_4");
        algo = new Dijkstra(grid);
        startIdx = grid.getIdx(0, 0);
        goalIdx = grid.getIdx(3, 3);
        algo.run(startIdx, goalIdx);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        algo.printShortestPath();
        String out = outStream.toString();
        assertTrue(out.contains("Xooo"));
        assertTrue(out.contains("oXoo"));
        assertTrue(out.contains("ooXo"));
        assertTrue(out.contains("oooX"));
        assertTrue(out.contains("\n"));
    }

    @Test
    public void testPrintShortestPath2() {
        startIdx = grid.getIdx(26, 15);
        goalIdx = grid.getIdx(3, 11);
        algo.run(startIdx, goalIdx);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        algo.printShortestPath();
        String out = outStream.toString();
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@T@@@@@@@TTToT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTTTTTTToooT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTTTTTToooTT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTooooooooTT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTooooooooTT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTToooooooTTTTTTTTTTTTTT"));
        assertTrue(out.contains("@TTTTTTTToooooooTTToooooTTooTT"));
        assertTrue(out.contains("@TTTTTTTToooooooTTToooooooooTT"));
        assertTrue(out.contains("@TTXooooooooooooooooooooooooTT"));
        assertTrue(out.contains("@TToXXXXXXXoooooooooooooooooTT"));
        assertTrue(out.contains("@TTooooooooXXXXXXXXXooooooooTT"));
        assertTrue(out.contains("@TToooooooooooooTTToXXXXXoooTT"));
        assertTrue(out.contains("TTTToooooTTTTTTTTTTTTTTToXXTTT"));
        assertTrue(out.contains("TTTTTTTTTTTTTTTTTTTTTTTToooTTT"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("\n"));
    }
}