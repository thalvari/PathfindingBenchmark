/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class MyListTest {

    private MyList<Node> list;

    @Before
    public void setUp() {
        list = new MyList<>();
    }

    @Test
    public void testNodeList() {
        assertEquals(0, list.size());
    }

    @Test
    public void testAdd1() {
        list.add(new Node(0, 1, ' '));
        assertEquals(1, list.size());
        assertEquals(0, list.get(0).getX());
        assertEquals(1, list.get(0).getY());
        assertEquals(' ', list.get(0).getSymbol());
    }

    @Test
    public void testAdd2() {
        for (int i = 1; i <= 9; i++) {
            list.add(new Node(i, 0, ' '));
        }

        assertEquals(9, list.size());
        assertEquals(1, list.get(0).getX());
        assertEquals(9, list.get(8).getX());
    }
}
