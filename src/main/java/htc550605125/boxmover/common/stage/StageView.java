package htc550605125.boxmover.common.stage;

import htc550605125.boxmover.common.vector.Dim2D;
import htc550605125.boxmover.common.vector.Vector;
import htc550605125.boxmover.common.element.ElementView;
import htc550605125.boxmover.common.exception.OutOfMapException;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/23/13
 * Time: 1:54 PM
 */
public class StageView extends StageBase implements Serializable {
    private final ElementView[] data;

    public StageView(Stage stage) {
        super(stage.info);
        player = stage.player;
        Dim2D dim = (Dim2D) info.dim();
        data = new ElementView[dim.getMax()];
        for (int i = 0; i < dim.getMax(); ++i)
            data[i] = ElementView.valueOf(stage.data[i]);
    }

    public ElementView get(Vector v) throws OutOfMapException {
        return data[v.checkGet()];
    }
}
