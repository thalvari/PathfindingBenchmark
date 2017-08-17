/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import static org.junit.Assert.assertEquals;
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

    private Grid grid1;
    private Grid grid2;

    @Before
    public void setUp() {
        grid1 = new Grid("arena");
        grid2 = new Grid("empty_4");
    }

    @Test
    public void testGrid1() {
        assertEquals(2401, grid1.getSize());
    }

    @Test
    public void testGrid2() {
        Grid grid = new Grid("?");
        assertEquals(0, grid.getSize());
    }

    @Test
    public void testCreateAdjList1() {
        assertNull(grid1.createAdjList(grid1.getIdx(5, 0)));
    }

    @Test
    public void testCreateAdjList2() {
        assertEquals(3, grid1.createAdjList(grid1.getIdx(3, 1)).size());
    }

    @Test
    public void testCreateAdjList3() {
        assertEquals(1, grid1.createAdjList(grid1.getIdx(19, 1)).size());
    }

    @Test
    public void testCreateAdjList4() {
        assertEquals(8, grid1.createAdjList(grid1.getIdx(5, 5)).size());
    }

    @Test
    public void testCreateAdjList5() {
        IntList adjList = grid1.createAdjList(grid1.getIdx(14, 3));
        assertTrue(adjList.get(0) == grid1.getIdx(13, 2));
        assertTrue(adjList.get(1) == grid1.getIdx(14, 2));
        assertTrue(adjList.get(2) == grid1.getIdx(13, 3));
        assertTrue(adjList.get(3) == grid1.getIdx(15, 3));
        assertTrue(adjList.get(4) == grid1.getIdx(13, 4));
        assertTrue(adjList.get(5) == grid1.getIdx(14, 4));
        assertTrue(adjList.get(6) == grid1.getIdx(15, 4));
    }

    @Test
    public void testCreateAdjList6() {
        assertEquals(3, grid2.createAdjList(grid2.getIdx(0, 0)).size());
    }

    @Test
    public void testCreateAdjList7() {
        assertEquals(3, grid2.createAdjList(grid2.getIdx(3, 3)).size());
    }

    @Test
    public void testGetPassableNodeCount() {
        assertEquals(2054, grid1.getPassableNodeCount());
    }
}
