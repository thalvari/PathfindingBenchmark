/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algos;

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
public class DijkstraTest {

    private Grid grid;
    private Dijkstra dijkstra;

    @Before
    public void setUp() {
        grid = new Grid("dao", "arena");
        dijkstra = new Dijkstra(grid);
    }

    @Test
    public void testRun() {
        Node s = new Node(1, 7, grid);
        Node t = new Node(47, 46, grid);
        dijkstra.run(s);
        assertEquals("62.1543", dijkstra.getRoundedCost(t));
    }

    @Test
    public void testRun2() {
        Node s = new Node(1, 42, grid);
        Node t = new Node(44, 5, grid);
        dijkstra.run(s);
        assertEquals("58.3259", dijkstra.getRoundedCost(t));
    }

    @Test
    public void testRun3() {
        Node s = new Node(1, 12, grid);
        Node t = new Node(14, 12, grid);
        dijkstra.run(s);
        assertEquals("13", dijkstra.getRoundedCost(t));
    }

    @Test
    public void testRun4() {
        Node s = new Node(1, 42, grid);
        Node t = new Node(4, 43, grid);
        dijkstra.run(s);
        assertEquals("3.41421", dijkstra.getRoundedCost(t));
    }

    @Test
    public void testRun5() {
        grid = new Grid("dao", "ost100d");
        dijkstra = new Dijkstra(grid);
        Node s = new Node(753, 420, grid);
        Node t = new Node(137, 561, grid);
        dijkstra.run(s);
        assertEquals("1122.11", dijkstra.getRoundedCost(t));
    }

    @Test
    public void testprintShortestPath() {
        grid = new Grid("dao", "lak110d");
        dijkstra = new Dijkstra(grid);
        Node s = new Node(26, 15, grid);
        Node t = new Node(3, 11, grid);
        dijkstra.run(s);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        dijkstra.printShortestPath(t);
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
        assertTrue(out.contains("@TT.X.......................TT"));
        assertTrue(out.contains("@TT..XXXXXXXXXXXXXXX........TT"));
        assertTrue(out.contains("@TT.............TTT.XXXXX...TT"));
        assertTrue(out.contains("TTTT.....TTTTTTTTTTTTTTT.XXTTT"));
        assertTrue(out.contains("TTTTTTTTTTTTTTTTTTTTTTTT...TTT"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
        assertTrue(out.contains("@TTTTTTT@@@@@@@@@@@@@@@@@@@@@@"));
    }
}
