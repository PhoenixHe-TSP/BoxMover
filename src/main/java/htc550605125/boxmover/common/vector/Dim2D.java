package htc550605125.boxmover.common.vector;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/29/13
 * Time: 11:39 AM
 */

/**
 * Two-dimensional game's {@link Dim}
 */
public class Dim2D extends Dim implements Serializable, Cloneable {
    /**
     * Define the dimension
     */
    public final int x, y;

    public Dim2D(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * You can only construct a {@link Vector} from
     * a dimension.
     *
     * @see Vector2D
     */
    public Vector2D newVector(int x, int y) {
        return new Vector2D(x, y, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMax() {
        return x * y;
    }

    @Override
    public boolean equals(Dim dim) {
        return dim instanceof Dim2D && equals((Dim2D) dim);
    }

    public boolean equals(Dim2D dim) {
        return dim.x == x && dim.y == y;
    }

    // return All the possible {@link Vector}
    @Override
    protected Vector[] _getAllVectors() {
        Vector[] ret = new Vector[x * y];
        int p = 0;
        for (int i = 0; i < x; ++i)
            for (int j = 0; j < y; ++j)
                ret[p++] = newVector(i, j);
        return ret;
    }

    /* return All the possible directions as {@link Vector}
       for the base class {@link Dim} to decorate
    */
    @Override
    protected Vector[] _getAllDirections() {
        return new Vector[]{Vector2D.UP, Vector2D.DOWN, Vector2D.LEFT, Vector2D.RIGHT};
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
