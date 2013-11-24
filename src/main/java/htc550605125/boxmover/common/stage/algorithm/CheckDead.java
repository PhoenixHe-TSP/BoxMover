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
 *      Define a box which is not on the destination as "remained box",
 *      First we count the number of the remained boxes. Then we find
 *      out how many boxes are remained and "touchable", and the player
 *      will lose if touchable boxes is less than remained boxes, We operate
 *      a breadth-first search to determine the number of touchable boxes.
 *      We define a set of accessible girds as "vis". First we add the
 *      position of the player to vis, and add it to the bfs queue either.
 *      Then, let x as a gird in the vis, assume player stands on x, tries
 *      to move in each directions. If the player COULD move regardless of the
 *      reason(empty gird or push the mox), let u = x + direction, add u to
 *      vis. And then, if there is a box on u, this algorithm shows that it
 *      could be moved to somewhere else, so it's "touchable", and we remove
 *      it from the stage because some box may not be movable(or touchable)
 *      due to this box. Finally, check whether the number of remained boxes
 *      and touchable boxes are equal.
 *
 * Thanks to the design of dimension/vector/stage system,
 * this algorithm could run regardless of the dimension of
 * the game.
 * </pre>
 */
public class CheckDead {
    private final static Logger logger = LogManager.getLogger("server");
    private Stage stage = null;

    public boolean check(Stage _stage) {
        // Clone the stage because the algorithm will do some modify on the stage
        stage = _stage.clone();

        // Count the number of remained boxes
        int boxRemain = 0;
        for (ElementSet es : stage.getRawData())
            if (shouldCount(es))
                ++boxRemain;

        // Count touchable boxes
        Queue<Vector> queue = new ArrayDeque<Vector>();
        int touchableBox = 0;
        // We could map any vector in the game dimension to a positive int less than dim.getMax()
        boolean[] vis = new boolean[stage.info().getDim().getMax()];
        queue.offer(stage.player());
        vis[stage.player().getPos()] = true;
        // bfs algorithm
        while (!queue.isEmpty()) {
            Vector x = queue.poll();
            // Try each direction of the game dimension
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

        // The player will never win if the two number are not equal
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