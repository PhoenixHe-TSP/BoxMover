package htc550605125.boxmover.server.mapreader;

import htc550605125.boxmover.common.stage.Stage;
import htc550605125.boxmover.common.stage.Stages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/28/13
 * Time: 11:12 AM
 */

/**
 * Parse some maps
 */
public abstract class MapReader {
    private final static Logger logger = LogManager.getLogger("server");
    private Stages maps = null;

    /**
     * Init the MapReader with a root map list config JsonNode
     */
    protected MapReader(JsonNode node) {
        maps = new Stages();
        for (Iterator<Map.Entry<String, JsonNode>> it = node.getFields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> t = it.next();
            try {
                String file = t.getValue().get("file").asText();
                String title = t.getValue().get("title").asText();
                Stage map = parse(file, t.getKey(), title);
                if (!map.validate()) logger.warn(file + " seems not a valid map.");
                maps.put(map.info().getID(), map);
            } catch (IOException e) {
                logger.error("Cannot load " + t.getValue().get("file"));
                e.printStackTrace();
            }
        }
    }

    /**
     * @return The map list
     */
    public Stages getMaps() {
        return maps;
    }

    /**
     * Parse a game map file
     *
     * @param path  The path of the file
     * @param mapID Initialize the map with specific getID
     * @param title Initialize the map with specific getTitle
     * @throws java.io.FileNotFoundException
     */
    protected abstract Stage parse(String path, String mapID, String title) throws FileNotFoundException;
}