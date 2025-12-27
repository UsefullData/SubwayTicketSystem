package ui;

import model.Ticket;
import utils.NumberFormatter;

import javax.swing.*;
import java.awt.*;

public class TicketDisplayScreen extends JPanel {
    private final TicketPurchaseUI mainFrame;

    private JLabel routeLabel;
    private JLabel timeLabel;
    private JLabel priceLabel;
    private JLabel changeLabel;

    public TicketDisplayScreen(TicketPurchaseUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(242, 242, 242)); // Light gray background
        initComponents();
    }

    private void initComponents() {
        JPanel header = new JPanel();
        header.setBackground(new Color(155, 89, 182)); // Purple header
        header.setPreferredSize(new Dimension(800, 80));

        JLabel title = new JLabel("Your Ticket");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(242, 242, 242)); // Light gray background for content
        content.setBorder(BorderFactory.createEmptyBorder(35, 120, 35, 120));

        JPanel card = new JPanel(new GridLayout(4, 1, 8, 8));
        card.setBackground(new Color(245, 245, 245)); // Light gray card background
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 2),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        card.setMaximumSize(new Dimension(560, 240));

        routeLabel = new JLabel("Route: - → -", SwingConstants.CENTER);
        routeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        timeLabel = new JLabel("Issued at: -", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        priceLabel = new JLabel("Price: -", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        priceLabel.setForeground(new Color(41, 128, 185)); // Blue for price

        changeLabel = new JLabel("Change: -", SwingConstants.CENTER);
        changeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        card.add(routeLabel);
        card.add(timeLabel);
        card.add(priceLabel);
        card.add(changeLabel);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        actions.setBackground(new Color(242, 242, 242)); // Light gray background for action buttons

        JButton newTicket = new JButton("Buy Another Ticket");
        JButton home = new JButton("Home");
        JButton exit = new JButton("Exit");

        for (JButton b : new JButton[]{newTicket, home, exit}) {
            b.setFont(new Font("Arial", Font.BOLD, 16));
            b.setPreferredSize(new Dimension(200, 45));
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        newTicket.setBackground(new Color(46, 204, 113)); // Green for "Buy Another Ticket"
        newTicket.setForeground(Color.WHITE);

        home.setBackground(new Color(52, 152, 219)); // Blue for "Home"
        home.setForeground(Color.WHITE);

        exit.setBackground(new Color(231, 76, 60)); // Red for "Exit"
        exit.setForeground(Color.WHITE);

        newTicket.addActionListener(e -> {
            mainFrame.getStationScreen().reset();
            mainFrame.showScreen("STATION");
        });
        home.addActionListener(e -> mainFrame.showScreen("WELCOME"));
        exit.addActionListener(e -> System.exit(0));

        actions.add(newTicket);
        actions.add(home);
        actions.add(exit);

        content.add(card);
        content.add(Box.createRigidArea(new Dimension(0, 25))); // Space between card and actions
        content.add(actions);

        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    public void displayTicket(Ticket ticket, int changeAmount) {
        if (ticket == null) return;

        routeLabel.setText("Route: " + ticket.getStartStation() + " → " + ticket.getEndStation());
        timeLabel.setText("Issued at: " + ticket.getIssueTime());
        priceLabel.setText("Price: " + NumberFormatter.formatCurrency(ticket.getPrice()));
        changeLabel.setText("Change: " + NumberFormatter.formatCurrency(changeAmount));
    }
}
