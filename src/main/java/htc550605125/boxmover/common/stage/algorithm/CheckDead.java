package htc550605125.boxmover.common.stage.algorithm;

import htc550605125.boxmover.common.element.Element;
import htc550605125.boxmover.common.element.ElementSet;
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
public class CheckDead {
    private final static Logger logger = LogManager.getLogger("server");
    private final Stage stage;

    public CheckDead(Stage stage) {
        this.stage = (Stage)stage.clone();
    }

    public boolean check() {
        int boxRemain = 0;
        for (ElementSet es : stage.getRawData())
            if (shouldCount(es))
                ++boxRemain;

        Queue<Vector> queue = new ArrayDeque<Vector>();
        int touchableBox = 0;
        boolean[] vis = new boolean[stage.info().dim().getMax()];
        queue.offer(stage.player());
        vis[stage.player().getPos()] = true;
        while (!queue.isEmpty()) {
            Vector x = queue.poll();
            for (Vector v : x.getDim().getAllDirections()) {
                Vector u = x.add(v);
                if (vis[u.getPos()]) continue;
                if (!tryPlayerMove(x, v)) continue;
                queue.offer(u);
                vis[u.getPos()] = true;
                try {
                    ElementSet es = stage.getPos(u);
                    if (shouldCount(es)) {
                        ++touchableBox;
                        es.del(Element.BOX);
                    }
                }
                catch (OutOfMapException e) {
                    e.printStackTrace();
                    logger.fatal(e);
                    System.exit(-1);
                }
            }
        }
        return boxRemain != touchableBox;
    }

    private boolean shouldCount(ElementSet es) {
        return es.has(Element.BOX) && !es.has(Element.DEST);
    }

    private boolean tryPlayerMove(Vector player, Vector d) {
        Vector d1 = player.add(d), d2 = d1.add(d);
        try {
            ElementSet Elem0 = stage.getPos(d1);

            if (Elem0.has(Element.WALL))
                return false;
            if (Elem0.has(Element.BOX)) {
                ElementSet Elem1 = stage.getPos(d2);
                if (Elem1.has(Element.BOX) || Elem1.has(Element.WALL))
                    return false;
            }
        }
        catch (OutOfMapException e) {
            return false;
        }
        return true;
    }
}