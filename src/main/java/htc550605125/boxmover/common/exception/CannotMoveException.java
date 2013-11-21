package htc550605125.boxmover.common.exception;

import htc550605125.boxmover.common.vector.Vector;
import htc550605125.boxmover.common.element.Element;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 10:05 AM
 */
public class CannotMoveException extends BoxMoverBaseException {
    public final Vector src, dest;
    public final Element element;

    public CannotMoveException(Element e, Vector s, Vector d) {
        element = e; src = s; dest = d;
    }
}
