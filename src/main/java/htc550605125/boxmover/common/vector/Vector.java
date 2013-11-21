package htc550605125.boxmover.common.vector;

import htc550605125.boxmover.common.exception.OutOfMapException;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 9:39 AM
 */

/**
 * Abstract Vector Base Class
 */
public abstract class Vector implements Serializable, Cloneable {
    /**
     * The {@link Dim} it belongs to.
     */
    Dim dim = null;

    public Vector clone() {
        Vector ret = null;
        try {
            ret = (Vector) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return ret;
    }

    public Dim getDim() {
        return dim;
    }

    /**
     * Check if this vector is in the {@link Dim} and
     * return the reference position of the {@link Dim}
     *
     * @return The reference position in Stage.Data[]
     * @throws OutOfMapException
     * @see htc550605125.boxmover.common.stage.Stage
     */
    public int checkGet() throws OutOfMapException {
        if (!checkRange())
            throw new OutOfMapException(this);
        return getPos();
    }

    /**
     * Add another Vector to this Vector
     */
    public abstract Vector add(Vector a);

    /**
     * @return The opposite Vector
     */
    public abstract Vector neg();

    /**
     * @return The reference position in Stage.Data[] without range check
     * @see htc550605125.boxmover.common.stage.Stage
     */
    public abstract int getPos();

    // If the vector is in the dimension
    protected abstract boolean checkRange();
}


