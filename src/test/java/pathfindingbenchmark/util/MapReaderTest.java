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
import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class MapReaderTest {

    private MapReader reader;

    @Before
    public void setUp() {
        reader = new MapReader("empty_4");
    }

    @Test
    public void testMapNames() {
        assertTrue(MapReader.MAP_NAMES.contains("empty_4"));
    }

    @Test
    public void testMapReader() {
        assertEquals(4, reader.getHeight());
        assertEquals(4, reader.getWidth());
    }

    @Test
    public void testInitNodes() {
        Node[][] nodes = reader.initNodes();
        for (int y = 0; y < reader.getHeight(); y++) {
            for (int x = 0; x < reader.getWidth(); x++) {
                assertEquals(x, nodes[y][x].getX());
                assertEquals(y, nodes[y][x].getY());
                assertEquals('.', nodes[y][x].getSymbol());
            }
        }
    }

    @Test
    public void testGetOrigSymbol() {
        assertEquals('.', reader.getOrigSymbol(0, 0));
        assertEquals('.', reader.getOrigSymbol(3, 3));
    }

    @Test
    public void testPassableNodeCount() {
        reader.initNodes();
        assertEquals(16, reader.getPassableNodeCount());
    }
}
