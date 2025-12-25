import java.util.*;

/**
 * Station registry for UI + business logic.
 * UI can use getStationNames() to populate dropdowns.
 * Business logic can use stopsBetween(...) for fare calculations.
 */
public class Station {
    private final int id;
    private final String name;
    private final int index; // position order on the line

    private static final Map<Integer, Station> BY_ID = new HashMap<>();
    private static final Map<String, Station> BY_NAME = new HashMap<>();
    private static final List<Station> ALL = new ArrayList<>();

    // --- Define your stations here (replace with real names) ---
    static {
        register(new Station(1, "A1", 0));
        register(new Station(2, "A2", 1));
        register(new Station(3, "A3", 2));
        register(new Station(4, "A4", 3));
        register(new Station(5, "A5", 4));
    }

    public Station(int id, String name, int index) {
        this.id = id;
        this.name = name;
        this.index = index;
    }

    private static void register(Station s) {
        ALL.add(s);
        BY_ID.put(s.id, s);
        BY_NAME.put(normalize(s.name), s);
    }

    private static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    // ---------------- UI helpers ----------------

    /** For JComboBox: gives ["A1","A2",...] */
    public static List<String> getStationNames() {
        List<String> names = new ArrayList<>();
        for (Station s : ALL) names.add(s.name);
        return Collections.unmodifiableList(names);
    }

    /** If UI shows names, you can validate selection quickly */
    public static boolean isValidStationName(String name) {
        return BY_NAME.containsKey(normalize(name));
    }

    // ---------------- Lookups ----------------

    public static Station fromName(String stationName) {
        Station s = BY_NAME.get(normalize(stationName));
        if (s == null) throw new IllegalArgumentException("Unknown station: " + stationName);
        return s;
    }

    public static Station fromId(int id) {
        Station s = BY_ID.get(id);
        if (s == null) throw new IllegalArgumentException("Unknown station id: " + id);
        return s;
    }

    public static List<Station> getAllStations() {
        return Collections.unmodifiableList(ALL);
    }

    // ---------------- Business helper ----------------

    /** Number of stops between stations (based on index difference) */
    public static int stopsBetween(String startStation, String endStation) {
        Station a = fromName(startStation);
        Station b = fromName(endStation);
        return Math.abs(a.index - b.index);
    }

    // ---------------- Getters ----------------

    public int getId() { return id; }
    public String getName() { return name; }
    public int getIndex() { return index; }

    @Override
    public String toString() {
        // GUI can display Station directly and it’ll show name
        return name;
    }
}
