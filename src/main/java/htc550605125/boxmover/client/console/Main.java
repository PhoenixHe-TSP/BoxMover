package htc550605125.boxmover.client.console;

import htc550605125.boxmover.common.stage.Stage;
import htc550605125.boxmover.common.stage.Stages;
import htc550605125.boxmover.server.SaveSlot;
import htc550605125.boxmover.server.Server;
import htc550605125.boxmover.server.mapreader.LocalFileMapReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 13-10-24
 * Time: 上午9:31
 */
public class Main {
    private final static Logger logger = LogManager.getLogger("client");

    public static void main(String[] argv) {
        for (;;) {
            logger.info("Welcome to htc's BoxMover console client interface.");
            Client client = new Client(new Server(getMapToLoad().clone()));
            client.start();
        }
    }

    private static void quit() {
        logger.info("bye~");
        System.exit(0);
    }

    private static Stage getMapToLoad() {
        for (;;) {
            logger.info("Do you want load map(m) or load saves(s)? Or q to quit?(m/s/q)");
            String line = Utils.getLine().toLowerCase();
            Stage s = null;
            if (line.length() == 0) continue;
            switch (line.charAt(0)) {
                case 'm':
                    s = loadLocalMap();
                    break;
                case 's':
                    s =  loadSave();
                    break;
                case 'q':
                    quit();
            }
            if (s != null) return s;
        }
    }

    private static Stage loadLocalMap() {
        Stages stages = LocalFileMapReader.getInstance().getMaps();
        logger.info("Here are available builtin maps:");
        List<Stage> tmpList =  new ArrayList<Stage>(stages.values());
        Collections.sort(tmpList);
        for (Stage stage: tmpList)
            logger.info(stage.info());
        logger.info("Please enter ID or custom path of map to load map.");
        logger.info("Or enter \"q\" to break.");
        for (;;) {
            String s = Utils.getLine();
            if (s.equals("q")) return null;
            if (stages.containsKey(s))
                return stages.get(s);
            else {
                try {
                    return LocalFileMapReader.getInstance().parse(s, "Custom Map", "Custom Map");
                } catch (IOException e) {
                    logger.error("Cannot Load Custom Map:" + s);
                }
            }
        }
    }

    private static Stage loadSave() {
        Stages stages = SaveSlot.getInstance().getMaps();
        logger.info("Here are available saves:");
        for (Map.Entry<String, Stage> slot: stages.entrySet())
            logger.info("slot:" + slot.getKey() + "    " + slot.getValue().info());
        logger.info("Please enter slot to load one save.");
        logger.info("Or enter \"q\" to break");
        for (;;) {
            String s = Utils.getLine();
            if (s.equals("q")) return null;
            if (stages.containsKey(s))
                return stages.get(s);
            logger.error("Cannot load save slot: " + s + " , please try again.");
        }
    }
}