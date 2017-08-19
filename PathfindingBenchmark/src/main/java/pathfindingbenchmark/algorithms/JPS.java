/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.datastructures.NodeList;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.util.Direction;
import pathfindingbenchmark.util.Node;

/**
 * JPS-algoritmin toteutus. Algoritmii karsii ensin huonot naapurisolmut ja
 * etsii loppujen tilalle mahdollisen hyppysolmun joka on samassa linjassa
 * solmun ja naapurin kanssa.
 *
 * @author thalvari
 */
public class JPS extends AStarAbstract {

    /**
     * Konstruktori.
     *
     * @param grid Verkko.
     */
    public JPS(Grid grid) {
        super(grid);
    }

    @Override
    protected NodeList getSuccList(Node node) {
        NodeList prunedAdjList = getPrunedAdjList(node);
        NodeList succList = new NodeList();
        for (int i = 0; i < prunedAdjList.size(); i++) {
            Node succ = jump(node, new Direction(node, prunedAdjList.get(i)));
            if (succ != null) {
                succList.add(succ);
            }
        }

        return succList;
    }

    private NodeList getPrunedAdjList(Node node) {
        Node parent = node.getPrev();
        NodeList prunedAdjList = new NodeList();
        if (parent == null) {
            return grid.createAdjList(node);
        }

        Direction dir = new Direction(parent, node);
        if (dir.isDiag()) {
            checkDiagPruningRules(node, dir, prunedAdjList);
        } else if (dir.isHor()) {
            checkHorPruningRules(node, dir, prunedAdjList);
        } else {
            checkVerPruningRules(node, dir, prunedAdjList);
        }

        return prunedAdjList;
    }

    private void checkDiagPruningRules(Node node, Direction dir,
            NodeList prunedAdjList) {

        Direction naturalAdjDir1 = new Direction(dir.getX(), 0);
        Direction naturalAdjDir2 = new Direction(0, dir.getY());
        Direction naturalAdjDir3 = new Direction(dir.getX(), dir.getY());
        boolean hasNaturalAdj1 = grid.isAdjNodePassable(node, naturalAdjDir1);
        boolean hasNaturalAdj2 = grid.isAdjNodePassable(node, naturalAdjDir2);
        boolean hasNaturalAdj3 = grid.isAdjNodePassable(node, naturalAdjDir3);
        if (hasNaturalAdj1) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, naturalAdjDir1));
        }

        if (hasNaturalAdj2) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, naturalAdjDir2));
        }

        if (hasNaturalAdj1 && hasNaturalAdj2 && hasNaturalAdj3) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, naturalAdjDir3));
        }
    }

    private void checkHorPruningRules(Node node, Direction dir,
            NodeList prunedAdjList) {

        Direction naturalAdjDir = new Direction(dir.getX(), 0);
        Direction forcedAdjDir1 = new Direction(0, -1);
        Direction forcedAdjDir2 = new Direction(dir.getX(), -1);
        Direction forcedAdjDir3 = new Direction(0, 1);
        Direction forcedAdjDir4 = new Direction(dir.getX(), 1);
        Direction noAdjDir1 = new Direction(-dir.getX(), -1);
        Direction noAdjDir2 = new Direction(-dir.getX(), 1);
        boolean hasNaturalAdj = grid.isAdjNodePassable(node, naturalAdjDir);
        boolean hasForcedAdj1 = grid.isAdjNodePassable(node, forcedAdjDir1);
        boolean hasForcedAdj2 = grid.isAdjNodePassable(node, forcedAdjDir2);
        boolean hasForcedAdj3 = grid.isAdjNodePassable(node, forcedAdjDir3);
        boolean hasForcedAdj4 = grid.isAdjNodePassable(node, forcedAdjDir4);
        boolean hasNoAdj1 = !grid.isAdjNodePassable(node, noAdjDir1);
        boolean hasNoAdj2 = !grid.isAdjNodePassable(node, noAdjDir2);
        if (hasNaturalAdj) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, naturalAdjDir));
        }

        if (hasForcedAdj1 && hasNoAdj1) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, forcedAdjDir1));
        }

        if (hasNaturalAdj && hasForcedAdj1 && hasForcedAdj2 && hasNoAdj1) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, forcedAdjDir2));
        }

        if (hasForcedAdj3 && hasNoAdj2) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, forcedAdjDir3));
        }

        if (hasNaturalAdj && hasForcedAdj3 && hasForcedAdj4 && hasNoAdj2) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, forcedAdjDir4));
        }
    }

    private void checkVerPruningRules(Node node, Direction dir,
            NodeList prunedAdjList) {

        Direction naturalAdjDir = new Direction(0, dir.getY());
        Direction forcedAdjDir1 = new Direction(-1, 0);
        Direction forcedAdjDir2 = new Direction(-1, dir.getY());
        Direction forcedAdjDir3 = new Direction(1, 0);
        Direction forcedAdjDir4 = new Direction(1, dir.getY());
        Direction noAdjDir1 = new Direction(-1, -dir.getY());
        Direction noAdjDir2 = new Direction(1, -dir.getY());
        boolean hasNaturalAdj = grid.isAdjNodePassable(node, naturalAdjDir);
        boolean hasForcedAdj1 = grid.isAdjNodePassable(node, forcedAdjDir1);
        boolean hasForcedAdj2 = grid.isAdjNodePassable(node, forcedAdjDir2);
        boolean hasForcedAdj3 = grid.isAdjNodePassable(node, forcedAdjDir3);
        boolean hasForcedAdj4 = grid.isAdjNodePassable(node, forcedAdjDir4);
        boolean hasNoAdj1 = !grid.isAdjNodePassable(node, noAdjDir1);
        boolean hasNoAdj2 = !grid.isAdjNodePassable(node, noAdjDir2);
        if (hasForcedAdj1 && hasNoAdj1) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, forcedAdjDir1));
        }

        if (hasNaturalAdj && hasForcedAdj1 && hasForcedAdj2 && hasNoAdj1) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, forcedAdjDir2));
        }

        if (hasForcedAdj3 && hasNoAdj2) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, forcedAdjDir3));
        }

        if (hasNaturalAdj && hasForcedAdj3 && hasForcedAdj4 && hasNoAdj2) {
            prunedAdjList.add(grid.getAdjNodeInDir(node, forcedAdjDir4));
        }
    }

    private Node jump(Node parent, Direction dir) {
        Node node = grid.getAdjNodeInDir(parent, dir);
        if (node == null) {
            return null;
        }

        if (node.equals(goal)) {
            return node;
        }

        if (dir.isHor()) {
            Direction forcedAdjDir1 = new Direction(0, -1);
            Direction forcedAdjDir2 = new Direction(0, 1);
            Direction noAdjDir1 = new Direction(-dir.getX(), -1);
            Direction noAdjDir2 = new Direction(-dir.getX(), 1);
            boolean hasForcedAdj1 = grid.isAdjNodePassable(node, forcedAdjDir1);
            boolean hasForcedAdj2 = grid.isAdjNodePassable(node, forcedAdjDir2);
            boolean hasNoAdj1 = !grid.isAdjNodePassable(node, noAdjDir1);
            boolean hasNoAdj2 = !grid.isAdjNodePassable(node, noAdjDir2);
            if ((hasForcedAdj1 && hasNoAdj1) || (hasForcedAdj2 && hasNoAdj2)) {
                return node;
            }
        } else if (dir.isVer()) {
            Direction forcedAdjDir1 = new Direction(-1, 0);
            Direction forcedAdjDir2 = new Direction(1, 0);
            Direction noAdjDir1 = new Direction(-1, -dir.getX());
            Direction noAdjDir2 = new Direction(1, -dir.getX());
            boolean hasForcedAdj1 = grid.isAdjNodePassable(node, forcedAdjDir1);
            boolean hasForcedAdj2 = grid.isAdjNodePassable(node, forcedAdjDir2);
            boolean hasNoAdj1 = !grid.isAdjNodePassable(node, noAdjDir1);
            boolean hasNoAdj2 = !grid.isAdjNodePassable(node, noAdjDir2);
            if ((hasForcedAdj1 && hasNoAdj1) || (hasForcedAdj2 && hasNoAdj2)) {
                return node;
            }
        } else if (jump(node, new Direction(dir.getX(), 0)) != null
                || jump(node, new Direction(0, dir.getY())) != null) {

            return node;
        }

        return jump(node, dir);
    }
}
