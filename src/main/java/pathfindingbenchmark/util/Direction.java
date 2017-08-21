/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.util;

import pathfindingbenchmark.grid.Node;

/**
 * Edettävää suuntaa koordinaatistossa kuvaava luokka.
 *
 * @author thalvari
 */
public class Direction {

    private final int x;
    private final int y;

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Konstruktori laskee suuntavektorin arvot.
     *
     * @param parIdx Solmun vanhemman indeksi.
     * @param idx Solmun indeksi.
     */
    public Direction(Node node, Node succ) {
        x = calcX(node, succ);
        y = calcY(node, succ);
    }

    private int calcX(Node node, Node succ) {
        int xDif = succ.getX() - node.getX();
        if (xDif == 0) {
            return 0;
        } else {
            return xDif / Math.abs(xDif);
        }
    }

    private int calcY(Node node, Node succ) {
        int yDif = succ.getY() - node.getY();
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
