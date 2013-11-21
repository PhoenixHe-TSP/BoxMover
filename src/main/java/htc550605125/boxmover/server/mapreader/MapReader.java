package htc550605125.boxmover.server.mapreader;

import htc550605125.boxmover.common.stage.Stage;
import htc550605125.boxmover.common.stage.Stages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/28/13
 * Time: 11:12 AM
 */
public abstract class MapReader {
    private final static Logger logger = LogManager.getLogger("server");
    private Stages maps = null;

    protected MapReader(JsonNode node) {
        maps = new Stages();
        for (Iterator<Map.Entry<String,JsonNode>> it = node.getFields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> t = it.next();
            try{
                String file = t.getValue().get("file").asText();
                String title = t.getValue().get("title").asText();
                Stage map = parse(file, t.getKey(), title);
                if (!map.validate())
                    logger.warn(file + " seems not a valid map.");
                maps.put(map.info().ID(), map);
            }
            catch (IOException e) {
                logger.error("Cannot load " + t.getValue().get("file"));
                e.printStackTrace();
            }
        }
    }

    public Stages getMaps() {
        return maps;
    }

    protected abstract Stage parse(String path, String mapID, String title) throws IOException;
}