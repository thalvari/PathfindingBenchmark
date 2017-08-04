/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

/**
 *
 * @author thalvari
 */
public class Node implements Comparable<Node> {

    private final int x;
    private final int y;
    private final Integer idx;
    private Double cost;

    public Node(int x, int y, int idx, double cost) {
        this.x = x;
        this.y = y;
        this.idx = idx;
        this.cost = cost;
    }

    public Node(int x, int y, Grid grid) {
        this(x, y, grid.getIdx(x, y), 0);
    }

    public Node(int idx, double cost, Grid grid) {
        this(grid.getX(idx), grid.getY(idx), idx, cost);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Integer getIdx() {
        return idx;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(Node o) {
        if (cost.equals(o.cost)) {
            return getIdx().compareTo(o.getIdx());
        } else {
            return cost.compareTo(o.cost);
        }
    }
}
