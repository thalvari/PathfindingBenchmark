/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.datastructures;

import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import pathfindingbenchmark.util.Node;

/**
 *
 * @author thalvari
 */
public class NodeMinHeapTest {

    private NodeMinHeap heap;

    @Before
    public void setUp() {
        heap = new NodeMinHeap(10);
    }

    @Test
    public void testNodeMinHeap() {
        assertTrue(heap.empty());
    }

    @Test
    public void testInsertDelMin1() {
        heap.insert(new Node(1, 0, 0));
        Node u = heap.delMin();
        Assert.assertEquals(1, u.getIdx());
    }

    @Test
    public void testInsertDelMin2() {
        heap.insert(new Node(1, 1, 2));
        heap.insert(new Node(2, 1, 2));
        heap.insert(new Node(3, 1, 2));
        int[] expected = {1, 3, 2};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getIdx());
        }
    }

    @Test
    public void testInsertDelMin3() {
        heap.insert(new Node(1, 1, 2));
        heap.insert(new Node(2, 2, 1));
        heap.insert(new Node(3, 1, 3));
        heap.insert(new Node(4, 3, 1));
        int[] expected = {2, 1, 4, 3};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getIdx());
        }
    }

    @Test
    public void testInsertDelMin4() {
        heap.insert(new Node(1, 1, 3));
        heap.insert(new Node(2, 2, 3));
        heap.insert(new Node(3, 1, 1));
        heap.insert(new Node(4, 2, 4));
        heap.insert(new Node(5, 2, 1));
        heap.insert(new Node(6, 1, 2));
        heap.insert(new Node(7, 3, 1));
        heap.insert(new Node(8, 8, 8));
        heap.insert(new Node(9, 4, 2));
        int[] expected = {3, 5, 6, 7, 1, 2, 9, 4, 8};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getIdx());
        }
    }

    @Test
    public void testInsertDelMin5() {
        heap.insert(new Node(1, 1, 1));
        heap.insert(new Node(2, 1, 1));
        heap.insert(new Node(3, 1, 1));
        heap.insert(new Node(4, 1, 1));
        heap.delMin();
        int[] expected = {4, 3, 2};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getIdx());
        }
    }

    @Test
    public void testDecKey() {
        heap.insert(new Node(1, 1, 3));
        heap.insert(new Node(2, 2, 3));
        heap.insert(new Node(3, 1, 1));
        heap.insert(new Node(4, 2, 4));
        heap.insert(new Node(5, 2, 1));
        heap.insert(new Node(6, 1, 2));
        heap.insert(new Node(7, 3, 1));
        heap.insert(new Node(8, 8, 8));
        heap.insert(new Node(9, 4, 2));
        heap.decKey(9, 1);
        int[] expected = {3, 5, 9, 6, 7, 1, 2, 4, 8};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getIdx());
        }
    }
}
