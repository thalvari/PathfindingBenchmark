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
    private AStarAbstract algo1;
    private AStarAbstract algo2;
    private AStarAbstract algo3;

    @Before
    public void setUp() {
        grid = new Grid("lak110d");
        algo1 = new Dijkstra(grid);
        algo2 = new Dijkstra(new Grid("empty_4"));
        algo3 = new AStar(grid);
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
    public void testGetMarkedMap1() {
        algo1.run(26, 15, 3, 11);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        printMarkedMap(algo1);
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
    }

    private void printMarkedMap(AStarAbstract algo) {
        char[][] mapData = algo.getMarkedMap();
        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[0].length; x++) {
                System.out.print(mapData[y][x]);
            }
            System.out.println();
        }
    }

    @Test
    public void testGetMarkedMap2() {
        algo2.run(0, 0, 3, 3);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        printMarkedMap(algo2);
        String out = outStream.toString();
        assertTrue(out.contains("Xooo"));
        assertTrue(out.contains("oXoo"));
        assertTrue(out.contains("ooXo"));
        assertTrue(out.contains("oooX"));
        assertTrue(out.contains("\n"));
    }

    @Test
    public void testGetHeapDecKeyOperCount() {
        algo3.run(14, 4, 27, 9);
        assertEquals(9, algo3.getHeapDecKeyOperCount());
    }

    @Test
    public void testGetHeapDelMinOperCount() {
        algo1.run(14, 4, 27, 9);
        assertEquals(165, algo1.getHeapDelMinOperCount());
    }

    @Test
    public void testGetHeapInsertOperCount() {
        algo1.run(14, 4, 27, 9);
        assertEquals(168, algo1.getHeapInsertOperCount());
    }

    @Test
    public void testGetSuccListTotalSize() {
        algo1.run(14, 4, 27, 9);
        assertEquals(1058, algo1.getSuccListTotalSize());
    }

    @Test
    public void testInit() {
        algo3.run(26, 15, 3, 11);
        algo3.run(14, 4, 27, 9);
        assertEquals("20.2426", algo3.getRoundedDist(6));
        assertEquals(9, algo3.getHeapDecKeyOperCount());
        assertEquals(39, algo3.getHeapDelMinOperCount());
        assertEquals(68, algo3.getHeapInsertOperCount());
    }

    @Test
    public void testHeuristic() {
        algo3.run(14, 4, 27, 9);
        assertEquals(0, algo1.heuristic(grid.getNode(27, 9)));
    }

    @Test
    public void testHeuristic2() {
        algo3.run(14, 4, 27, 9);
        long octileDist = (13 - 5) * Grid.HOR_VER_NODE_DIST + 5
                * Grid.DIAG_NODE_DIST;

        assertEquals(octileDist, algo3.heuristic(grid.getNode(14, 4)));
    }
}
