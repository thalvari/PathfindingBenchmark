/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class IntListTest {

    private IntList list;

    @Before
    public void setUp() {
        list = new IntList();
    }

    @Test
    public void testIntList() {
        assertEquals(0, list.size());
    }

    @Test
    public void testAdd() {
        list.add(2);
        assertEquals(1, list.size());
        assertEquals(2, list.get(0));
    }

    @Test
    public void testAdd2() {
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }

        assertEquals(9, list.size());
        assertEquals(9, list.get(8));
    }
}
