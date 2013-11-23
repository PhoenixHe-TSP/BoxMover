package htc550605125.boxmover.common.vector;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/29/13
 * Time: 11:38 AM
 */

/**
 * <pre>
 * Abstract Dimension Class
 * It defines the dimension of the {@link Vector}
 * </pre>
 *
 * @see Vector
 */
public abstract class Dim implements Serializable, Cloneable {
    // All the possible vectors in this dimension
    private Vector[] allVectors = null;
    // All the directions that could move in this dimension
    private Vector[] allDirections = null;

    /**
     * @return All the directions in this dimension as {@link Vector}
     *         decorated with this Dim
     */
    public final Vector[] getAllDirections() {
        if (allDirections == null) {
            allDirections = _getAllDirections();
            //Decorate all the vector of directions with this dimension
            for (Vector x : allDirections) x.dim = this;
        }
        return allDirections;
    }

    /**
     * @return All the {@link Vector} in this dimension.
     *         decorated with this Dim
     */
    public final Vector[] getAllVectors() {
        if (allVectors == null) {
            allVectors = _getAllVectors();
            // Decorate all the vector with this dimension
            for (Vector x : allVectors) x.dim = this;
        }
        return allVectors;
    }

    /**
     * @return The number of all the {@link Vector} in this dimension
     */
    public abstract int getMax();

    public abstract boolean equals(Dim dim);

    protected abstract Vector[] _getAllVectors();

    protected abstract Vector[] _getAllDirections();
}
