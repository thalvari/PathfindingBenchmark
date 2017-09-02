/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.datastructures.MyList;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.grid.Node;
import pathfindingbenchmark.util.Direction;

/**
 * JPS-algoritmin toteutus. Algoritmi karsii ensin huonot naapurisolmut ja etsii
 * loppujen tilalle mahdollisen hyppysolmun joka on samassa linjassa solmun ja
 * naapurin kanssa.
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
    protected MyList<Node> getSuccList(Node node) {
        MyList<Node> pruned = getPruned(node);
        MyList<Node> succList = new MyList<>();
        for (int i = 0; i < pruned.size(); i++) {
            Node succ = jump(node, new Direction(node, pruned.get(i)));
            if (succ != null) {
                succList.add(succ);
            }
        }

        return succList;
    }

    private void checkPruningRulesWhenDirDiag(Node node, Direction dir,
            MyList<Node> pruned) {

        Direction naturalAdjDir1 = new Direction(dir.getX(), 0);
        Direction naturalAdjDir2 = new Direction(0, dir.getY());
        Direction naturalAdjDir3 = new Direction(dir.getX(), dir.getY());
        boolean hasNaturalAdj1 = grid.isNodeInDirPassable(node, naturalAdjDir1);
        boolean hasNaturalAdj2 = grid.isNodeInDirPassable(node, naturalAdjDir2);
        boolean hasNaturalAdj3 = grid.isNodeInDirPassable(node, naturalAdjDir3);
        if (hasNaturalAdj1) {
            pruned.add(grid.getNodeInDir(node, naturalAdjDir1));
        }

        if (hasNaturalAdj2) {
            pruned.add(grid.getNodeInDir(node, naturalAdjDir2));
        }

        if (hasNaturalAdj1 && hasNaturalAdj2 && hasNaturalAdj3) {
            pruned.add(grid.getNodeInDir(node, naturalAdjDir3));
        }
    }

    private void checkPruningRulesWhenDirHor(Node node, Direction dir,
            MyList<Node> pruned) {

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
            pruned.add(grid.getNodeInDir(node, naturalAdjDir));
        }

        if (grid.isNodeInDirPassable(node, forcedAdjDir1) && forcedPossibleUp) {
            pruned.add(grid.getNodeInDir(node, forcedAdjDir1));
        }

        if (grid.isNodeInDirAdj(node, forcedAdjDir2) && forcedPossibleUp) {
            pruned.add(grid.getNodeInDir(node, forcedAdjDir2));
        }

        if (grid.isNodeInDirPassable(node, forcedAdjDir3)
                && forcedPossibleDown) {

            pruned.add(grid.getNodeInDir(node, forcedAdjDir3));
        }

        if (grid.isNodeInDirAdj(node, forcedAdjDir4) && forcedPossibleDown) {
            pruned.add(grid.getNodeInDir(node, forcedAdjDir4));
        }
    }

    private void checkPruningRulesWhenDirVer(Node node, Direction dir,
            MyList<Node> pruned) {

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
            pruned.add(grid.getNodeInDir(node, naturalAdjDir));
        }

        if (grid.isNodeInDirPassable(node, forcedAdjDir1)
                && forcedPossibleLeft) {

            pruned.add(grid.getNodeInDir(node, forcedAdjDir1));
        }

        if (grid.isNodeInDirAdj(node, forcedAdjDir2) && forcedPossibleLeft) {
            pruned.add(grid.getNodeInDir(node, forcedAdjDir2));
        }

        if (grid.isNodeInDirPassable(node, forcedAdjDir3)
                && forcedPossibleRight) {

            pruned.add(grid.getNodeInDir(node, forcedAdjDir3));
        }

        if (grid.isNodeInDirAdj(node, forcedAdjDir4) && forcedPossibleRight) {
            pruned.add(grid.getNodeInDir(node, forcedAdjDir4));
        }
    }

    private MyList<Node> getPruned(Node node) {
        Node parent = node.getPrev();
        if (parent == null) {
            return grid.createAdjList(node);
        }

        MyList<Node> pruned = new MyList<>();
        Direction dir = new Direction(parent, node);
        if (dir.isDiag()) { // Tarkistetaan karsimissäännöt viistoon edetessä.
            checkPruningRulesWhenDirDiag(node, dir, pruned);
        } else if (dir.isHor()) { // Karsimissäännöt vaakasuuntaan edetessä.
            checkPruningRulesWhenDirHor(node, dir, pruned);
        } else { // Karsimissäännöt pystysuuntaan edetessä.
            checkPruningRulesWhenDirVer(node, dir, pruned);
        }

        return pruned;
    }

    private Node jump(Node parent, Direction dir) {
        if (!grid.isNodeInDirAdj(parent, dir)) { // Katsotaan voidaanko edetä.
            return null;
        }

        Node node = grid.getNodeInDir(parent, dir);
        if (node.equals(goal)) {
            return node;
        }

        if (!dir.isDiag()) { // Tarkastetaan onko pakotettuja naapureita.
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
        } else if (jump(node, new Direction(dir.getX(), 0)) != null
                || jump(node, new Direction(0, dir.getY())) != null) {

            return node; // Pysty- tai vaakasuunnassa solmu jolla pakotettuja.
        }

        return jump(node, dir); // Muuten jatketaan samaan suuntaan.
    }
}
