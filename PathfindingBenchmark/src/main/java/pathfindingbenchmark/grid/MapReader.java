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

    public List<String> readFile(String mapName) {
        List<String> lines = new ArrayList<>();
        try {
            Files.lines(Paths.get("maps/" + mapName + ".map"))
                    .forEach(lines::add);
        } catch (Exception e) {
            return null;
        }

        return lines;
    }

}
