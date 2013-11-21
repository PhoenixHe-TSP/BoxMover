package htc550605125.boxmover.server;

import htc550605125.boxmover.common.Config;
import htc550605125.boxmover.common.stage.Stage;
import htc550605125.boxmover.common.stage.Stages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/28/13
 * Time: 8:18 AM
 */
public class SaveSlot {
    private static final Logger logger = LogManager.getLogger("common");
    private static SaveSlot instance = null;
    private String saveRoot = null, suffix = null, randomValues = null;
    private final Stages maps = new Stages();

    public static SaveSlot getInstance() {
        if (instance == null)
            instance = new SaveSlot();
        return instance;
    }

    private SaveSlot()  {
        saveRoot = Config.getConfig().get("saves/root");
        suffix = Config.getConfig().get("saves/suffix");
        randomValues = Config.getConfig().get("saves/randomValues");
        loadSaves(new File(saveRoot));
    }

    public final Stages getMaps() {
        return maps;
    }

    public void saveStage(String slot, Stage stage) {
        slot = saveRoot + slot + suffix;
        writeSlot(new File(slot), stage);
        String ID = stage.info().ID();
        maps.put(ID, stage);
        logger.info("Game saved to " + slot);
    }

    public String getRandomSlot() {
        Random r = new Random();
        for (;;) {
            String ret = "";
            for (int i = -2; i < r.nextInt(2); ++i)
                ret += String.valueOf(randomValues.charAt(r.nextInt(randomValues.length())));
            if (!maps.containsKey(ret)) return ret;
        }
    }

    private void loadSaves(File fd) {
        if (fd.isFile()) {
            String name = fd.getName();
            if (name.endsWith(suffix)) {
                try {
                    name = name.substring(0, name.length() - suffix.length());
                    maps.put(name, readSlot(fd));
                }
                catch (IOException e) {
                    logger.warn("Cannot load save slot: " + fd.getPath() + ". Delete it.");
                    fd.delete();
                }
            }
            return;
        }
        try {
            for (File f : fd.listFiles()) loadSaves(f);
        }
        catch (NullPointerException e) {
        }
    }

    private void writeSlot(File fd, Stage stage) {
        try {
            if (!fd.exists()) fd.createNewFile();
            new ObjectOutputStream(new FileOutputStream(fd)).writeObject(stage);
        }
        catch (IOException e) {
            logger.fatal(e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private Stage readSlot(File fd) throws IOException {
        try {
            return (Stage) new ObjectInputStream(new FileInputStream(fd)).readObject();
        }
        catch (ClassNotFoundException e) {
            throw new IOException();
        }
    }
}