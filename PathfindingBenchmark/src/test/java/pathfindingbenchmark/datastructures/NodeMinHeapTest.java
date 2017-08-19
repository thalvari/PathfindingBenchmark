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
        Node node1 = new Node(1, 0, ' ');
        Node node2 = new Node(2, 0, ' ');
        Node node3 = new Node(3, 0, ' ');
        node1.setDist(1);
        node1.setHeuristic(2);
        node2.setDist(1);
        node2.setHeuristic(2);
        node3.setDist(1);
        node3.setHeuristic(2);
        heap.insert(node1);
        heap.insert(node2);
        heap.insert(node3);
        int[] expected = {1, 3, 2};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testInsertDelMin3() {
        Node node1 = new Node(1, 0, ' ');
        Node node2 = new Node(2, 0, ' ');
        Node node3 = new Node(3, 0, ' ');
        Node node4 = new Node(4, 0, ' ');
        node1.setDist(1);
        node1.setHeuristic(2);
        node2.setDist(2);
        node2.setHeuristic(1);
        node3.setDist(1);
        node3.setHeuristic(3);
        node4.setDist(3);
        node4.setHeuristic(1);
        heap.insert(node1);
        heap.insert(node2);
        heap.insert(node3);
        heap.insert(node4);
        int[] expected = {2, 1, 4, 3};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testInsertDelMin4() {
        Node node1 = new Node(1, 0, ' ');
        Node node2 = new Node(2, 0, ' ');
        Node node3 = new Node(3, 0, ' ');
        Node node4 = new Node(4, 0, ' ');
        Node node5 = new Node(5, 0, ' ');
        Node node6 = new Node(6, 0, ' ');
        Node node7 = new Node(7, 0, ' ');
        Node node8 = new Node(8, 0, ' ');
        Node node9 = new Node(9, 0, ' ');
        node1.setDist(1);
        node1.setHeuristic(3);
        node2.setDist(2);
        node2.setHeuristic(3);
        node3.setDist(1);
        node3.setHeuristic(1);
        node4.setDist(2);
        node4.setHeuristic(4);
        node5.setDist(2);
        node5.setHeuristic(1);
        node6.setDist(1);
        node6.setHeuristic(2);
        node7.setDist(3);
        node7.setHeuristic(1);
        node8.setDist(8);
        node8.setHeuristic(8);
        node9.setDist(4);
        node9.setHeuristic(2);
        heap.insert(node1);
        heap.insert(node2);
        heap.insert(node3);
        heap.insert(node4);
        heap.insert(node5);
        heap.insert(node6);
        heap.insert(node7);
        heap.insert(node8);
        heap.insert(node9);
        int[] expected = {3, 5, 6, 7, 1, 2, 9, 4, 8};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testInsertDelMin5() {
        Node node1 = new Node(1, 0, ' ');
        Node node2 = new Node(2, 0, ' ');
        Node node3 = new Node(3, 0, ' ');
        Node node4 = new Node(4, 0, ' ');
        node1.setDist(1);
        node1.setHeuristic(1);
        node2.setDist(1);
        node2.setHeuristic(1);
        node3.setDist(1);
        node3.setHeuristic(1);
        node4.setDist(1);
        node4.setHeuristic(1);
        heap.insert(node1);
        heap.insert(node2);
        heap.insert(node3);
        heap.insert(node4);
        heap.delMin();
        int[] expected = {4, 3, 2};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }

    @Test
    public void testDecKey() {
        Node node1 = new Node(1, 0, ' ');
        Node node2 = new Node(2, 0, ' ');
        Node node3 = new Node(3, 0, ' ');
        Node node4 = new Node(4, 0, ' ');
        Node node5 = new Node(5, 0, ' ');
        Node node6 = new Node(6, 0, ' ');
        Node node7 = new Node(7, 0, ' ');
        Node node8 = new Node(8, 0, ' ');
        Node node9 = new Node(9, 0, ' ');
        node1.setDist(1);
        node1.setHeuristic(3);
        node2.setDist(2);
        node2.setHeuristic(3);
        node3.setDist(1);
        node3.setHeuristic(1);
        node4.setDist(2);
        node4.setHeuristic(4);
        node5.setDist(2);
        node5.setHeuristic(1);
        node6.setDist(1);
        node6.setHeuristic(2);
        node7.setDist(3);
        node7.setHeuristic(1);
        node8.setDist(8);
        node8.setHeuristic(8);
        node9.setDist(4);
        node9.setHeuristic(2);
        heap.insert(node1);
        heap.insert(node2);
        heap.insert(node3);
        heap.insert(node4);
        heap.insert(node5);
        heap.insert(node6);
        heap.insert(node7);
        heap.insert(node8);
        heap.insert(node9);
        heap.decKey(node9, 1);
        int[] expected = {3, 5, 9, 6, 7, 1, 2, 4, 8};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], heap.delMin().getX());
        }
    }
}
