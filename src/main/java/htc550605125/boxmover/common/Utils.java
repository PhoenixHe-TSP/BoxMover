package htc550605125.boxmover.common;

import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 11/23/13
 * Time: 2:17 PM
 */
public class Utils {
    public static void exit(Exception e, Logger logger, String info) {
        e.printStackTrace();
        logger.fatal(e);
        if (info.length() > 0) logger.info(info);
        System.exit(-1);
    }
}
