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

    @Test
    public void testNode() {
        Node u = new Node(1, 2, 3);
        assertEquals(1, u.getIdx());
    }
}
