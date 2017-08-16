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

    private AStarAbstract algo1;
    private AStarAbstract algo2;

    @Before
    public void setUp() {
        algo1 = new Dijkstra(new Grid("lak110d"));
        algo2 = new Dijkstra(new Grid("empty_4"));
    }

    @Test
    public void testGetRoundedCost1() {
        algo1.run(10, 10, 10, 6);
        assertEquals("4", algo1.getRoundedDist(6));
    }

    @Test
    public void testGetRoundedCost2() {
        algo1.run(26, 15, 3, 11);
        assertEquals("24.6569", algo1.getRoundedDist(6));
    }

    @Test
    public void testGetMarkedGrid1() {
        algo1.run(26, 15, 3, 11);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        printMarkedGrid(algo1);
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
        assertTrue(out.contains("@TToXoooooooooooooooooooooooTT"));
        assertTrue(out.contains("@TTooXXXXXXXXXXXXXXXooooooooTT"));
        assertTrue(out.contains("@TToooooooooooooTTToXXXXXoooTT"));
        assertTrue(out.contains("TTTToooooTTTTTTTTTTTTTTToXXTTT"));
        assertTrue(out.contains("TTTTTTTTTTTTTTTTTTTTTTTToooTTT"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("\n"));
    }

    private void printMarkedGrid(AStarAbstract algo) {
        String[][] mapData = algo.getMarkedGrid();
        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[0].length; x++) {
                System.out.print(mapData[y][x]);
            }
            System.out.println();
        }
    }

    @Test
    public void testGetMarkedGrid2() {
        algo2.run(0, 0, 3, 3);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        printMarkedGrid(algo2);
        String out = outStream.toString();
        assertTrue(out.contains("Xooo"));
        assertTrue(out.contains("oXoo"));
        assertTrue(out.contains("ooXo"));
        assertTrue(out.contains("oooX"));
        assertTrue(out.contains("\n"));
    }

    @Test
    public void testGetClosedCounter() {
        algo1.run(14, 4, 27, 9);
        assertEquals(165, algo1.getClosedNodeCount());
    }

    @Test
    public void testGetHeapOperCount() {
        algo1.run(14, 4, 27, 9);
        assertEquals(333, algo1.getHeapOperCount());
    }

    @Test
    public void testInit() {
        algo1.run(26, 15, 3, 11);
        algo1.run(14, 4, 27, 9);
        assertEquals("20.2426", algo1.getRoundedDist(6));
        assertEquals(165, algo1.getClosedNodeCount());
        assertEquals(333, algo1.getHeapOperCount());
    }
}
