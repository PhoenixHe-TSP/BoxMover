package htc550605125.boxmover.server;

import htc550605125.boxmover.common.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/28/13
 * Time: 8:18 AM
 */

/**
 * The management of the saves
 */
public class SaveSlot {
    private static final Logger logger = LogManager.getLogger("common");
    private static SaveSlot instance = null;
    private String saveRoot = null, suffix = null, randomValues = null;
    private HashMap<String, Object> maps = new HashMap<String, Object>();

    public static SaveSlot getInstance() {
        if (instance == null)
            instance = new SaveSlot();
        return instance;
    }

    private SaveSlot() {
        // Root directory of the save files
        saveRoot = Config.getConfig().get("saves/root");
        // File suffix of the saved files
        suffix = Config.getConfig().get("saves/suffix");
        // Range of random characters
        randomValues = Config.getConfig().get("saves/randomValues");
        loadSaves(new File(saveRoot));
    }

    public final HashMap<String, Object> getMaps() {
        return maps;
    }

    /**
     * Save the stage to the slot, the file path
     * is {saveRoot}/{slot}.{suffix} specified in the
     * configure file.
     *
     * @see Config
     */
    public void save(String slot, Object obj) {
        maps.put(slot, obj);
        slot = saveRoot + slot + suffix;
        writeSlot(new File(slot), obj);
        logger.info("Game saved to " + slot);
    }

    /**
     * @return Random generated slot name
     */
    public String getRandomSlot() {
        Random r = new Random();
        for (; ; ) {
            String ret = "";
            for (int i = -2; i < r.nextInt(2); ++i)
                ret += String.valueOf(randomValues.charAt(r.nextInt(randomValues.length())));
            if (!maps.containsKey(ret)) return ret;
        }
    }

    // Load a save slot from a file
    private void loadSaves(File fd) {
        if (fd.isFile()) {
            String name = fd.getName();
            if (name.endsWith(suffix)) {
                try {
                    name = name.substring(0, name.length() - suffix.length());
                    maps.put(name, readSlot(fd));
                } catch (IOException e) {
                    logger.warn("Cannot load save slot: " + fd.getPath() + ". Delete it.");
                    fd.delete();
                }
            }
            return;
        }
        try {
            for (File f : fd.listFiles()) loadSaves(f);
        } catch (NullPointerException e) {
        }
    }

    // Save the stage to a file
    private void writeSlot(File fd, Object obj) {
        try {
            if (!fd.exists()) fd.createNewFile();
            new ObjectOutputStream(new FileOutputStream(fd)).writeObject(obj);
        } catch (IOException e) {
            logger.fatal(e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private Object readSlot(File fd) throws IOException {
        try {
            return new ObjectInputStream(new FileInputStream(fd)).readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }
    }
}