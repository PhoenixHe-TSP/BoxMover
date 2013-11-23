package htc550605125.boxmover.common.exception;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 11/23/13
 * Time: 1:48 PM
 */

/**
 * Cannot convert types. Excepted one class but got another.
 */
public class CannotConvertException extends BoxMoverBaseException {
    public final Class expected, got;

    public CannotConvertException(Class exp, Class got) {
        this.expected = exp;
        this.got = got;
    }
}
