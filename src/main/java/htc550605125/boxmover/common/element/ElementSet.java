package htc550605125.boxmover.common.element;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 11:20 AM
 */

/**
 * <pre>
 * Defines a set of elements.
 * Implement detail:
 *      It holds a int called data, each
 *      bit of the data represents a element.
 *      For example, a element set with a player
 *      and a destination is 0b101 = 5, because
 *      in {@link Element} we defines player as 0
 *      and destination as 2, so the result is
 *      2^0 + 2^2 = 5.
 * </pre>
 *
 * @see Element
 */
public class ElementSet implements Serializable {
    private int data = 0;

    public ElementSet() {
    }

    /**
     * Construct an {@link ElementSet} with one element.
     */
    public ElementSet(Element e) {
        add(e);
    }

    public ElementSet(ElementSet es) {
        data = es.data;
    }

    /**
     * @param s "PLAYER" or "BOX" or "DEST" or "WALL" or "BOX_DEST"
     */
    public static ElementSet valueOf(String s) {
        if (s.equals("PLAYER")) return new ElementSet(Element.PLAYER);
        if (s.equals("BOX")) return new ElementSet(Element.BOX);
        if (s.equals("DEST")) return new ElementSet(Element.DEST);
        if (s.equals("WALL")) return new ElementSet(Element.WALL);
        if (s.equals("BOX_DEST")) return new ElementSet(Element.BOX).add(Element.DEST);
        return new ElementSet();
    }

    public ElementSet add(Element e) {
        data |= 1 << e.value();
        return this;
    }

    public ElementSet del(Element e) {
        data &= ~(1 << e.value());
        return this;
    }

    /**
     * @return The inner data value
     */
    public final int value() {
        return data;
    }

    public boolean has(Element e) {
        return (data & (1 << e.value())) > 0;
    }

    /**
     * @return If the data is 0
     */
    public boolean isEmpty() {
        return data == 0;
    }
}
