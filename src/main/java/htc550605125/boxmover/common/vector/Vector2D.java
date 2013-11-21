package htc550605125.boxmover.common.vector;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/29/13
 * Time: 11:17 AM
 */

/** Two-dimensional vector */
public class Vector2D extends Vector implements Serializable, Cloneable {
    final public int x, y;
    private Dim2D _dim = null;
    final static Vector2D UP = new Vector2D(-1, 0);
    final static Vector2D DOWN = new Vector2D(1, 0);
    final static Vector2D LEFT = new Vector2D(0, -1);
    final static Vector2D RIGHT = new Vector2D(0, 1);
    public final static Vector2D NULL = new Vector2D(-1, -1, new Dim2D(-1, -1));

    /** Construct a 2D vector.
     * Note that you cannot construct it from outer package,
     * because the {@link Dim} information MUST always stays
     * with the vector for range check. You can only construct
     * a vector from a dimension.
     * @param dim The dimension it belongs to
     */
    Vector2D(int x, int y, Dim2D dim) {
        this.x = x; this.y = y;
        this.dim = dim;
        this._dim = dim;
    }

    /** Private constructor only for the static directions
     * that defines in this class
     */
    private Vector2D(int x, int y) {
        this(x, y, null);
    }

    public Vector2D(Vector2D v) {
        x = v.x; y = v.y;
        dim = v.dim;
    }

    /** Friendly for debug output */
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    @Override
    public Vector2D add(Vector a) {
        Vector2D v = (Vector2D) a;
        return new Vector2D(v.x + x, v.y + y, _dim);
    }

    @Override
    public Vector2D neg() {
        return new Vector2D(-x, -y, _dim);
    }

    @Override
    public boolean checkRange() {
        return x >= 0 && x < _dim.x && y >= 0 && y < _dim.y;
    }

    @Override
    public int getPos() {
        return x * _dim.y + y;
    }
}