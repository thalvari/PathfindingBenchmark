/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class GridTest {

    private Grid grid;

    @Before
    public void setUp() {
        grid = new Grid("dao", "arena");
    }

    @Test
    public void testGrid() {
        assertNotNull(grid.getAdjList(new Node(0, 0, grid)));
        assertEquals(2401, grid.getN());
    }

    @Test
    public void testGrid2() {
        grid = new Grid("?", "?");
        assertNull(grid.getAdjList(new Node(0, 0, grid)));
        assertEquals(0, grid.getN());
    }

    @Test
    public void testGetX() {
        assertEquals(0, grid.getX(1));
    }

    @Test
    public void testGetX2() {
        assertEquals(0, grid.getX(50));
    }

    @Test
    public void testGetY() {
        assertEquals(0, grid.getY(1));
    }

    @Test
    public void testGetY2() {
        assertEquals(1, grid.getY(50));
    }

    @Test
    public void testGetAdjList() {
        List<Node> adjList = grid.getAdjList(new Node(5, 0, grid));
        assertEquals(0, adjList.size());
    }

    @Test
    public void testGetAdjList2() {
        List<Node> adjList = grid.getAdjList(new Node(3, 1, grid));
        assertEquals(3, adjList.size());
    }

    @Test
    public void testGetAdjList3() {
        List<Node> adjList = grid.getAdjList(new Node(19, 1, grid));
        assertEquals(1, adjList.size());
    }

    @Test
    public void testGetAdjList4() {
        List<Node> adjList = grid.getAdjList(new Node(5, 5, grid));
        assertTrue(adjList.contains(new Node(4, 5, grid)));
        assertTrue(adjList.contains(new Node(6, 5, grid)));
        assertTrue(adjList.contains(new Node(5, 4, grid)));
        assertTrue(adjList.contains(new Node(5, 6, grid)));
        assertTrue(adjList.contains(new Node(4, 4, grid)));
        assertTrue(adjList.contains(new Node(4, 6, grid)));
        assertTrue(adjList.contains(new Node(6, 4, grid)));
        assertTrue(adjList.contains(new Node(6, 6, grid)));
    }
}
