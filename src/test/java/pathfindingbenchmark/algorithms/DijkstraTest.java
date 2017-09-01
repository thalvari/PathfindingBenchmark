/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class DijkstraTest {

    private Grid grid1;
    private AStarAbstract algo1;
    private AStarAbstract algo2;
    private AStarAbstract algo3;
    private AStarAbstract algo4;

    @Before
    public void setUp() {
        grid1 = new Grid("maze512-2-2");
        algo1 = new Dijkstra(grid1);
        algo2 = new Dijkstra(new Grid("32room_002"));
        algo3 = new Dijkstra(new Grid("orz100d"));
        algo4 = new Dijkstra(new Grid("random512-40-5"));
    }

    @Test
    public void testRun1() {
        algo1.run(145, 419, 483, 58);
        assertEquals("4754.04", getRoundedDist(algo1, 6));
    }

    @Test
    public void testRun2() {
        algo1.run(449, 236, 440, 46);
        assertEquals("4754.32", getRoundedDist(algo1, 6));
    }

    @Test
    public void testRun3() {
        algo1.run(479, 4, 2, 376);
        assertEquals("4753.94", getRoundedDist(algo1, 6));
    }

    @Test
    public void testRun4() {
        algo2.run(481, 47, 9, 506);
        assertEquals("811.649", getRoundedDist(algo2, 6));
    }

    @Test
    public void testRun5() {
        algo2.run(486, 414, 82, 41);
        assertEquals("809.566", getRoundedDist(algo2, 6));
    }

    @Test
    public void testRun6() {
        algo2.run(23, 20, 459, 364);
        assertEquals("810.85", getRoundedDist(algo2, 6));
    }

    @Test
    public void testRun7() {
        algo3.run(397, 233, 149, 17);
        assertEquals("971.82", getRoundedDist(algo3, 6));
    }

    @Test
    public void testRun8() {
        algo3.run(392, 32, 386, 229);
        assertEquals("971.59", getRoundedDist(algo3, 6));
    }

    @Test
    public void testRun9() {
        algo3.run(407, 329, 138, 15);
        assertEquals("937.365", getRoundedDist(algo3, 6));
    }

    @Test
    public void testRun10() {
        algo4.run(114, 240, 82, 322);
        assertEquals("2004.81", getRoundedDist(algo4, 6));
    }

    @Test
    public void testRun11() {
        algo4.run(32, 308, 183, 232);
        assertEquals("2005.36", getRoundedDist(algo4, 6));
    }

    @Test
    public void testRun12() {
        algo4.run(8, 99, 179, 423);
        assertEquals("1785.83", getRoundedDist(algo4, 6));
    }

    @Test
    public void testHeuristic() {
        assertEquals(0, algo1.heuristic(grid1.getNode(0, 0)));
    }

    private String getRoundedDist(AStarAbstract algo, int n) {
        return new BigDecimal(algo.getMinDist())
                .divide(BigDecimal.valueOf(Grid.HOR_VER_NODE_DIST),
                        new MathContext(n, RoundingMode.HALF_UP))
                .stripTrailingZeros()
                .toString();
    }
}
