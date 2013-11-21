package htc550605125.boxmover.common.stage;

import htc550605125.boxmover.common.vector.Dim;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: htc
 * Date: 10/23/13
 * Time: 1:59 PM
 */
public class StageInfo implements Serializable, Comparable<StageInfo> {
    private final Dim dim;
    private final String ID, title;

    public StageInfo(Dim size, String mapID, String title) {
        this.dim = size;
        this.ID = mapID;
        this.title = title;
    }

    public int compareTo(StageInfo s) {
        return ID.compareTo(s.ID);
    }

    public final Dim getDim() {
        return dim;
    }

    public final String getID() {
        return ID;
    }

    public final String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "mapID: " + ID + "   Title: " + title + "   size: " + dim;
    }
}
