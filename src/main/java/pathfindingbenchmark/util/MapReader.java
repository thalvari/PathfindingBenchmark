/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thalvari
 */
public class MapReader {

    public static final List<String> MAPS = new ArrayList<>();

    static {
        try {
            Files.newDirectoryStream(Paths.get("maps/")).forEach((path) -> {
                MAPS.add(path.getFileName().toString().split(".map")[0]);
            });
        } catch (IOException ex) {
            MAPS.clear();
        }
    }

    public List<String> readMap(String mapName) {
        List<String> mapData = new ArrayList<>();
        try {
            Files.lines(Paths.get("maps/" + mapName + ".map"))
                    .forEach(mapData::add);
        } catch (Exception e) {
            return null;
        }

        return mapData;
    }
}
