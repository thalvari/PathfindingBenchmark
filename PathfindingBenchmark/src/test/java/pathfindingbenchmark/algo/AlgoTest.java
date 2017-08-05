/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class AlgoTest {

    private Grid grid;
    private Algo algo;
    private Node s;
    private Node t;

    @Before
    public void setUp() {
        grid = new Grid("dao", "lak110d");
        algo = new Dijkstra(grid);
    }

    @Test
    public void testGetRoundedCost() {
        s = new Node(10, 10, grid);
        t = new Node(10, 6, grid);
        algo.run(s, t);
        assertEquals("4", algo.getRoundedCost());
    }

    @Test
    public void testGetRoundedCost2() {
        s = new Node(26, 15, grid);
        t = new Node(3, 11, grid);
        algo.run(s, t);
        assertEquals("24.6569", algo.getRoundedCost());
    }

    @Test
    public void testPrintShortestPath() {
        s = new Node(26, 15, grid);
        t = new Node(3, 11, grid);
        algo.run(s, t);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        algo.printShortestPath();
        String out = outStream.toString();
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@@@@@@@@@TTTTTT@@@@@@@@@@@"));
        assertTrue(out.contains("@@@@@T@@@@@@@TTT.T@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTTTTTTT...T@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTTTTTT...TT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT........TT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT........TT@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTTT.......TTTTTTTTTTTTTT"));
        assertTrue(out.contains("@TTTTTTTT.......TTT.....TT..TT"));
        assertTrue(out.contains("@TTTTTTTT.......TTT.........TT"));
        assertTrue(out.contains("@TTX........................TT"));
        assertTrue(out.contains("@TT.XXXXXXX.................TT"));
        assertTrue(out.contains("@TT........XXXXXXXXX........TT"));
        assertTrue(out.contains("@TT.............TTT.XXXXX...TT"));
        assertTrue(out.contains("TTTT.....TTTTTTTTTTTTTTT.XXTTT"));
        assertTrue(out.contains("TTTTTTTTTTTTTTTTTTTTTTTT...TTT"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("\n"));
    }
}
