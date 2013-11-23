package htc550605125.boxmover.common.stage;

import htc550605125.boxmover.common.Utils;
import htc550605125.boxmover.common.vector.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/23/13
 * Time: 7:51 PM
 */

/**
 * Base class of a stage.
 */
public class StageBase implements Serializable, Cloneable {
    protected static final Logger logger = LogManager.getLogger("common");
    /**
     * Player position of the stage
     */
    protected Vector player = null;
    /**
     * Information of the stage
     */
    protected StageInfo info = null;

    public StageBase clone() {
        StageBase ret = null;
        try {
            ret = (StageBase) super.clone();
        } catch (CloneNotSupportedException e) {
            Utils.exit(e, logger, "");
        }
        return ret;
    }

    public StageBase(StageInfo info) {
        this.info = info;
    }

    public StageBase(Vector player, StageInfo info) {
        this.info = info;
        this.player = player.clone();
    }

    public final StageInfo info() {
        return info;
    }

    public final Vector player() {
        return player;
    }
}