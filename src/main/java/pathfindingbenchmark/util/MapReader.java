/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * @author thalvari
 */
public class MapReader {

    public static final String[] MAPS;

    static {
        Object[] paths = getPaths();
        if (paths == null) {
            MAPS = null;
        } else {
            MAPS = new String[paths.length];
            for (int i = 0; i < paths.length; i++) {
                MAPS[i] = paths[i].toString().split("maps/")[1]
                        .split(".map")[0];
            }
        }
    }

    private static Object[] getPaths() {
        try {
            return Files.list(Paths.get("maps/")).sorted().toArray();
        } catch (IOException e) {
            return null;
        }
    }

    public String[] readMap(String mapName) {
        Object[] lineObjects = getLineObjects(mapName);
        if (lineObjects == null) {
            return null;
        } else {
            return Arrays.copyOf(lineObjects, lineObjects.length,
                    String[].class);
        }
    }

    private static Object[] getLineObjects(String mapName) {
        try {
            return Files.lines(Paths.get("maps/" + mapName + ".map")).toArray();
        } catch (IOException e) {
            return null;
        }
    }

}
