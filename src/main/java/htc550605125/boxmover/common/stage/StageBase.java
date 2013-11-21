package htc550605125.boxmover.common.stage;

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
public class StageBase implements Serializable, Cloneable {
    protected static final Logger logger = LogManager.getLogger("common");
    protected Vector player = null;
    protected StageInfo info = null;

    public StageBase clone() {
        StageBase ret = null;
        try {
            ret = (StageBase) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(-1);
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

    public final StageInfo info(){
        return info;
    }

    public final Vector player() {
        return player;
    }
}