/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import java.util.ArrayList;
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
    public void testGetAdjList() {
        assertEquals(0, grid.getAdjList(new Node(5, 0, grid)).size());
    }

    @Test
    public void testGetAdjList2() {
        assertEquals(3, grid.getAdjList(new Node(3, 1, grid)).size());
    }

    @Test
    public void testGetAdjList3() {
        assertEquals(1, grid.getAdjList(new Node(19, 1, grid)).size());
    }

    @Test
    public void testGetAdjList4() {
        assertEquals(8, grid.getAdjList(new Node(5, 5, grid)).size());
    }

    @Test
    public void testGetAdjList5() {
        List<Node> adjList = grid.getAdjList(new Node(14, 3, grid));
        List<Integer> adjListIdxs = new ArrayList();
        for (Node u : adjList) {
            adjListIdxs.add(u.getIdx());
        }

        assertTrue(adjListIdxs.contains(grid.getIdx(13, 3)));
        assertTrue(adjListIdxs.contains(grid.getIdx(15, 3)));
        assertTrue(adjListIdxs.contains(grid.getIdx(14, 2)));
        assertTrue(adjListIdxs.contains(grid.getIdx(14, 4)));
        assertTrue(adjListIdxs.contains(grid.getIdx(13, 2)));
        assertTrue(adjListIdxs.contains(grid.getIdx(13, 4)));
        assertTrue(adjListIdxs.contains(grid.getIdx(15, 4)));
    }

    @Test
    public void testGetAdjList6() {
        grid = new Grid("local", "empty_64");
        assertEquals(3, grid.getAdjList(new Node(0, 0, grid)).size());
    }

    @Test
    public void testGetAdjList7() {
        grid = new Grid("local", "empty_64");
        assertEquals(3, grid.getAdjList(new Node(63, 63, grid)).size());
    }
}
