import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private String startStation;
    private String endStation;
    private String issueTime;
    private int price;

    public Ticket(String start, String end, String issueTime, int price) {
        this.startStation = start;
        this.endStation = end;
        this.issueTime = issueTime;
        this.price = price;
    }

    // creates a ticket with automatic issue time
    public Ticket(String start, String end, int price) {
        this.startStation = start;
        this.endStation = end;
        this.price = price;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.issueTime = LocalDateTime.now().format(formatter);
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket from " + startStation + " to " + endStation +
                " issued at " + issueTime + " with price " + price;
    }

}
