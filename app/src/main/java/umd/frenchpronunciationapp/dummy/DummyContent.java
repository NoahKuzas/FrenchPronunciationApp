package umd.frenchpronunciationapp.dummy;

import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umd.frenchpronunciationapp.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    /*public void listRaw(){
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
           addItem(new DummyItem (, fields[count]));
        }


    }*/

    static {
        // File file = new File ("res/raw/")
        // Add 3G sample items.

        addItem(new DummyItem("1", "Play 1"));
        addItem(new DummyItem("2", "Play 2"));
        addItem(new DummyItem("3", "Play 3"));
        addItem(new DummyItem("4", "Play 4"));
        addItem(new DummyItem("5", "Play 5"));
        addItem(new DummyItem("6", "Play 6"));
        addItem(new DummyItem("7", "Play 7"));
        addItem(new DummyItem("8", "Play 8"));
        addItem(new DummyItem("9", "Play 9"));
        addItem(new DummyItem("10", "Play 10"));
        addItem(new DummyItem("11", "Play 11"));
        addItem(new DummyItem("12", "Play 12"));
        addItem(new DummyItem("13", "Play 13"));
        addItem(new DummyItem("14", "Play 14"));
        addItem(new DummyItem("15", "Play 15"));
        addItem(new DummyItem("16", "Play 16"));
        addItem(new DummyItem("17", "Play 17"));
        addItem(new DummyItem("18", "Play 18"));
        addItem(new DummyItem("19", "Play 19"));
        addItem(new DummyItem("20", "Play 20"));
        addItem(new DummyItem("21", "Play 21"));
        addItem(new DummyItem("22", "Play 22"));
        addItem(new DummyItem("23", "Play 23"));
        addItem(new DummyItem("24", "Play 24"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;

        public DummyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
