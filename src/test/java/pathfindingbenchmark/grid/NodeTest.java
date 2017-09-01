/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class NodeTest {

    private Node node1;
    private Node node2;
    private Node node3;

    @Before
    public void setUp() {
        node1 = new Node(0, 0, '.');
        node1.setDist(2);
        node1.setHeuristic(3);
        node1.setHeapIdx(1);
        node2 = new Node(1, 1, 'O');
        node2.setDist(4);
        node2.setHeuristic(2);
        node3 = new Node(1, 1, 'W');
        node3.setDist(6);
        node3.setHeuristic(3);
        node1.setPrev(node2);
    }

    @Test
    public void testNode() {
        assertEquals(0, node1.getX());
        assertEquals(0, node1.getY());
        assertEquals(2, node1.getDist());
        assertEquals(1, node1.getHeapIdx());
        assertEquals('.', node1.getSymbol());
        assertTrue(node1.getPrev().equals(node2));
    }

    @Test
    public void testCompareTo1() {
        assertTrue(node1.compareTo(node2) < 0);
    }

    @Test
    public void testCompareTo2() {
        node2.setDist(3);
        assertTrue(node1.compareTo(node2) > 0);
    }

    @Test
    public void testCompareTo3() {
        node3.setDist(2);
        assertTrue(node1.compareTo(node3) == 0);
    }

    @Test
    public void testEquals() {
        assertFalse(node1.equals(node3));
        assertTrue(node2.equals(node3));
    }

    @Test
    public void testIsPassable() {
        assertTrue(node1.isPassable());
        assertFalse(node2.isPassable());
        assertFalse(node3.isPassable());
    }

    @Test
    public void testSetClosed() {
        node1.setClosed();
        assertTrue(node1.isClosed());
    }

    @Test
    public void testSetPath() {
        node1.setPath();
        assertEquals('P', node1.getSymbol());
    }

    @Test
    public void testInit() {
        node1.setClosed();
        node1.init('.');
        assertEquals(Integer.MAX_VALUE, node1.getDist());
        assertEquals(0, node1.getHeapIdx());
        assertFalse(node1.isClosed());
        assertNull(node1.getPrev());
    }
}
