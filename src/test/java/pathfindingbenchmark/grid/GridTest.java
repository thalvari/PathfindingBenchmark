/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.datastructures.MyList;
import pathfindingbenchmark.util.Direction;

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
        assertEquals(49, grid1.getHeight());
        assertEquals(49, grid1.getWidth());
        assertNotNull(grid1.getNodes());
    }

    @Test
    public void testGrid2() {
        Grid grid = new Grid("?");
        assertEquals(0, grid.getHeight());
        assertEquals(0, grid.getWidth());
        assertNull(grid.getNodes());
        assertFalse(grid.isInBounds(0, 0));
    }

    @Test
    public void testCreateAdjList1() {
        assertEquals(3, grid1.createAdjList(new Node(3, 1, ' ')).size());
    }

    @Test
    public void testCreateAdjList2() {
        assertEquals(1, grid1.createAdjList(new Node(19, 1, ' ')).size());
    }

    @Test
    public void testCreateAdjList3() {
        assertEquals(8, grid1.createAdjList(new Node(5, 5, ' ')).size());
    }

    @Test
    public void testCreateAdjList4() {
        MyList<Node> adjList = grid1.createAdjList(new Node(14, 3, ' '));
        assertTrue(adjList.contains(new Node(13, 2, ' ')));
        assertTrue(adjList.contains(new Node(14, 2, ' ')));
        assertTrue(adjList.contains(new Node(13, 3, ' ')));
        assertTrue(adjList.contains(new Node(15, 3, ' ')));
        assertTrue(adjList.contains(new Node(13, 4, ' ')));
        assertTrue(adjList.contains(new Node(14, 4, ' ')));
        assertTrue(adjList.contains(new Node(15, 4, ' ')));
    }

    @Test
    public void testCreateAdjList5() {
        assertEquals(3, grid2.createAdjList(new Node(0, 0, ' ')).size());
    }

    @Test
    public void testCreateAdjList6() {
        assertEquals(3, grid2.createAdjList(new Node(3, 3, ' ')).size());
    }

    @Test
    public void testIsNodeInDirAdj() {
        Node node = grid1.getNode(1, 3);
        assertTrue(grid1.isNodeInDirAdj(node, new Direction(1, 0)));
        assertFalse(grid1.isNodeInDirAdj(node, new Direction(1, -1)));
        assertTrue(grid1.isNodeInDirAdj(node, new Direction(1, 1)));
    }

    @Test
    public void testIsNodeInDirPassable() {
        Node node = grid1.getNode(1, 3);
        assertTrue(grid1.isNodeInDirPassable(node, new Direction(1, -1)));
        assertFalse(grid1.isNodeInDirPassable(node, new Direction(0, -1)));
    }

    @Test
    public void testNodeDist() {
        Node node1 = grid1.getNode(1, 3);
        Node node2 = grid1.getNode(5, 3);
        Node node3 = grid1.getNode(3, 5);
        assertEquals(4 * Grid.HOR_VER_NODE_DIST,
                grid1.getNodeDist(node1, node2));

        assertEquals(2 * Grid.DIAG_NODE_DIST,
                grid1.getNodeDist(node1, node3));
    }

    @Test
    public void testInitNodes() {
        grid2.getNode(0, 0).setClosed();
        grid2.getNode(3, 3).setClosed();
        grid2.initNodes();
        assertFalse(grid2.getNode(0, 0).isClosed());
        assertFalse(grid2.getNode(3, 3).isClosed());
    }

    @Test
    public void testIsInBounds() {
        assertTrue(grid2.isInBounds(0, 0));
        assertFalse(grid2.isInBounds(-1, 0));
        assertFalse(grid2.isInBounds(0, -1));
        assertTrue(grid2.isInBounds(3, 3));
        assertFalse(grid2.isInBounds(4, 3));
        assertFalse(grid2.isInBounds(3, 4));
    }
}
