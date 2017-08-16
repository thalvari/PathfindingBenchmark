/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.util;

import pathfindingbenchmark.grid.Grid;

/**
 * Edettävää suuntaa koordinaatistossa kuvaava luokka.
 *
 * @author thalvari
 */
public class Direction {

    private final int x;
    private final int y;

    /**
     * Konstruktori laskee suuntavektorin arvot.
     *
     * @param parIdx Solmun vanhemman indeksi.
     * @param idx Solmun indeksi.
     * @param grid Verkko.
     */
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

    /**
     * Palauttaa suuntavektorin arvon vaaka-akselilla.
     *
     * @return Arvo.
     */
    public int getX() {
        return x;
    }

    /**
     * Palauttaa suuntavektorin arvon pysty-akselilla.
     *
     * @return Arvo.
     */
    public int getY() {
        return y;
    }

    /**
     * Kertoo osoittaako vektori viistoon.
     *
     * @return Totuusarvo.
     */
    public boolean isDiag() {
        return x != 0 && y != 0;
    }

    /**
     * Kertoo osoittaako vektori oikealle tai vasemmalle.
     *
     * @return Totuusarvo.
     */
    public boolean isHor() {
        return x != 0 && y == 0;
    }

    /**
     * Kertoo osoittaako vektori ylös tai alas.
     *
     * @return Totuusarvo.
     */
    public boolean isVer() {
        return x == 0 && y != 0;
    }
}
