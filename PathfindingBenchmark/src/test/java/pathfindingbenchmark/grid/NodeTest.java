/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class NodeTest {

    private static final double DELTA = 0.01;
    private Node u;

    @Test
    public void testNode() {
        u = new Node(1, Math.sqrt(2));
        assertEquals(1, u.getIdx());
        assertEquals(Math.sqrt(2), u.getPriority(), DELTA);
    }

    @Test
    public void testNode2() {
        u = new Node(2);
        assertEquals(2, u.getIdx());
        assertEquals(0, u.getPriority(), DELTA);
    }
}
