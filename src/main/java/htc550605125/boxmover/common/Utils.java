package htc550605125.boxmover.common;

import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 11/23/13
 * Time: 2:17 PM
 */

/**
 * Some helper functions
 */
public class Utils {
    /**
     * Exit the program noisy, There must be something wrong happened if this function is invoked.
     *
     * @param e      The source of the error
     * @param logger Put some information to the logger
     * @param info   Add some information to the logger
     */
    public static void exit(Exception e, Logger logger, String info) {
        e.printStackTrace();
        logger.fatal(e);
        if (info.length() > 0) logger.info(info);
        System.exit(-1);
    }
}
