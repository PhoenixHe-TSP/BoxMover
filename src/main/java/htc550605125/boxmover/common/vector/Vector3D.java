package htc550605125.boxmover.common.vector;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 11/19/13
 * Time: 10:47 AM
 */

/**
 * Two-dimensional vector
 */
public class Vector3D extends Vector implements Serializable, Cloneable {
    /**
     * Three coordinates of the vector
     */
    final public int x, y, z;
    private Dim3D dim = null;
    final static Vector3D UP = new Vector3D(0, 0, 1);
    final static Vector3D DOWN = new Vector3D(0, 0, -1);
    final static Vector3D LEFT = new Vector3D(0, -1, 0);
    final static Vector3D RIGHT = new Vector3D(0, 1, 0);
    final static Vector3D FORWARD = new Vector3D(-1, 0, 0);
    final static Vector3D BACKWARD = new Vector3D(1, 0, 0);
    public final static Vector3D NULL = new Vector3D(-1, -1, -1, new Dim3D(-1, -1, -1));

    /**
     * Construct a 3D vector.
     * Note that you cannot construct it from outer package,
     * because the {@link Dim} information MUST always stays
     * with the vector for range check. You can only construct
     * a vector from a dimension.
     *
     * @param dim The dimension it belongs to
     */
    Vector3D(int x, int y, int z, Dim3D dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
        this.dim = dim;
    }

    /**
     * Private construct only for static directions
     */
    private Vector3D(int x, int y, int z) {
        this(x, y, z, null);
    }

    public Vector3D(Vector3D v) {
        x = v.x;
        y = v.y;
        z = v.z;
        dim = v.dim;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    @Override
    public Vector3D add(Vector a) {
        Vector3D v = (Vector3D) a;
        return new Vector3D(v.x + x, v.y + y, v.z + z, dim);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector3D neg() {
        return new Vector3D(-x, -y, -z, dim);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkRange() {
        return x >= 0 && x < dim.x && y >= 0 && y < dim.y && z >= 0 && z < dim.z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPos() {
        return x * dim.y * dim.z + y * dim.z + z;
    }
}