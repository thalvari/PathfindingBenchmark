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
        IntList prunedAdjList = prune(idx);
        IntList succList = new IntList();
        for (int i = 0; i < prunedAdjList.size(); i++) {
            int succIdx = prunedAdjList.get(i);
            succIdx = jump(idx, new Direction(idx, succIdx, grid));
            if (succIdx != 0) {
                succList.add(succIdx);
            }
        }

        return succList;
    }

    private IntList prune(int idx) {
        int parIdx = prev[idx];
        if (parIdx == 0) {
            return grid.getAdjList(idx);
        }

        Direction dir = new Direction(parIdx, idx, grid);
        IntList prunedAdjList = new IntList();
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
        if (checkDiagPruningRule1(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y));
        }

        if (checkDiagPruningRule2(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + dir.getY()));
        }

        if (checkDiagPruningRule3(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y + dir.getY()));
        }
    }

    private boolean checkDiagPruningRule1(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) + dir.getX(), grid.getY(idx));
    }

    private boolean checkDiagPruningRule2(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx), grid.getY(idx) + dir.getY());
    }

    private boolean checkDiagPruningRule3(int idx, Direction dir) {
        return checkDiagPruningRule1(idx, dir)
                && checkDiagPruningRule2(idx, dir)
                && grid.isPassable(grid.getX(idx) + dir.getX(),
                        grid.getY(idx) + dir.getY());
    }

    private void checkHorPruningRules(int idx, Direction dir,
            IntList prunedAdjList) {

        int x = grid.getX(idx);
        int y = grid.getY(idx);
        if (checkHorPruningRule1(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y));
        }

        if (checkHorPruningRule2(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x, y - 1));
        }

        if (checkHorPruningRule3(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y - 1));
        }

        if (checkHorPruningRule4(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + 1));
        }

        if (checkHorPruningRule5(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + dir.getX(), y + 1));
        }
    }

    private boolean checkHorPruningRule1(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx) + dir.getX(), grid.getY(idx));
    }

    private boolean checkHorPruningRule2(int idx, Direction dir) {
        int x = grid.getX(idx);
        int y = grid.getY(idx);
        return grid.isPassable(x, y - 1)
                && !grid.isPassable(x - dir.getX(), y - 1);
    }

    private boolean checkHorPruningRule3(int idx, Direction dir) {
        return checkHorPruningRule2(idx, dir)
                && grid.isPassable(grid.getX(idx) + dir.getX(),
                        grid.getY(idx) - 1);
    }

    private boolean checkHorPruningRule4(int idx, Direction dir) {
        int x = grid.getX(idx);
        int y = grid.getY(idx);
        return grid.isPassable(x, y + 1)
                && !grid.isPassable(x - dir.getX(), y + 1);
    }

    private boolean checkHorPruningRule5(int idx, Direction dir) {
        return checkHorPruningRule4(idx, dir)
                && grid.isPassable(grid.getX(idx) + dir.getX(),
                        grid.getY(idx) + 1);
    }

    private void checkVerPruningRules(int idx, Direction dir,
            IntList prunedAdjList) {

        int x = grid.getX(idx);
        int y = grid.getY(idx);
        if (checkVerPruningRule1(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x, y + dir.getY()));
        }

        if (checkVerPruningRule2(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x - 1, y));
        }

        if (checkVerPruningRule3(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x - 1, y + dir.getY()));
        }

        if (checkVerPruningRule4(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + 1, y));
        }

        if (checkVerPruningRule5(idx, dir)) {
            prunedAdjList.add(grid.getIdx(x + 1, y + dir.getY()));
        }
    }

    private boolean checkVerPruningRule1(int idx, Direction dir) {
        return grid.isPassable(grid.getX(idx), grid.getY(idx) + dir.getY());
    }

    private boolean checkVerPruningRule2(int idx, Direction dir) {
        int x = grid.getX(idx);
        int y = grid.getY(idx);
        return grid.isPassable(x - 1, y)
                && !grid.isPassable(x - 1, y - dir.getY());
    }

    private boolean checkVerPruningRule3(int idx, Direction dir) {
        return checkVerPruningRule2(idx, dir)
                && grid.isPassable(grid.getX(idx) - 1,
                        grid.getY(idx) + dir.getY());
    }

    private boolean checkVerPruningRule4(int idx, Direction dir) {
        int x = grid.getX(idx);
        int y = grid.getY(idx);
        return grid.isPassable(x + 1, y)
                && !grid.isPassable(x + 1, y - dir.getY());
    }

    private boolean checkVerPruningRule5(int idx, Direction dir) {
        return checkVerPruningRule4(idx, dir)
                && grid.isPassable(grid.getX(idx) + 1,
                        grid.getY(idx) + dir.getY());
    }

    private int jump(int parIdx, Direction dir) {
        if (!nextPassable(parIdx, dir)) {
            return 0;
        }

        int idx = step(parIdx, dir);
        if (idx == goalIdx) {
            return idx;
        }

        if (!dir.isDiag()) {
            if ((dir.isHor()
                    && (checkHorPruningRule2(idx, dir)
                    || checkHorPruningRule3(idx, dir)
                    || checkHorPruningRule4(idx, dir)
                    || checkHorPruningRule5(idx, dir)))
                    || (dir.isVer()
                    && (checkVerPruningRule2(idx, dir)
                    || checkVerPruningRule3(idx, dir)
                    || checkVerPruningRule4(idx, dir)
                    || checkVerPruningRule5(idx, dir)))) {

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
