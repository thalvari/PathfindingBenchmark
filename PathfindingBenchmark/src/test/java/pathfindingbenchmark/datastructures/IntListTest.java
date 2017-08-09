/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
    public void testContains() {
        list.add(2);
        assertTrue(list.contains(2));
    }

    @Test
    public void testContains2() {
        list.add(2);
        assertFalse(list.contains(1));
    }
}
