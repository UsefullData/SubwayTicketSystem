package model;
import java.util.*;

public class Station {
    private final int id;
    private final String name;   // what UI shows (and what Ticket stores)
    private final int index;     // order along the line

    private static final Map<String, Station> BY_NAME = new HashMap<>();
    private static final List<Station> ALL = new ArrayList<>();

    // Hangzhou Metro Line 3 (ordered)
    static {
        register(1,  "Wushanqiancun",         0);
        register(2,  "West Railway Station",  1);
        register(3,  "North Longzhou Road",   2);
        register(4,  "West Wenyi Road",       3);
        register(5,  "Lvting Road",           4);
        register(6,  "Quanfeng",              5);
        register(7,  "Gaojiao Road",          6);
        register(8,  "Liansheng Road",        7);
        register(9,  "Hongyuan",              8);
        register(10, "Shima",                 9);
        register(11, "Xiaoheshan",            10);
        register(12, "Pingfeng",              11);
        register(13, "Liuxia",                12);
        register(14, "South Xixi Wetland",    13);
        register(15, "Huawu",                 14);
        register(16, "Dongyue",               15);
        register(17, "Gudun Road",            16);
        register(18, "Gudang Xincun",         17);
        register(19, "Gudang",                18);
        register(20, "Huanglong Sports Center", 19);
        register(21, "Huanglong Cave",        20);
        register(22, "Wulinmen",              21);
        register(23, "Wulin Square",          22);
        register(24, "West Lake Cultural Square", 23);
        register(25, "Chaowang Road",         24);
        register(26, "Xiangji Temple",        25);
        register(27, "Daguan",                26);
        register(28, "Shanxian",              27);
        register(29, "Xintiandi Street",      28);
        register(30, "Qilun Square",          29);
        register(31, "Huafeng Road",          30);
        register(32, "Tongxie Road",          31);
        register(33, "Taohuahu Park",         32);
        register(34, "Dingqiao",              33);
        register(35, "Huahe Street",          34);
        register(36, "Huangheshan",           35);
        register(37, "Xingqiao",              36);
    }

    private Station(int id, String name, int index) {
        this.id = id;
        this.name = name;
        this.index = index;
    }

    private static void register(int id, String englishName, int index) {
        Station s = new Station(id, englishName, index);
        ALL.add(s);

        // accept English input from UI
        BY_NAME.put(normalize(englishName), s);
    }

    private static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    // ----- UI -----
    public static List<String> getStationNames() {
        // Choose English list for UI
        List<String> names = new ArrayList<>();
        for (Station s : ALL) names.add(s.name);
        return Collections.unmodifiableList(names);
    }

    public static boolean isValidStationName(String name) {
        return BY_NAME.containsKey(normalize(name));
    }

    // ----- Business -----
    public static Station fromName(String stationName) {
        Station s = BY_NAME.get(normalize(stationName));
        if (s == null) throw new IllegalArgumentException("Unknown station: " + stationName);
        return s;
    }

    public static int stopsBetween(String startStation, String endStation) {
        Station a = fromName(startStation);
        Station b = fromName(endStation);
        return Math.abs(a.index - b.index);
    }

    // ----- Getters -----
    public int getId() { return id; }
    public String getName() { return name; }
    public int getIndex() { return index; }

    @Override
    public String toString() { return name; }
}
