package htc550605125.boxmover.common.element;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 9:42 AM
 */

/**
 * Defines the elements.
 */
public class Element implements Serializable, Cloneable {
    public final static Element PLAYER = new Element(0);
    public final static Element BOX = new Element(1);
    public final static Element DEST = new Element(2);
    public final static Element WALL = new Element(3);

    private final int nCode;

    /**
     * Init the Element with specific code.
     * PLAYER is 0
     * BOX is 1
     * DEST is 2
     * WALL is 3
     */
    public Element(int nCode) {
        this.nCode = nCode;
    }

    /**
     * @return The inner code of the {@link Element}
     */
    public final int value() {
        return nCode;
    }

    @Override
    public String toString() {
        switch (nCode) {
            case 0:
                return "player";
            case 1:
                return "box";
            case 2:
                return "dest";
            case 3:
                return "wall";
        }
        return "Element(" + nCode + ")";
    }
}
