/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.datastructures.IntList;
import pathfindingbenchmark.grid.Grid;
import pathfindingbenchmark.util.Direction;

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
    protected IntList getSuccList(int idx) {
        IntList prunedAdjList = getPrunedAdjList(idx);
        IntList succList = new IntList();
        for (int i = 0; i < prunedAdjList.size(); i++) {
            int succIdx = jump(idx,
                    new Direction(idx, prunedAdjList.get(i), grid));

            if (succIdx != 0) {
                succList.add(succIdx);
            }
        }

        return succList;
    }

    private IntList getPrunedAdjList(int idx) {
        int parIdx = prev[idx];
        IntList prunedAdjList = new IntList();
        if (parIdx == 0) {
            return grid.createAdjList(idx);
        }

        Direction dir = new Direction(parIdx, idx, grid);
        if (dir.isDiag()) {
            checkDiagPruningRules(idx, dir, prunedAdjList);
        } else if (dir.isHor()) {
            checkHorPruningRules(idx, dir, prunedAdjList);
        } else {
            checkVerPruningRules(idx, dir, prunedAdjList);
        }

        return prunedAdjList;
    }

    private void checkDiagPruningRules(int idx, Direction dir,
            IntList prunedAdjList) {

        int x = grid.getX(idx);
        int y = grid.getY(idx);
        boolean hasNaturalAdj1 = false;
        boolean hasNaturalAdj2 = false;
        if (checkDiagNaturalAdj1(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y));
            hasNaturalAdj1 = true;
        }

        if (checkDiagNaturalAdj2(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + dir.getY()));
            hasNaturalAdj2 = true;
        }

        if (hasNaturalAdj1
                && hasNaturalAdj2
                && checkDiagNaturalAdj3(x, y, dir)) {

            prunedAdjList.add(grid.getIdx(x + dir.getX(), y + dir.getY()));
        }
    }

    private boolean checkDiagNaturalAdj1(int x, int y, Direction dir) {
        return grid.isPassable(x + dir.getX(), y);
    }

    private boolean checkDiagNaturalAdj2(int x, int y, Direction dir) {
        return grid.isPassable(x, y + dir.getY());
    }

    private boolean checkDiagNaturalAdj3(int x, int y, Direction dir) {
        return grid.isPassable(x + dir.getX(), y + dir.getY());
    }

    private void checkHorPruningRules(int idx, Direction dir,
            IntList prunedAdjList) {

        int x = grid.getX(idx);
        int y = grid.getY(idx);
        boolean hasNaturalAdj = false;
        boolean hasForcedAdj1 = false;
        boolean hasForcedAdj3 = false;
        if (checkHorNaturalAdj(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y));
            hasNaturalAdj = true;
        }

        if (checkHorForcedAdj1(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x, y - 1));
            hasForcedAdj1 = true;
        }

        if (hasNaturalAdj && hasForcedAdj1 && checkHorForcedAdj2(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y - 1));
        }

        if (checkHorForcedAdj3(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + 1));
            hasForcedAdj3 = true;
        }

        if (hasNaturalAdj && hasForcedAdj3 && checkHorForcedAdj4(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y + 1));
        }
    }

    private boolean checkHorNaturalAdj(int x, int y, Direction dir) {
        return grid.isPassable(x + dir.getX(), y);
    }

    private boolean checkHorForcedAdj1(int x, int y, Direction dir) {
        return grid.isPassable(x, y - 1)
                && !grid.isPassable(x - dir.getX(), y - 1);
    }

    private boolean checkHorForcedAdj2(int x, int y, Direction dir) {
        return grid.isPassable(x + dir.getX(), y - 1);
    }

    private boolean checkHorForcedAdj3(int x, int y, Direction dir) {
        return grid.isPassable(x, y + 1)
                && !grid.isPassable(x - dir.getX(), y + 1);
    }

    private boolean checkHorForcedAdj4(int x, int y, Direction dir) {
        return grid.isPassable(x + dir.getX(), y + 1);
    }

    private void checkVerPruningRules(int idx, Direction dir,
            IntList prunedAdjList) {

        int x = grid.getX(idx);
        int y = grid.getY(idx);
        boolean hasNaturalAdj = false;
        boolean hasForcedAdj1 = false;
        boolean hasForcedAdj3 = false;
        if (checkVerNaturalAdj(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + dir.getY()));
            hasNaturalAdj = true;
        }

        if (checkVerForcedAdj1(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x - 1, y));
            hasForcedAdj1 = true;
        }

        if (hasNaturalAdj && hasForcedAdj1 && checkVerForcedAdj2(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x - 1, y + dir.getY()));
        }

        if (checkVerForcedAdj3(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x + 1, y));
            hasForcedAdj3 = true;
        }

        if (hasNaturalAdj && hasForcedAdj3 && checkVerForcedAdj4(x, y, dir)) {
            prunedAdjList.add(grid.getIdx(x + 1, y + dir.getY()));
        }
    }

    private boolean checkVerNaturalAdj(int x, int y, Direction dir) {
        return grid.isPassable(x, y + dir.getY());
    }

    private boolean checkVerForcedAdj1(int x, int y, Direction dir) {
        return grid.isPassable(x - 1, y)
                && !grid.isPassable(x - 1, y - dir.getY());
    }

    private boolean checkVerForcedAdj2(int x, int y, Direction dir) {
        return grid.isPassable(x - 1, y + dir.getY());
    }

    private boolean checkVerForcedAdj3(int x, int y, Direction dir) {
        return grid.isPassable(x + 1, y)
                && !grid.isPassable(x + 1, y - dir.getY());
    }

    private boolean checkVerForcedAdj4(int x, int y, Direction dir) {
        return grid.isPassable(x + 1, y + dir.getY());
    }

    private int jump(int parIdx, Direction dir) {
        int parX = grid.getX(parIdx);
        int parY = grid.getY(parIdx);
        if (!nextPassable(parX, parY, dir)) {
            return 0;
        }

        int idx = grid.getIdx(parX + dir.getX(), parY + dir.getY());
        if (idx == goalIdx) {
            return idx;
        }

        int x = grid.getX(idx);
        int y = grid.getY(idx);
        if (!dir.isDiag()) {
            if ((dir.isHor()
                    && (checkHorForcedAdj1(x, y, dir)
                    || checkHorForcedAdj3(x, y, dir)))
                    || (dir.isVer()
                    && (checkVerForcedAdj1(x, y, dir)
                    || checkVerForcedAdj3(x, y, dir)))) {

                return idx;
            }
        } else {
            Direction dir1 = new Direction(parIdx, grid.getIdx(x, parY), grid);
            Direction dir2 = new Direction(parIdx, grid.getIdx(parX, y), grid);
            if (jump(idx, dir1) != 0 || jump(idx, dir2) != 0) {
                return idx;
            }
        }

        return jump(idx, dir);
    }

    private boolean nextPassable(int x, int y, Direction dir) {
        return grid.isPassable(x + dir.getX(), y + dir.getY())
                && (!dir.isDiag()
                || (grid.isPassable(x + dir.getX(), y)
                && grid.isPassable(x, y + dir.getY())));
    }
}
