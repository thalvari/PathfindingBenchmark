/*
 * To change this license header, chccse License Headers in Project Properties.
 * To change this template file, chccse Tccls | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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

    private Grid grid1;
    private Grid grid2;
    private AStarAbstract algo1;
    private AStarAbstract algo2;
    private AStarAbstract algo3;

    @Before
    public void setUp() {
        grid1 = new Grid("lak110d");
        grid2 = new Grid("empty_4");
        algo1 = new Dijkstra(grid1);
        algo2 = new Dijkstra(grid2);
        algo3 = new AStar(grid1);
    }

    @Test
    public void testGetRoundedCost1() {
        algo1.run(10, 10, 10, 6);
        assertEquals("4", getRoundedDist(algo1, 6));
    }

    @Test
    public void testGetRoundedCost2() {
        algo1.run(26, 15, 3, 11);
        assertEquals("24.6569", getRoundedDist(algo1, 6));
    }

    @Test
    public void testGetMarkedMap1() {
        algo1.run(26, 15, 3, 11);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        printMarkedMap(algo1, grid1);
        String out = outStream.toString();
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@T@@@@@@@TTTcT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTTTTTTTcccT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTTTTTTcccTT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTccccccccTT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTccccccccTT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTTcccccccTTTTTTTTTTTTTT"));
        assertTrue(out.contains("@TTTTTTTTcccccccTTTcccccTTccTT"));
        assertTrue(out.contains("@TTTTTTTTcccccccTTTcccccccccTT"));
        assertTrue(out.contains("@TTPccccccccccccccccccccccccTT"));
        assertTrue(out.contains("@TTcPcccccccccccccccccccccccTT"));
        assertTrue(out.contains("@TTccPPPPPPPPPPPPPPPccccccccTT"));
        assertTrue(out.contains("@TTcccccccccccccTTTcPPPPPcccTT"));
        assertTrue(out.contains("TTTTcccccTTTTTTTTTTTTTTTcPPTTT"));
        assertTrue(out.contains("TTTTTTTTTTTTTTTTTTTTTTTTcccTTT"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
    }

    private void printMarkedMap(AStarAbstract algo, Grid grid) {
        algo.markPath();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                System.out.print(grid.getNode(x, y).getSymbol());
            }

            System.out.println();
        }
    }

    @Test
    public void testGetMarkedMap2() {
        algo2.run(0, 0, 3, 3);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        printMarkedMap(algo2, grid2);
        String out = outStream.toString();
        assertTrue(out.contains("Pccc"));
        assertTrue(out.contains("cPcc"));
        assertTrue(out.contains("ccPc"));
        assertTrue(out.contains("cccP"));
        assertTrue(out.contains("\n"));
    }

    @Test
    public void testGetHeapOperCount() {
        algo3.run(14, 4, 27, 9);
        assertEquals(116, algo3.getHeapOperCount());
    }

    @Test
    public void testGetSuccListTotalSize() {
        algo1.run(14, 4, 27, 9);
        assertEquals(1058, algo1.getSuccListTotalSize());
    }

    @Test
    public void testHeuristic() {
        algo3.run(14, 4, 27, 9);
        assertEquals(0, algo1.heuristic(grid1.getNode(27, 9)));
    }

    @Test
    public void testHeuristic2() {
        algo3.run(14, 4, 27, 9);
        long octileDist = (13 - 5) * Grid.HOR_VER_NODE_DIST + 5
                * Grid.DIAG_NODE_DIST;

        assertEquals(octileDist, algo3.heuristic(grid1.getNode(14, 4)));
    }

    private String getRoundedDist(AStarAbstract algo, int n) {
        return new BigDecimal(algo.getMinDist())
                .divide(BigDecimal.valueOf(Grid.HOR_VER_NODE_DIST),
                        new MathContext(n, RoundingMode.HALF_UP))
                .stripTrailingZeros()
                .toString();
    }
}
