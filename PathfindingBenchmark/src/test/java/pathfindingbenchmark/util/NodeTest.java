/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.util;

import static org.junit.Assert.assertEquals;
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
        node1 = new Node(1, 2, 3);
        node2 = new Node(2, 4, 2);
        node3 = new Node(2, 6, 3);
    }

    @Test
    public void testNode() {
        assertEquals(1, node1.getIdx());

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
}