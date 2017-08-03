/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid("dao", "arena");
        String[][] mapData = grid.getMapData();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                System.out.print(mapData[y][x]);
            }
            System.out.println("");
        }
        System.out.println(grid.getAdjList(53));
        System.out.println(grid.getCost(53, 54));
        System.out.println(grid.getCost(53, 101));
        System.out.println(grid.getCost(53, 102));
        System.out.println(grid.getCost(53, 103));
    }
}
