package htc550605125.boxmover.common.exception;

import htc550605125.boxmover.common.vector.Dim;
import htc550605125.boxmover.common.vector.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 10:04 AM
 */

/**
 * Range check exception: the target is out of the {@link Dim}
 */
public class OutOfMapException extends BoxMoverBaseException {
    public final Vector target;

    public OutOfMapException(Vector t) {
        target = t;
    }
}
