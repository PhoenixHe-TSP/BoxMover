package htc550605125.boxmover.client.console;

import htc550605125.boxmover.common.Config;
import htc550605125.boxmover.common.element.ElementSet;
import htc550605125.boxmover.common.element.ElementView;
import org.codehaus.jackson.JsonNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/28/13
 * Time: 7:56 AM
 */
public class Utils {
    private static final Scanner input = new Scanner(System.in);

    public final static HashMap<ElementView, String> VIEWTEXT = new HashMap<ElementView, String>();
    private static final Config conf = Config.getConfig();
    static {
        JsonNode arr = conf.getNode("client/console/config");
        for (Iterator<String> it = arr.getFieldNames(); it.hasNext();) {
            String name = it.next();
            //name -> ElementSet -> ElementView
            VIEWTEXT.put(ElementView.valueOf(ElementSet.valueOf(name)), arr.get(name).asText());
        }
    }

    public static String getLine() {
        System.out.print(">");
        return input.nextLine().trim();
    }
}
