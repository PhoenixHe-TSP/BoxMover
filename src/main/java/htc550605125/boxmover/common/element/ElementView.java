package htc550605125.boxmover.common.element;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/22/13
 * Time: 9:23 AM
 */
public enum ElementView {
    PLAYER_VIEW, BOX_VIEW, DEST_VIEW, WALL_VIEW, EMPTY_VIEW, BOX_DEST_VIEW;

    private final static ElementView[] from = new ElementView[65536];

    static {
        from[0] = EMPTY_VIEW;
        from[new ElementSet().add(Element.PLAYER).value()] = PLAYER_VIEW;
        from[new ElementSet().add(Element.PLAYER).add(Element.DEST).value()] = PLAYER_VIEW;
        from[new ElementSet().add(Element.BOX).value()] = BOX_VIEW;
        from[new ElementSet().add(Element.DEST).value()] = DEST_VIEW;
        from[new ElementSet().add(Element.WALL).value()] = WALL_VIEW;
        from[new ElementSet().add(Element.DEST).add(Element.BOX).value()] = BOX_DEST_VIEW;
    }

    /**
     * @return The view of the ElementSet
     */
    public static ElementView valueOf(ElementSet es) {
        return from[es.value()];
    }
}