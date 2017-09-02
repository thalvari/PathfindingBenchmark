package pathfindingbenchmark.wrapper;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.algorithms.AStar;
import pathfindingbenchmark.algorithms.Dijkstra;
import pathfindingbenchmark.algorithms.JPS;

/**
 *
 * @author thalvari
 */
public class WrapperTest {

    private Wrapper wrapper1;
    private Wrapper wrapper2;

    @Before
    public void setUp() {
        wrapper1 = new Wrapper();
        wrapper1.setGrid("empty_4");
        wrapper1.setAlgo("Dijkstra");
        wrapper2 = new Wrapper();
        wrapper2.setGrid("test");
        wrapper2.setAlgo("Dijkstra");
    }

    @Test
    public void testSetGrid() {
        Wrapper wrapper = new Wrapper();
        wrapper.setGrid("lak110d");
        assertNull(wrapper.getAlgo());
        assertEquals(21, wrapper.getGrid().getHeight());
        assertEquals(30, wrapper.getGrid().getWidth());
    }

    @Test
    public void testSetAlgo() {
        assertTrue(wrapper1.getAlgo() instanceof Dijkstra);
        wrapper1.setAlgo("AStar");
        assertTrue(wrapper1.getAlgo() instanceof AStar);
        wrapper1.setAlgo("JPS");
        assertTrue(wrapper1.getAlgo() instanceof JPS);
    }

    @Test
    public void testGetMapAsWritebleImage1() {
        wrapper2.setGrid("test");
        WritableImage mapAsWritableImage = wrapper2.getMapAsWritableImage();
        PixelReader pixelReader = mapAsWritableImage.getPixelReader();
        int width = Wrapper.IMAGE_SCALE_FACTOR * wrapper2.getGrid().getWidth();
        for (int x = 0; x < width; x++) {
            switch (x / Wrapper.IMAGE_SCALE_FACTOR) {
                case 0:
                case 1:
                    assertEquals(Color.BLACK, pixelReader.getColor(x, 0));
                    break;
                case 2:
                case 3:
                case 4:
                    assertEquals(Color.GREY, pixelReader.getColor(x, 0));
                    break;
                case 5:
                    assertEquals(Color.GREEN, pixelReader.getColor(x, 0));
                    break;
                case 6:
                    assertEquals(Color.PURPLE, pixelReader.getColor(x, 0));
                    break;
                default:
                    assertEquals(Color.BLUE, pixelReader.getColor(x, 0));
                    break;
            }
        }
    }

    @Test
    public void testGetMapAsWritebleImage2() {
        wrapper1.runAlgo(0, 0, 3, 3);
        WritableImage mapAsWritableImage = wrapper1.getMapAsWritableImage();
        PixelReader pixelReader = mapAsWritableImage.getPixelReader();
        int height = Wrapper.IMAGE_SCALE_FACTOR * wrapper1.getGrid()
                .getHeight();

        int width = Wrapper.IMAGE_SCALE_FACTOR * wrapper1.getGrid().getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x / Wrapper.IMAGE_SCALE_FACTOR
                        == y / Wrapper.IMAGE_SCALE_FACTOR) {

                    assertEquals(Color.RED, pixelReader.getColor(x, y));
                } else {
                    assertEquals(Color.YELLOW, pixelReader.getColor(x, y));
                }
            }
        }
    }

    @Test
    public void testCheckCoordinates1() {
        Wrapper wrapper = new Wrapper();
        assertFalse(wrapper.checkStartGoal(2, 0, 3, 0));
    }

    @Test
    public void testCheckCoordinates2() {
        assertFalse(wrapper2.checkStartGoal(-1, 0, 3, 0));
    }

    @Test
    public void testCheckCoordinates3() {
        assertFalse(wrapper2.checkStartGoal(2, 0, -1, 0));
    }

    @Test
    public void testCheckCoordinates4() {
        assertFalse(wrapper2.checkStartGoal(0, 0, 3, 0));
    }

    @Test
    public void testCheckCoordinates5() {
        assertFalse(wrapper2.checkStartGoal(2, 0, 1, 0));
    }

    @Test
    public void testCheckCoordinates6() {
        assertTrue(wrapper2.checkStartGoal(2, 0, 4, 0));
    }

    @Test
    public void testGetMinDist1() {
        wrapper1.runAlgo(0, 0, 3, 3);
        assertEquals("4.243", wrapper1.getPathLen());
    }

    @Test
    public void testGetMinDist2() {
        wrapper2.runAlgo(2, 0, 6, 0);
        assertEquals("inf", wrapper2.getPathLen());
    }

    @Test
    public void testGetAvgSuccListSize1() {
        wrapper2.runAlgo(2, 0, 4, 0);
        assertEquals("1.500", wrapper2.getAvgSuccListSize());
    }

    @Test
    public void testGetAvgSuccListSize2() {
        wrapper2.runAlgo(2, 0, 6, 0);
        assertEquals("1.333", wrapper2.getAvgSuccListSize());
    }

    @Test
    public void testGetAvgSuccListSize3() {
        wrapper2.runAlgo(2, 0, 2, 0);
        assertEquals("0.000", wrapper2.getAvgSuccListSize());
    }

    @Test
    public void testGetCpuTime() {
        wrapper1.runAlgo(0, 0, 3, 3);
        assertNotEquals(-1, wrapper2.getCpuTime().indexOf("ms"));
    }

    @Test
    public void testGetUsedMemory() {
        wrapper1.runAlgo(0, 0, 3, 3);
        assertNotEquals(-1, wrapper2.getUsedMemory().indexOf("MB"));
    }

    @Test
    public void testGetClosedNodePercentage() {
        wrapper2.runAlgo(2, 0, 4, 0);
        assertEquals("75.000 %", wrapper2.getClosedNodePercentage());
    }
}
