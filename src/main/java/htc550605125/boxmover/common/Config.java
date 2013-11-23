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

/**
 * Parse the configuration file "config,json"
 */
public class Config {
    private static Config instance = null;
    private static final Logger logger = LogManager.getLogger("common");

    private JsonNode rootNode;

    private Config(File fd) {
        try {
            this.rootNode = getRootNode(fd);
        } catch (IOException e) {
            Utils.exit(e, logger, "Cannot read config file!");
        }
        logger.info("Configuration loaded successfully");
    }

    /**
     * @return The root node of the config file
     */
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

    /**
     * Get the node information by the path
     *
     * @param t    The root node
     * @param path The path of the node you want, for example "/server/config"
     * @return The JsonNode under the path of the root node
     */
    public static JsonNode getNode(JsonNode t, String path) {
        String[] p = path.split("\\/");
        for (String node : p) {
            if (node.equals("")) continue;
            t = t.get(node);
        }
        return t;
    }

    /**
     * @return The JsonNode under the path of the config
     *         files' root node
     */
    public JsonNode getNode(String path) {
        return getNode(rootNode, path);
    }

    /**
     * Get the node information by the path
     *
     * @param t    The root node
     * @param path The path of the node you want, for example "/server/config"
     * @return The String value of the JsonNode under the path of the root node
     */
    public static String get(JsonNode t, String path) {
        return getNode(t, path).asText();
    }

    /**
     * @return The string value of the node under the
     *         path of the config files' root node
     */
    public String get(String path) {
        return get(rootNode, path);
    }
}