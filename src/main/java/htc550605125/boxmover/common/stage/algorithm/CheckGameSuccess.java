package htc550605125.boxmover.common.stage.algorithm;

import htc550605125.boxmover.common.element.Element;
import htc550605125.boxmover.common.element.ElementSet;
import htc550605125.boxmover.common.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/29/13
 * Time: 1:51 PM
 */

/**
 * <pre>
 * Algorithm to check if the player wins
 * Algorithm detail:
 *      if there exists a position that
 *      only a box or a destination is on it, the
 *      player is not win, otherwise, the player wins.
 * </pre>
 */
public class CheckGameSuccess {
    public static boolean check(Stage s) {
        for (ElementSet es : s.getRawData())
            if (es.has(Element.BOX) ^ es.has(Element.DEST))
                return false;
        return true;
    }
}
