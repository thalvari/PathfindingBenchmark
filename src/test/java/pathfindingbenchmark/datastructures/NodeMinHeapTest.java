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
import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class NodeMinHeapTest {

    private NodeMinHeap heap;

    @Before
    public void setUp() {
        heap = new NodeMinHeap();
    }

    @Test
    public void testNodeMinHeap() {
        assertTrue(heap.empty());
    }

    @Test
    public void testInsertDelMin1() {
        Node node = new Node(1, 0, ' ');
        heap.insert(node);
        node = heap.delMin();
        Assert.assertEquals(1, node.getX());
    }

    @Test
    public void testInsertDelMin2() {
        insertNode(1, 1, 2);
        insertNode(2, 1, 2);
        insertNode(3, 1, 2);
        int[] expected = {1, 3, 2};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testInsertDelMin3() {
        insertNode(1, 1, 2);
        insertNode(2, 2, 1);
        insertNode(3, 1, 3);
        insertNode(4, 3, 1);
        int[] expected = {2, 1, 4, 3};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testInsertDelMin4() {
        insertNode(1, 1, 3);
        insertNode(2, 2, 3);
        insertNode(3, 1, 1);
        insertNode(4, 2, 4);
        insertNode(5, 2, 1);
        insertNode(6, 1, 2);
        insertNode(7, 3, 1);
        insertNode(8, 8, 8);
        insertNode(9, 4, 2);
        int[] expected = {3, 5, 6, 7, 1, 2, 9, 4, 8};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testInsertDelMin5() {
        insertNode(1, 1, 1);
        insertNode(2, 1, 1);
        insertNode(3, 1, 1);
        insertNode(4, 1, 1);
        heap.delMin();
        int[] expected = {4, 3, 2};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testDecKey1() {
        insertNode(1, 1, 3);
        insertNode(2, 2, 3);
        insertNode(3, 1, 1);
        insertNode(4, 2, 4);
        insertNode(5, 2, 1);
        insertNode(6, 1, 2);
        insertNode(7, 3, 1);
        insertNode(8, 8, 8);
        Node node = new Node(9, 0, ' ');
        node.setDist(4);
        node.setHeuristic(2);
        heap.insert(node);
        heap.decKey(node, 1);
        int[] expected = {3, 5, 9, 6, 7, 1, 2, 4, 8};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testDecKey2() {
        Node node1 = new Node(1, 0, ' ');
        node1.setDist(1);
        heap.insert(node1);
        Node node2 = new Node(2, 0, ' ');
        node2.setDist(2);
        heap.insert(node2);
        heap.decKey(node2, 0);
        Assert.assertEquals(2, heap.delMin().getX());
        Assert.assertEquals(1, heap.delMin().getX());
    }

    @Test
    public void testDecKey3() {
        Node node1 = new Node(1, 0, ' ');
        node1.setDist(1);
        heap.insert(node1);
        Node node2 = new Node(2, 0, ' ');
        node2.setDist(2);
        heap.insert(node2);
        heap.decKey(node2, 1);
        Assert.assertEquals(1, heap.delMin().getX());
        Assert.assertEquals(2, heap.delMin().getX());
    }

    private void insertNode(int x, int dist, int heuristic) {
        Node node = new Node(x, 0, ' ');
        node.setDist(dist);
        node.setHeuristic(heuristic);
        heap.insert(node);
    }

    @Test
    public void testGetMaxHeapSize() {
        insertNode(1, 1, 1);
        insertNode(2, 1, 1);
        heap.delMin();
        insertNode(3, 1, 1);
        insertNode(4, 1, 1);
        heap.delMin();
        heap.delMin();
        Assert.assertEquals(3, heap.getMaxHeapSize());
    }
}
