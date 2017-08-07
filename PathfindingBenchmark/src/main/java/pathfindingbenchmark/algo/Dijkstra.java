/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algo;

import java.util.PriorityQueue;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 * Dijkstran algoritmin toteutus.
 *
 * @author thalvari
 */
public class Dijkstra extends Algo {

    /**
     * Konstruktori käyttää yläluokan konstruktoria.
     *
     * @param grid Verkko.
     */
    public Dijkstra(Grid grid) {
        super(grid);
    }

    @Override
    public void run(Node s, Node t) {
        init(s, t);
        PriorityQueue<Node> heap = new PriorityQueue<>();
        heap.add(new Node(s.getX(), s.getY(), 0, grid));
        while (!heap.isEmpty()) {
            Node u = heap.poll();
            if (visited[u.getIdx()]) {
                continue;
            }

            visited[u.getIdx()] = true;
            for (Node v : grid.getAdjList(u)) {
                if (!visited[v.getIdx()]) {
                    relax(u, v);
                    heap.add(new Node(v.getX(),
                            v.getY(),
                            cost[v.getIdx()],
                            grid));
                }
            }
        }
    }
}
