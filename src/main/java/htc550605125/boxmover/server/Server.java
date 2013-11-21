package htc550605125.boxmover.server;

import htc550605125.boxmover.common.vector.Vector;
import htc550605125.boxmover.common.element.Element;
import htc550605125.boxmover.common.element.ElementSet;
import htc550605125.boxmover.common.exception.CannotMoveException;
import htc550605125.boxmover.common.exception.OutOfMapException;
import htc550605125.boxmover.common.stage.Stage;
import htc550605125.boxmover.common.vector.Vector2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 7:10 PM
 */
public class Server {
    protected final static Logger logger = LogManager.getLogger("server");

    private Stage stage;
    private final Stack<Stage> history = new Stack<Stage>();

    public Server(Stage stage) {
        this.stage = stage;
    }

    public final Stage getStage() {
        return stage;
    }

    public void historyBack() throws CannotMoveException {
        try{
            stage = history.pop();
        }
        catch (EmptyStackException e) {
            throw new CannotMoveException(Element.PLAYER, stage.player(), Vector2D.NULL);
        }
    }

    public void restartGame() {
        try {
            for (;;) historyBack();
        }
        catch (CannotMoveException e) {
        }
    }

    public void playerMove(Vector d) throws CannotMoveException, OutOfMapException{
        history.push(new Stage(stage));

        Vector d0 = stage.player(), d1 = d0.add(d), d2 = d1.add(d);
        ElementSet Elem0 = stage.getPos(d1);
        CannotMoveException cannotMove = new CannotMoveException(Element.PLAYER, d0, d1);

        if (Elem0.has(Element.WALL)) {
            history.pop();
            throw cannotMove;
        }
        if (Elem0.has(Element.BOX)) {
            ElementSet Elem1 = stage.getPos(d2);
            if (Elem1.has(Element.BOX) || Elem1.has(Element.WALL)) {
                history.pop();
                throw cannotMove;
            }
            stage.moveElement(Element.BOX, d1, d2);
        }
        stage.moveElement(Element.PLAYER, d0, d1);
    }
}