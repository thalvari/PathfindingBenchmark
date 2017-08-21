/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.util;

import pathfindingbenchmark.grid.Node;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class DirectionTest {

    private Grid grid;
    private Direction dir1;
    private Direction dir2;
    private Direction dir3;

    @Before
    public void setUp() {
        grid = new Grid("empty_4");
        dir1 = new Direction(new Node(0, 0, ' '), new Node(1, 0, ' '));
        dir2 = new Direction(0, -1);
        dir3 = new Direction(1, -1);
    }

    @Test
    public void testDirection() {
        assertEquals(1, dir1.getX());
        assertEquals(0, dir1.getY());
    }

    @Test
    public void testIsHor() {
        assertTrue(dir1.isHor());
        assertFalse(dir1.isDiag());
        assertFalse(dir1.isVer());
    }

    @Test
    public void testIsVer() {
        assertTrue(dir2.isVer());
        assertFalse(dir2.isDiag());
        assertFalse(dir2.isHor());
    }

    @Test
    public void testIsDiag() {
        assertTrue(dir3.isDiag());
        assertFalse(dir3.isHor());
        assertFalse(dir3.isVer());
    }
}
