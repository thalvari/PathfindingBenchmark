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

    private static final double DELTA = 0.01;
    private Grid grid;

    @Before
    public void setUp() {
        grid = new Grid("dao", "arena");
    }

    @Test
    public void testNode() {
        Node u = new Node(0, 1, 2, 3, grid);
        assertEquals(0, u.getX());
        assertEquals(1, u.getY());
        assertEquals(2, u.getCost(), DELTA);
        assertEquals(3, u.getH(), DELTA);
    }

    @Test
    public void testGetIdx() {
        Node u = new Node(1, 0, grid);
        assertEquals(2, u.getIdx());
    }
}
