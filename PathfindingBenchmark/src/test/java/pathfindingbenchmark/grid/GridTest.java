/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.datastructures.IntList;

/**
 *
 * @author thalvari
 */
public class GridTest {

    private Grid grid;

    @Before
    public void setUp() {
        grid = new Grid("arena");
    }

    @Test
    public void testGrid() {
        assertNotNull(grid.getAdjList(grid.getIdx(0, 0)));
        assertEquals(2401, grid.getN());
    }

    @Test
    public void testGrid2() {
        grid = new Grid("?");
        assertNull(grid.getAdjList(grid.getIdx(0, 0)));
        assertEquals(0, grid.getN());
    }

    @Test
    public void testGetAdjList() {
        assertEquals(0, grid.getAdjList(grid.getIdx(5, 0)).size());
    }

    @Test
    public void testGetAdjList2() {
        assertEquals(3, grid.getAdjList(grid.getIdx(3, 1)).size());
    }

    @Test
    public void testGetAdjList3() {
        assertEquals(1, grid.getAdjList(grid.getIdx(19, 1)).size());
    }

    @Test
    public void testGetAdjList4() {
        assertEquals(8, grid.getAdjList(grid.getIdx(5, 5)).size());
    }

    @Test
    public void testGetAdjList5() {
        IntList adjList = grid.getAdjList(grid.getIdx(14, 3));
        assertTrue(adjList.contains(grid.getIdx(13, 3)));
        assertTrue(adjList.contains(grid.getIdx(15, 3)));
        assertTrue(adjList.contains(grid.getIdx(14, 2)));
        assertTrue(adjList.contains(grid.getIdx(14, 4)));
        assertTrue(adjList.contains(grid.getIdx(13, 2)));
        assertTrue(adjList.contains(grid.getIdx(13, 4)));
        assertTrue(adjList.contains(grid.getIdx(15, 4)));
    }

    @Test
    public void testGetAdjList6() {
        grid = new Grid("empty_64");
        assertEquals(3, grid.getAdjList(grid.getIdx(0, 0)).size());
    }

    @Test
    public void testGetAdjList7() {
        grid = new Grid("empty_64");
        assertEquals(3, grid.getAdjList(grid.getIdx(63, 63)).size());
    }
}
