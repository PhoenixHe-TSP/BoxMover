package htc550605125.boxmover.common.stage;

import htc550605125.boxmover.common.element.ElementSet;
import htc550605125.boxmover.common.element.ElementView;
import htc550605125.boxmover.common.exception.OutOfMapException;
import htc550605125.boxmover.common.vector.Vector;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/23/13
 * Time: 1:54 PM
 */

/**
 * A view of a stage
 *
 * @see ElementView
 */
public class StageView extends StageBase implements Serializable {
    private final ElementView[] data;

    /**
     * Construct a view from a stage
     */
    public StageView(Stage stage) {
        super(stage.info);
        player = stage.player;
        data = new ElementView[stage.info().getDim().getMax()];
        int i = 0;
        for (ElementSet es : stage.getRawData())
            data[i++] = ElementView.valueOf(es);
    }

    public ElementView get(Vector v) throws OutOfMapException {
        return data[v.checkGet()];
    }
}
