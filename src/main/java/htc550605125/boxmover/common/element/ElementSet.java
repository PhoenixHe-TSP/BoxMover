package htc550605125.boxmover.common.element;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 11:20 AM
 */
public class ElementSet implements Serializable {
    private int data = 0;

    public ElementSet() {}

    public ElementSet(Element e) {
        add(e);
    }

    public ElementSet(ElementSet es) {
        data = es.data;
    }

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

    public final int value() {
        return data;
    }

    public boolean has(Element e) {
        return (data & (1 << e.value())) > 0;
    }

    public boolean isEmpty() {
        return data == 0;
    }
}
