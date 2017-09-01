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

    /**
     * Konstruktori.
     *
     * @param x Suunta vaaka-akselilla.
     * @param y Suunta pystyakselilla.
     */
    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vaihtoehtoinen konstruktori.
     *
     * @param node1 Solmu.
     * @param node2 Toinen solmu.
     */
    public Direction(Node node1, Node node2) {
        x = calcX(node1, node2);
        y = calcY(node1, node2);
    }

    private int calcX(Node node1, Node node2) {
        int xDif = node2.getX() - node1.getX();
        if (xDif == 0) {
            return 0;
        } else {
            return xDif / Math.abs(xDif);
        }
    }

    private int calcY(Node node1, Node node2) {
        int yDif = node2.getY() - node1.getY();
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
