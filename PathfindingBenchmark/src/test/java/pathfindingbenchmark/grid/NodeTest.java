/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class NodeTest {

    private static final double DELTA = 0.00001;
    private Grid grid;

    @Before
    public void setUp() {
        grid = new Grid("dao", "arena");
    }

    @Test
    public void testNode() {
        Node node = new Node(1, 0, 2, 0.0);
        assertEquals(1, node.getX());
        assertEquals(0, node.getY());
        assertEquals(2, node.getIdx().intValue());
        assertEquals(0.0, node.getCost(), DELTA);
    }

    @Test
    public void testNode2() {
        Node node = new Node(2, 1, grid);
        assertEquals(2, node.getX());
        assertEquals(1, node.getY());
        assertEquals(52, node.getIdx().intValue());
    }

    @Test
    public void testNode3() {
        Node node = new Node(49, 1.0, grid);
        assertEquals(48, node.getX());
        assertEquals(0, node.getY());
        assertEquals(49, node.getIdx().intValue());
        assertEquals(1.0, node.getCost(), DELTA);

    }
}
