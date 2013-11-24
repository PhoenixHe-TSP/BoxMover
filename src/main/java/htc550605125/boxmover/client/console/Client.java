package htc550605125.boxmover.client.console;

import htc550605125.boxmover.common.exception.CannotConvertException;
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

/**
 * A simple console client. It connects to
 * server by running native java codes of server.
 */
public class Client {
    private final static Logger logger = LogManager.getLogger("client");

    private Server server = null;
    private boolean quited = false;

    public Client(Server server) {
        this.server = server;
    }

    /**
     * <pre>
     * Play game of one stage.
     * It will exit if:
     *      1. The player wins
     *      2. The player exited
     * </pre>
     */
    public void start() {
        logger.info("Stage:" + server.getStage().info().getTitle() + " Loaded.");
        logger.info("Enter \"h\" to show help info.");
        while (!CheckGameSuccess.check(server.getStage()) && !quited) {
            // check if the player could not win.
            if (new CheckDead().check(server.getStage()))
                logger.warn("It seems that you cannot win. History back(b) or Game restart(r) is recommended.");
            logger.info("You have moved " + server.getStage().info().getMoveSteps() + " step(s)");
            dumpStage();
            for (;;) {
                String cmd = Utils.getLine().toLowerCase();
                // Try to handle long command
                if (handleCommand(cmd)) break;
                boolean needBreak = false;
                // Handle every character of the command
                for (Byte x : cmd.getBytes()) {
                    needBreak |= handleSingleCommand((char) x.byteValue());
                }
                if (needBreak) break;
            }
        }
        if (!quited) {
            dumpStage();
            logger.info("You pass this stage in " + server.getStage().info().getMoveSteps() + " step(s)~ Congratulations!");
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
            htc550605125.boxmover.common.Utils.exit(e, logger, "");
        }
        System.out.print(ret);
    }

    private void saveGame() {
        logger.info("Please specific save slot (empty for random slot):");
        String slot = Utils.getLine();
        if (slot.length() == 0)
            // Generate random save slot
            slot = SaveSlot.getInstance().getRandomSlot();
        SaveSlot.getInstance().save(slot, server.getStage());
    }

    private void backNSteps() {
        try {
            logger.info("History back N steps - Please input N:");
            int n = Integer.parseInt(Utils.getLine());
            while (n-- > 0) server.historyBack();
        } catch (NumberFormatException e) {
            logger.error("Invalid input");
        } catch (CannotMoveException e) {
            logger.warn("History record is empty");
        }
    }

    private void dumpHelpInfo() {
        logger.info("Command List:");
        logger.info("w      move up");
        logger.info("a      move left");
        logger.info("s      move down");
        logger.info("d      move right");
        logger.info("h      print this help information");
        logger.info("q      quit the game stage");
        logger.info("r      restart the game stage");
        logger.info("b      history back");
        logger.info("back   history back n steps");
        logger.info("save   save the current stage to a save slot");
        logger.info("");
        logger.info("Note:  the commands are not case sensitive.");
        logger.info("       you can type combinations of single characters,");
        logger.info("       e.g. \"wshb\" will move up then move down then dump help info then history back.");
        logger.info("       and \"wsaadsswdd\" will solve the first local map");
    }

    private boolean handleSingleCommand(char ch) {
        Dim2D dim = (Dim2D) server.getStage().player().getDim();
        try {
            switch (ch) {
                case 'w':
                    logger.info("Move up");
                    server.playerMove(dim.newVector(-1, 0));
                    return true;
                case 's':
                    logger.info("Move down");
                    server.playerMove(dim.newVector(1, 0));
                    return true;
                case 'a':
                    logger.info("Move left");
                    server.playerMove(dim.newVector(0, -1));
                    return true;
                case 'd':
                    logger.info("Move right");
                    server.playerMove(dim.newVector(0, 1));
                    return true;
                case 'b':
                    logger.info("History back");
                    try {
                        server.historyBack();
                    } catch (CannotMoveException e) {
                        logger.warn("History record is empty");
                    }
                    return true;
                case 'r':
                    logger.info("Restart game stage");
                    server.restartGame();
                    return true;
                case 'h':
                    dumpHelpInfo();
                    return false;
                case 'q':
                    logger.info("Quit game stage");
                    quited = true;
                    return true;
            }
            logger.error("Unknown command.");
        } catch (CannotMoveException e) {
            logger.error("Cannot move " + e.element + " from " + e.src + " to " + e.dest);
        } catch (OutOfMapException e) {
            logger.error("Cannot move to " + e.target + " : out of map!");
        } catch (CannotConvertException e) {
            htc550605125.boxmover.common.Utils.exit(e, logger, "");
        }
        return false;
    }

    private boolean handleCommand(String cmd) {
        if (cmd.equals("save")) {
            saveGame();
            return true;
        }
        if (cmd.equals("back")) {
            backNSteps();
            return true;
        }
        return false;
    }
}