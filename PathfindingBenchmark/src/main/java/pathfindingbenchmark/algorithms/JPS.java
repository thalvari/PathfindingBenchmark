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
        NodeList pruned = getPruned(node);
        NodeList succList = new NodeList();
        for (int i = 0; i < pruned.size(); i++) {
            Node succ = jump(node, new Direction(node, pruned.get(i)));
            if (succ != null) {
                succList.add(succ);
            }
        }

        return succList;
    }

    private NodeList getPruned(Node node) {
        Node parent = node.getPrev();
        if (parent == null) {
            return grid.createAdjList(node);
        }

        NodeList pruned = new NodeList();
        Direction dir = new Direction(parent, node);
        if (dir.isDiag()) {
            checkPruningRulesWhenDirDiag(node, dir, pruned);
        } else if (dir.isHor()) {
            checkPruningRulesWhenDirHor(node, dir, pruned);
        } else {
            checkPruningRulesWhenDirVer(node, dir, pruned);
        }

        return pruned;
    }

    private void checkPruningRulesWhenDirDiag(Node node, Direction dir,
            NodeList pruned) {

        Direction naturalAdjDir1 = new Direction(dir.getX(), 0);
        Direction naturalAdjDir2 = new Direction(0, dir.getY());
        Direction naturalAdjDir3 = new Direction(dir.getX(), dir.getY());
        boolean hasNaturalAdj1 = grid.isNodeInDirPassable(node, naturalAdjDir1);
        boolean hasNaturalAdj2 = grid.isNodeInDirPassable(node, naturalAdjDir2);
        boolean hasNaturalAdj3 = grid.isNodeInDirPassable(node, naturalAdjDir3);
        if (hasNaturalAdj1) {
            pruned.add(grid.getAdjNodeInDir(node, naturalAdjDir1));
        }

        if (hasNaturalAdj2) {
            pruned.add(grid.getAdjNodeInDir(node, naturalAdjDir2));
        }

        if (hasNaturalAdj1 && hasNaturalAdj2 && hasNaturalAdj3) {
            pruned.add(grid.getAdjNodeInDir(node, naturalAdjDir3));
        }
    }

    private void checkPruningRulesWhenDirHor(Node node, Direction dir,
            NodeList pruned) {

        Direction naturalAdjDir = new Direction(dir.getX(), 0);
        Direction forcedAdjDir1 = new Direction(0, -1);
        Direction forcedAdjDir2 = new Direction(dir.getX(), -1);
        Direction forcedAdjDir3 = new Direction(0, 1);
        Direction forcedAdjDir4 = new Direction(dir.getX(), 1);
        boolean forcedPossibleUp = !grid.isNodeInDirPassable(node,
                new Direction(-dir.getX(), -1));

        boolean forcedPossibleDown = !grid.isNodeInDirPassable(node,
                new Direction(-dir.getX(), 1));

        if (grid.isNodeInDirPassable(node, naturalAdjDir)) {
            pruned.add(grid.getAdjNodeInDir(node, naturalAdjDir));
        }

        if (grid.isNodeInDirPassable(node, forcedAdjDir1) && forcedPossibleUp) {
            pruned.add(grid.getAdjNodeInDir(node, forcedAdjDir1));
        }

        if (grid.isNodeInDirAdj(node, forcedAdjDir2) && forcedPossibleUp) {
            pruned.add(grid.getAdjNodeInDir(node, forcedAdjDir2));
        }

        if (grid.isNodeInDirPassable(node, forcedAdjDir3)
                && forcedPossibleDown) {

            pruned.add(grid.getAdjNodeInDir(node, forcedAdjDir3));
        }

        if (grid.isNodeInDirAdj(node, forcedAdjDir4) && forcedPossibleDown) {
            pruned.add(grid.getAdjNodeInDir(node, forcedAdjDir4));
        }
    }

    private void checkPruningRulesWhenDirVer(Node node, Direction dir,
            NodeList pruned) {

        Direction naturalAdjDir = new Direction(0, dir.getY());
        Direction forcedAdjDir1 = new Direction(-1, 0);
        Direction forcedAdjDir2 = new Direction(-1, dir.getY());
        Direction forcedAdjDir3 = new Direction(1, 0);
        Direction forcedAdjDir4 = new Direction(1, dir.getY());
        boolean forcedPossibleLeft = !grid.isNodeInDirPassable(node,
                new Direction(-1, -dir.getY()));

        boolean forcedPossibleRight = !grid.isNodeInDirPassable(node,
                new Direction(1, -dir.getY()));

        if (grid.isNodeInDirPassable(node, naturalAdjDir)) {
            pruned.add(grid.getAdjNodeInDir(node, naturalAdjDir));
        }

        if (grid.isNodeInDirPassable(node, forcedAdjDir1)
                && forcedPossibleLeft) {

            pruned.add(grid.getAdjNodeInDir(node, forcedAdjDir1));
        }

        if (grid.isNodeInDirAdj(node, forcedAdjDir2) && forcedPossibleLeft) {
            pruned.add(grid.getAdjNodeInDir(node, forcedAdjDir2));
        }

        if (grid.isNodeInDirPassable(node, forcedAdjDir3)
                && forcedPossibleRight) {

            pruned.add(grid.getAdjNodeInDir(node, forcedAdjDir3));
        }

        if (grid.isNodeInDirAdj(node, forcedAdjDir4) && forcedPossibleRight) {
            pruned.add(grid.getAdjNodeInDir(node, forcedAdjDir4));
        }
    }

    private Node jump(Node parent, Direction dir) {
        Node node;
        if (grid.isNodeInDirAdj(parent, dir)) {
            node = grid.getAdjNodeInDir(parent, dir);
        } else {
            return null;
        }

        if (node.equals(goal)) {
            return node;
        }

        if (!dir.isDiag()) {
            if ((dir.isHor()
                    && ((grid.isNodeInDirPassable(node, new Direction(0, -1))
                    && !grid.isNodeInDirPassable(node,
                            new Direction(-dir.getX(), -1)))
                    || (grid.isNodeInDirPassable(node, new Direction(0, 1))
                    && !grid.isNodeInDirPassable(node,
                            new Direction(-dir.getX(), 1)))))
                    || (dir.isVer()
                    && ((grid.isNodeInDirPassable(node, new Direction(-1, 0))
                    && !grid.isNodeInDirPassable(node,
                            new Direction(-1, -dir.getY())))
                    || (grid.isNodeInDirPassable(node, new Direction(1, 0))
                    && !grid.isNodeInDirPassable(node,
                            new Direction(1, -dir.getY())))))) {

                return node;
            }
        } else {
            if (jump(node, new Direction(dir.getX(), 0)) != null
                    || jump(node, new Direction(0, dir.getY())) != null) {

                return node;
            }
        }

        return jump(node, dir);
    }
}
