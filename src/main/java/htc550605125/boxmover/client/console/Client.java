package htc550605125.boxmover.client.console;

import htc550605125.boxmover.common.exception.CannotMoveException;
import htc550605125.boxmover.common.exception.OutOfMapException;
import htc550605125.boxmover.common.stage.StageView;
import htc550605125.boxmover.common.stage.algorithm.CheckDead;
import htc550605125.boxmover.common.stage.algorithm.CheckGameSuccess;
import htc550605125.boxmover.common.vector.Dim2D;
import htc550605125.boxmover.server.SaveSlot;
import htc550605125.boxmover.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/21/13
 * Time: 4:33 PM
 */
public class Client {
    private final static Logger logger = LogManager.getLogger("client");

    private Server server = null;
    private boolean quited = false;

    public Client(Server server) {
        this.server = server;
    }

    public void start() {
        logger.info("Stage:" + server.getStage().info().getTitle() + " Loaded.");
        logger.info("Enter \"h\" to show help info.");
        while (!CheckGameSuccess.check(server.getStage()) && !quited) {
            if (new CheckDead().check(server.getStage()))
                logger.warn("It seems that you cannot win. History back(b) or Game restart(r) is recommended.");
            dumpStage();
            for (; ; ) {
                String cmd = Utils.getLine().toLowerCase();
                if (handleCommand(cmd)) break;
            }
        }
        if (!quited) {
            dumpStage();
            logger.info("You win~ Congratulations!");
        }
    }

    private void dumpStage() {
        StageView view = new StageView(server.getStage());
        Dim2D dim = (Dim2D) view.info().getDim();
        StringBuilder ret = new StringBuilder();
        try {
            for (int i = 0; i < dim.x; ++i) {
                for (int j = 0; j < dim.y; ++j)
                    ret.append(Utils.VIEWTEXT.get(view.get(dim.newVector(i, j))));
                ret.append('\n');
            }
        } catch (OutOfMapException e) {
            logger.fatal(e);
            logger.fatal("At " + this.getClass());
            System.exit(-1);
        }
        System.out.print(ret);
    }

    private boolean handleCommand(String cmd) {
        Dim2D dim = (Dim2D) server.getStage().player().getDim();
        try {
            if (cmd.length() == 1) {
                switch (cmd.charAt(0)) {
                    case 'w':
                        logger.info("moveElement up");
                        server.playerMove(dim.newVector(-1, 0));
                        return true;
                    case 's':
                        logger.info("moveElement down");
                        server.playerMove(dim.newVector(1, 0));
                        return true;
                    case 'a':
                        logger.info("moveElement left");
                        server.playerMove(dim.newVector(0, -1));
                        return true;
                    case 'd':
                        logger.info("moveElement right");
                        server.playerMove(dim.newVector(0, 1));
                        return true;
                    case 'b':
                        logger.info("history back");
                        server.historyBack();
                        return true;
                    case 'r':
                        logger.info("restart game");
                        server.restartGame();
                        return true;
                    case 'h':
                        logger.info("help info - !!TODO!!");
                        return false;
                    case 'q':
                        logger.info("quit game.");
                        quited = true;
                        return true;
                }
            }
            if (cmd.equals("save")) {
                logger.info("Please specific save slot (empty for random slot):");
                String slot = Utils.getLine();
                if (slot.length() == 0)
                    slot = SaveSlot.getInstance().getRandomSlot();
                SaveSlot.getInstance().save(slot, server.getStage());
                return true;
            }

            logger.error("Unknown command.Please try again");
        } catch (CannotMoveException e) {
            logger.error("Cannot moveElement " + e.element + " from " + e.src + " to " + e.dest);
        } catch (OutOfMapException e) {
            logger.error("Cannot moveElement to " + e.target + ":out of map!");
        }
        return false;
    }
}