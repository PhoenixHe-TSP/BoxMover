package htc550605125.boxmover.server.mapreader;

import htc550605125.boxmover.common.Config;
import htc550605125.boxmover.common.vector.Dim2D;
import htc550605125.boxmover.common.element.ElementSet;
import htc550605125.boxmover.common.exception.BoxMoverBaseException;
import htc550605125.boxmover.common.stage.Stage;
import htc550605125.boxmover.common.stage.StageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 11:02 AM
 */
public class LocalFileMapReader extends MapReader {
    private final static Logger logger = LogManager.getLogger("server");
    private final static Config conf = Config.getConfig();
    private static final HashMap<Character, ElementSet> keys = new HashMap<Character, ElementSet>();
    private static LocalFileMapReader instance = null;

    public static LocalFileMapReader getInstance() {
        if (instance == null) {
            JsonNode arr = conf.getNode("server/file maps/config");
            for (Iterator<String> it = arr.getFieldNames(); it.hasNext();) {
                String name = it.next();
                keys.put(name.charAt(0), ElementSet.valueOf(arr.get(name).asText()));
            }
            instance = new LocalFileMapReader();
        }
        return instance;
    }

    private LocalFileMapReader() {
        super(conf.getNode("server/file maps/maps"));
    }

    public Stage parse(String path, String mapID, String title) throws IOException{
        try {
            Scanner scanner = new Scanner(new FileReader(new File(path)));
            int m = scanner.nextInt(), n = scanner.nextInt();
            scanner.nextLine();
            Dim2D dim = new Dim2D(n, m);
            Stage ret = new Stage(new StageInfo(dim, mapID, title));
            for (int x = 0; x < n; ++x) {
                String line = scanner.nextLine();
                for (int y = 0; y < m; ++y)
                    ret.setPos(dim.newVector(x, y), keys.get(line.charAt(y)));
            }
            return ret;
        }
        catch (BoxMoverBaseException e) {
            logger.fatal(e);
            System.exit(-1);
        }
        return null;
    }
}