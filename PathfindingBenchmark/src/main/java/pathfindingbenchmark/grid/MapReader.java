/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.grid;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thalvari
 */
public class MapReader {

    private List<String> mapData;
    private boolean error;

    public MapReader() {
        mapData = new ArrayList<>();
    }

    public void readFile(String mapName) {
        try {
            Files.lines(Paths.get("maps/" + mapName + ".map"))
                    .forEach(mapData::add);
        } catch (Exception e) {
            error = true;
        }
    }

    public List<String> getMapData() {
        return mapData;
    }

    public boolean isError() {
        return error;
    }
}
