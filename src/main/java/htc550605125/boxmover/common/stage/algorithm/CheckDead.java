package htc550605125.boxmover.common.stage.algorithm;

import htc550605125.boxmover.common.Utils;
import htc550605125.boxmover.common.element.Element;
import htc550605125.boxmover.common.element.ElementSet;
import htc550605125.boxmover.common.exception.BoxMoverBaseException;
import htc550605125.boxmover.common.exception.CannotConvertException;
import htc550605125.boxmover.common.exception.OutOfMapException;
import htc550605125.boxmover.common.stage.Stage;
import htc550605125.boxmover.common.vector.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 11/2/13
 * Time: 7:33 PM
 */

/**
 * <pre>
 * Algorithm that check if the player is unable to win
 * The algorithm is a bit weak that it could not checkout
 * all the fail situation, but quiet a lot.
 * Algorithm detail:
 *      TODO
 * </pre>
 */
public class CheckDead {
    private final static Logger logger = LogManager.getLogger("server");
    private Stage stage = null;

    public boolean check(Stage _stage) {
        stage = _stage.clone();
        int boxRemain = 0;
        for (ElementSet es : stage.getRawData())
            if (shouldCount(es))
                ++boxRemain;

        Queue<Vector> queue = new ArrayDeque<Vector>();
        int touchableBox = 0;
        boolean[] vis = new boolean[stage.info().getDim().getMax()];
        queue.offer(stage.player());
        vis[stage.player().getPos()] = true;
        while (!queue.isEmpty()) {
            Vector x = queue.poll();
            for (Vector v : x.getDim().getAllDirections()) {
                try {
                    Vector u = x.add(v);
                    if (vis[u.getPos()]) continue;
                    if (playerCannotMove(x, v)) continue;
                    queue.offer(u);
                    vis[u.getPos()] = true;
                    ElementSet es = stage.getPos(u);
                    if (shouldCount(es)) {
                        ++touchableBox;
                        es.del(Element.BOX);
                    }
                } catch (BoxMoverBaseException e) {
                    Utils.exit(e, logger, "");
                }
            }
        }
        return boxRemain != touchableBox;
    }

    private boolean shouldCount(ElementSet es) {
        return es.has(Element.BOX) && !es.has(Element.DEST);
    }

    private boolean playerCannotMove(Vector player, Vector d) throws CannotConvertException {
        Vector d1 = player.add(d), d2 = d1.add(d);
        try {
            ElementSet Elem0 = stage.getPos(d1);

            if (Elem0.has(Element.WALL))
                return true;
            if (Elem0.has(Element.BOX)) {
                ElementSet Elem1 = stage.getPos(d2);
                if (Elem1.has(Element.BOX) || Elem1.has(Element.WALL))
                    return true;
            }
        } catch (OutOfMapException e) {
            return true;
        }
        return false;
    }
}