package htc550605125.boxmover.common.stage;

import htc550605125.boxmover.common.element.Element;
import htc550605125.boxmover.common.element.ElementSet;
import htc550605125.boxmover.common.exception.CannotMoveException;
import htc550605125.boxmover.common.exception.OutOfMapException;
import htc550605125.boxmover.common.vector.Dim;
import htc550605125.boxmover.common.vector.Vector;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/23/13
 * Time: 1:51 PM
 */

/**
 * <pre>
 * The game stage.
 * It only contains the basic control of abstract {@link Element}.
 * It has an one-dimensional array of {@link ElementSet}, and
 * here is the implement detail of element management:
 *      First we have the concept of element(player, wall, box, destination).
 *      They are defined in {@link Element}. Then, every grid of the game
 *      stage is a {@link ElementSet} which contains a set of {@link Element}.
 *      The {@link Stage} manages the ElementSets in an one-dimensional array,
 *      because the {@link Stage} could be used without the actually dimension
 *      information. In other words, the {@link Stage} could run with either
 *      2D-box-moving game or 3D-box-moving game. The trick is in {@link Dim}
 *      and {@link Vector}.
 * </pre>
 *
 * @see Dim
 * @see Vector
 */
public class Stage extends StageBase implements Serializable, Cloneable, Comparable<Stage> {
    private ElementSet data[] = null;

    @Override
    public Stage clone() {
        super.clone();
        Stage ret = new Stage(info);
        ret.player = player;
        for (int i = 0; i < data.length; ++i)
            ret.data[i] = new ElementSet(data[i]);
        return ret;
    }

    /**
     * Create an empty stage with the {@link StageInfo}
     */
    public Stage(StageInfo info) {
        super(info);
        data = new ElementSet[info.getDim().getMax()];
    }

    /**
     * Compare two stages by their ID in their {@link StageInfo}
     */
    @Override()
    public int compareTo(Stage s) {
        return info.compareTo(s.info);
    }

    /**
     * @param v The position of the stage you want to get
     * @return An {@link ElementSet} of the specific grid of the game
     * @throws OutOfMapException
     */
    public final ElementSet getPos(Vector v) throws OutOfMapException {
        return data[v.checkGet()];
    }

    /**
     * @return The whole data array. Can be used for dimension-free operation.
     */
    public final ElementSet[] getRawData() {
        return data;
    }

    /**
     * Set the specific grid to the specific ElementSet
     *
     * @param v  The position of the grid
     * @param es The ElementSet you want to put
     * @return This Stage
     * @throws OutOfMapException
     * @throws CannotMoveException
     */
    public Stage setPos(Vector v, ElementSet es) throws OutOfMapException, CannotMoveException {
        if (es.has(Element.PLAYER)) {
            if (player != null)
                throw new CannotMoveException(Element.PLAYER, player, v);
            player = v;
        }
        data[v.checkGet()] = new ElementSet(es);
        return this;
    }

    /**
     * Move Element e from src to dest
     *
     * @return This stage
     * @throws OutOfMapException   - src or dest is out of the stage
     * @throws CannotMoveException - Element is not in src or element is in dest
     */
    public Stage moveElement(Element e, Vector src, Vector dest) throws OutOfMapException, CannotMoveException {
        ElementSet s = getPos(src), d = getPos(dest);
        if (!s.has(e) || d.has(e))
            throw new CannotMoveException(e, src, dest);
        s.del(e);
        d.add(e);
        if (e == Element.PLAYER) player = dest;
        return this;
    }

    public boolean validate() {
        if (player == null) return false;
        int destCnt = 0, boxCnt = 0;
        for (ElementSet es : data) {
            if (es.has(Element.BOX)) ++boxCnt;
            if (es.has(Element.DEST)) ++destCnt;
        }
        return boxCnt == destCnt;
    }
}