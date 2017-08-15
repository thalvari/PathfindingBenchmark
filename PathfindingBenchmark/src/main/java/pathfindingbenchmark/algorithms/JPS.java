/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.datastructures.IntList;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class JPS extends AStarAbstract {

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
            return grid.createAdjListForIdx(idx);
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
        if (checkDiagNaturalAdj1(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y));
            hasNaturalAdj1 = true;
        }

        if (checkDiagNaturalAdj2(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + dir.getY()));
            hasNaturalAdj2 = true;
        }

        if (hasNaturalAdj1
                && hasNaturalAdj2
                && checkDiagNaturalAdj3(idx, dir)) {

            prunedAdjList.add(grid.getIdx(x + dir.getX(), y + dir.getY()));
        }
    }

    private boolean checkDiagNaturalAdj1(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) + dir.getX(), grid.getY(idx));
    }

    private boolean checkDiagNaturalAdj2(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx), grid.getY(idx) + dir.getY());
    }

    private boolean checkDiagNaturalAdj3(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) + dir.getX(),
                grid.getY(idx) + dir.getY());
    }

    private void checkHorPruningRules(int idx, Direction dir,
            IntList prunedAdjList) {

        int x = grid.getX(idx);
        int y = grid.getY(idx);
        boolean hasForcedAdj1 = false;
        boolean hasForcedAdj3 = false;
        if (checkHorNaturalAdj(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y));
        }

        if (checkHorForcedAdj1(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x, y - 1));
            hasForcedAdj1 = true;
        }

        if (hasForcedAdj1 && checkHorForcedAdj2(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y - 1));
        }

        if (checkHorForcedAdj3(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + 1));
            hasForcedAdj3 = true;
        }

        if (hasForcedAdj3 && checkHorForcedAdj4(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y + 1));
        }
    }

    private boolean checkHorNaturalAdj(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) + dir.getX(), grid.getY(idx));
    }

    private boolean checkHorForcedAdj1(int idx, Direction dir) {
        int x = grid.getX(idx);
        int y = grid.getY(idx);
        return grid.isPassable(x, y - 1)
                && !grid.isPassable(x - dir.getX(), y - 1);
    }

    private boolean checkHorForcedAdj2(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) + dir.getX(), grid.getY(idx) - 1);
    }

    private boolean checkHorForcedAdj3(int idx, Direction dir) {
        int x = grid.getX(idx);
        int y = grid.getY(idx);
        return grid.isPassable(x, y + 1)
                && !grid.isPassable(x - dir.getX(), y + 1);
    }

    private boolean checkHorForcedAdj4(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) + dir.getX(), grid.getY(idx) + 1);
    }

    private void checkVerPruningRules(int idx, Direction dir,
            IntList prunedAdjList) {

        int x = grid.getX(idx);
        int y = grid.getY(idx);
        boolean hasForcedAdj1 = false;
        boolean hasForcedAdj3 = false;
        if (checkVerNaturalAdj(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + dir.getY()));
        }

        if (checkVerForcedAdj1(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x - 1, y));
            hasForcedAdj1 = true;
        }

        if (hasForcedAdj1 && checkVerForcedAdj2(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x - 1, y + dir.getY()));
        }

        if (checkVerForcedAdj3(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + 1, y));
            hasForcedAdj3 = true;
        }

        if (hasForcedAdj3 && checkVerForcedAdj4(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + 1, y + dir.getY()));
        }
    }

    private boolean checkVerNaturalAdj(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx), grid.getY(idx) + dir.getY());
    }

    private boolean checkVerForcedAdj1(int idx, Direction dir) {
        int x = grid.getX(idx);
        int y = grid.getY(idx);
        return grid.isPassable(x - 1, y)
                && !grid.isPassable(x - 1, y - dir.getY());
    }

    private boolean checkVerForcedAdj2(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) - 1, grid.getY(idx) + dir.getY());
    }

    private boolean checkVerForcedAdj3(int idx, Direction dir) {
        int x = grid.getX(idx);
        int y = grid.getY(idx);
        return grid.isPassable(x + 1, y)
                && !grid.isPassable(x + 1, y - dir.getY());
    }

    private boolean checkVerForcedAdj4(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) + 1, grid.getY(idx) + dir.getY());
    }

    protected int jump(int parIdx, Direction dir) {
        if (!nextPassable(parIdx, dir)) {
            return 0;
        }

        int idx = step(parIdx, dir);
        if (idx == goalIdx) {
            return idx;
        }

        if (!dir.isDiag()) {
            if ((dir.isHor()
                    && (checkHorForcedAdj1(idx, dir)
                    || checkHorForcedAdj3(idx, dir)))
                    || (dir.isVer()
                    && (checkVerForcedAdj1(idx, dir)
                    || checkVerForcedAdj3(idx, dir)))) {

                return idx;
            }
        } else {
            int idx1 = grid.getIdx(grid.getX(idx), grid.getY(parIdx));
            int idx2 = grid.getIdx(grid.getX(parIdx), grid.getY(idx));
            Direction dir1 = new Direction(parIdx, idx1, grid);
            Direction dir2 = new Direction(parIdx, idx2, grid);
            if (jump(idx, dir1) != 0 || jump(idx, dir2) != 0) {
                return idx;
            }
        }

        return jump(idx, dir);
    }

    private boolean nextPassable(int idx, Direction dir) {
        int nextX = grid.getX(idx) + dir.getX();
        int nextY = grid.getY(idx) + dir.getY();
        return grid.isPassable(nextX, nextY);
    }

    private int step(int idx, Direction dir) {
        return grid.getIdx(grid.getX(idx) + dir.getX(),
                grid.getY(idx) + dir.getY());
    }
}
