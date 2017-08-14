/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.algorithms;

import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class Direction {

    private final int x;
    private final int y;

    public Direction(int parIdx, int idx, Grid grid) {
        x = calcXDir(parIdx, idx, grid);
        y = calcYDir(parIdx, idx, grid);
    }

    private int calcXDir(int parIdx, int idx, Grid grid) {
        int xDif = grid.getX(idx) - grid.getX(parIdx);
        if (xDif == 0) {
            return 0;
        } else {
            return xDif / Math.abs(xDif);
        }
    }

    private int calcYDir(int parIdx, int idx, Grid grid) {
        int yDif = grid.getY(idx) - grid.getY(parIdx);
        if (yDif == 0) {
            return 0;
        } else {
            return yDif / Math.abs(yDif);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDiag() {
        return x != 0 && y != 0;
    }

    public boolean isHor() {
        return x != 0 && y == 0;
    }

    public boolean isVer() {
        return x == 0 && y != 0;
    }
}
