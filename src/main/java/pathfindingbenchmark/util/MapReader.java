/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import pathfindingbenchmark.datastructures.MyList;
import pathfindingbenchmark.grid.Node;

/**
 * Kartanlukija.
 *
 * @author thalvari
 */
public class MapReader {

    /**
     * Lista /maps -kansiossa olevien karttojen nimistä.
     */
    public static final MyList<String> MAP_NAMES;

    static {
        MAP_NAMES = getMapNames();
    }

    private MyList<String> mapData;

    /**
     * Konstruktori.
     *
     * @param mapName Kartan nimi.
     */
    public MapReader(String mapName) {
        readMap(mapName);
    }

    private static MyList<String> getMapNames() {
        MyList<String> mapNames = new MyList<>();
        try {
            Files.list(Paths.get("maps/")).forEach((path) -> {
                mapNames.add(path.getFileName().toString().split(".map")[0]);
            });
        } catch (Exception e) {
            return null;
        }

        return mapNames;
    }

    private void readMap(String mapName) {
        mapData = new MyList<>();
        try {
            Files.lines(Paths.get("maps/" + mapName + ".map"))
                    .forEach(mapData::add);
        } catch (Exception e) {
            mapData = null;
        }
    }

    /**
     * Palauttaa kartan korkeuden.
     *
     * @return Korkeus.
     */
    public int getHeight() {
        if (mapData == null) {
            return 0;
        } else {
            return Integer.parseInt(mapData.get(1).split(" ")[1]);
        }
    }

    /**
     * Palauttaa kartan leveyden.
     *
     * @return Leveys.
     */
    public int getWidth() {
        if (mapData == null) {
            return 0;
        } else {
            return Integer.parseInt(mapData.get(2).split(" ")[1]);
        }
    }

    /**
     * Palauttaa kartan esityksen solmuja sisältävänä taulukkona.
     *
     * @return Solmuja sisältävä taulukko.
     */
    public Node[][] initNodes() {
        if (mapData == null) {
            return null;
        }

        Node[][] nodes = new Node[getHeight()][getWidth()];
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                nodes[y][x] = new Node(x, y, getOrigSymbol(x, y));
            }
        }

        return nodes;
    }

    /**
     * Palauttaa kartan tietyissä koordinaateissa olevan merkin.
     *
     * @param x X-koordinaatti.
     * @param y Y-koordinaatti.
     * @return Merkki.
     */
    public char getOrigSymbol(int x, int y) {
        return mapData.get(4 + y).charAt(x);
    }
}
