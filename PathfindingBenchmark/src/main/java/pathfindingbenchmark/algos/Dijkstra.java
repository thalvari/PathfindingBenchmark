/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algos;

import java.util.PriorityQueue;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;

/**
 *
 * @author thalvari
 */
public class Dijkstra {

    private final Grid grid;
    private final double cost[];
    private final Node path[];
    private final boolean visited[];

    public Dijkstra(Grid grid) {
        this.grid = grid;
        cost = new double[grid.getN() + 1];
        path = new Node[grid.getN() + 1];
        visited = new boolean[grid.getN() + 1];
    }

    public double run(Node s, Node t) {
        init(s);
        PriorityQueue<Node> heap = new PriorityQueue();
        heap.add(new Node(s.getIdx(), 0.0, grid));
        while (!heap.isEmpty()) {
            Node u = heap.poll();
            if (visited[u.getIdx()]) {
                continue;
            }

            visited[u.getIdx()] = true;
            for (Node v : grid.getAdjList(u)) {
                if (!visited[v.getIdx()]) {
                    relax(u, v);
                    heap.add(new Node(v.getIdx(), cost[v.getIdx()], grid));
                }
            }
        }

        return cost[t.getIdx()];
    }

    private void init(Node s) {
        for (int i = 1; i <= grid.getN(); i++) {
            visited[i] = false;
            cost[i] = Double.MAX_VALUE;
            path[i] = null;
        }

        cost[s.getIdx()] = 0;
    }

    private void relax(Node u, Node v) {
        if (cost[v.getIdx()] > cost[u.getIdx()] + v.getCost()) {
            cost[v.getIdx()] = cost[u.getIdx()] + v.getCost();
            path[v.getIdx()] = u;
        }
    }

    public void printShortestPath(Node s, Node t) {
        String[][] markedMapData = grid.cloneMapData();
        markedMapData[t.getY()][t.getX()] = "X";
        Node u = path[t.getIdx()];
        while (u != null) {
            markedMapData[u.getY()][u.getX()] = "X";
            u = path[u.getIdx()];
        }

        markedMapData[s.getY()][s.getX()] = "X";
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                System.out.print(markedMapData[y][x]);
            }
            System.out.println();
        }
    }
}
