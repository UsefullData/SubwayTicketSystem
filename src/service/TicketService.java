/**
 * TicketService: fare calculation + validation.
 */
public class TicketService {

    // You can adjust these easily
    private static final int BASE_FARE = 2;
    private static final int FARE_PER_STOP = 1;

    /**
     * Calculate fare using station names (same format Ticket uses).
     */
    public int calculateFare(String startStation, String endStation) {
        validateStations(startStation, endStation);

        int stops = Station.stopsBetween(startStation, endStation);
        return BASE_FARE + (stops * FARE_PER_STOP);
    }

    private void validateStations(String startStation, String endStation) {
        if (startStation == null || endStation == null) {
            throw new IllegalArgumentException("Stations cannot be null");
        }

        String start = startStation.trim();
        String end = endStation.trim();

        if (start.isEmpty() || end.isEmpty()) {
            throw new IllegalArgumentException("Stations cannot be empty");
        }

        if (start.equalsIgnoreCase(end)) {
            throw new IllegalArgumentException("Start and end station cannot be the same");
        }

        // Validate against station registry (prevents UI typos causing wrong fare)
        if (!Station.isValidStationName(start) || !Station.isValidStationName(end)) {
            throw new IllegalArgumentException("Invalid station name(s): " + start + ", " + end);
        }
    }
}
