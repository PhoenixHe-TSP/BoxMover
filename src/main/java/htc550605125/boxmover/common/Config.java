package htc550605125.boxmover.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 10:58 AM
 */
public class Config {
    private static Config instance = null;
    private static final Logger logger = LogManager.getLogger("common");

    private JsonNode rootNode;

    private Config(File fd){
        try {
            this.rootNode = getRootNode(fd);
        }
        catch (IOException e) {
            logger.error(e);
            logger.error("Cannot read config file!");
            System.exit(-1);
        }
        logger.info("Configuration loaded successfully");
    }

    public static JsonNode getRootNode(File fd) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readTree(fd);
    }

    public static Config getConfig() {
        if (instance == null) {
            instance = new Config(new File("config.json"));
        }
        return instance;
    }

    public static JsonNode getNode(JsonNode t, String path) {
        String[] p = path.split("\\/");
        for (String node : p) {
            if (node.equals("")) continue;
            t = t.get(node);
        }
        return t;
    }

    public JsonNode getNode(String path) {
        return getNode(rootNode, path);
    }

    public static String get(JsonNode t, String path) {
        return getNode(t, path).asText();
    }

    public String get(String path) {
        return get(rootNode, path);
    }
}