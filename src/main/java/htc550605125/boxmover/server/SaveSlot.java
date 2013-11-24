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
 * <pre>
 * The management of the saves
 * Every saves are at: saveRoot/slot.suffix
 * The saveRoot and suffix can be found in config file.
 *
 * @see Config
 *      </pre>
 */
public class SaveSlot {
    private static final Logger logger = LogManager.getLogger("common");
    private static SaveSlot instance = null;
    private String saveRoot = null, suffix = null, randomValues = null;
    private final HashMap<String, Object> maps = new HashMap<String, Object>();

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
        try {
            writeSlot(new File(slot), obj);
        } catch (IOException e) {
            logger.warn("Cannot save to: " + slot);
            return;
        }
        logger.info("Game saved to " + slot);
    }

    /**
     * Delete a save slot
     *
     * @return Whether the operation is success
     */
    public boolean deleteSave(String slot) {
        if (!maps.containsKey(slot)) return false;
        maps.remove(slot);
        slot = saveRoot + slot + suffix;
        new File(slot).delete();
        return true;
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

    /**
     * Load a save slot from /saveRoot/*.suffix
     */
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
        } catch (java.lang.NullPointerException e) {
        }
    }

    /**
     * Save the stage to a file
     */
    private void writeSlot(File fd, Object obj) throws IOException {
        if (!fd.exists()) fd.createNewFile();
        new ObjectOutputStream(new FileOutputStream(fd)).writeObject(obj);
    }

    /**
     * Read one slot from a file
     */
    private Object readSlot(File fd) throws IOException {
        try {
            return new ObjectInputStream(new FileInputStream(fd)).readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }
    }
}