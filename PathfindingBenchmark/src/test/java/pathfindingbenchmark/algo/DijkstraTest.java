/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algo;

import static org.junit.Assert.assertEquals;
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
    private Node s;
    private Node t;

    @Before
    public void setUp() {
        grid = new Grid("dao", "arena");
        dijkstra = new Dijkstra(grid);
    }

    @Test
    public void testRun() {
        s = new Node(1, 7, grid);
        t = new Node(47, 46, grid);
        dijkstra.run(s, t);
        assertEquals("62.1543", dijkstra.getRoundedCost());
    }

    @Test
    public void testRun2() {
        s = new Node(1, 42, grid);
        t = new Node(44, 5, grid);
        dijkstra.run(s, t);
        assertEquals("58.3259", dijkstra.getRoundedCost());
    }

    @Test
    public void testRun3() {
        s = new Node(1, 12, grid);
        t = new Node(14, 12, grid);
        dijkstra.run(s, t);
        assertEquals("13", dijkstra.getRoundedCost());
    }

    @Test
    public void testRun4() {
        s = new Node(1, 42, grid);
        t = new Node(4, 43, grid);
        dijkstra.run(s, t);
        assertEquals("3.41421", dijkstra.getRoundedCost());
    }

    @Test
    public void testRun5() {
        grid = new Grid("dao", "ost100d");
        s = new Node(753, 420, grid);
        t = new Node(137, 561, grid);
        dijkstra = new Dijkstra(grid);
        dijkstra.run(s, t);
        assertEquals("1122.11", dijkstra.getRoundedCost());
    }

    @Test
    public void testRun6() {
        grid = new Grid("local", "empty_64");
        s = new Node(0, 0, grid);
        t = new Node(63, 63, grid);
        dijkstra = new Dijkstra(grid);
        dijkstra.run(s, t);
        assertEquals("89.0955", dijkstra.getRoundedCost());
    }
}
