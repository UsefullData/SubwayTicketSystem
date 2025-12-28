package ui;

import javax.swing.*;
import java.awt.*;

public class TicketPurchaseUI extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    // Screens
    private final WelcomeScreen welcomeScreen;
    private final StationSelectionScreen stationScreen;
    private final PaymentScreen paymentScreen;
    private final TicketDisplayScreen ticketScreen;

    public TicketPurchaseUI() {
        setTitle("Hangzhou Metro Line 3 - Ticket System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        welcomeScreen = new WelcomeScreen(this);
        stationScreen = new StationSelectionScreen(this);
        paymentScreen = new PaymentScreen(this);
        ticketScreen = new TicketDisplayScreen(this);

        mainPanel.add(welcomeScreen, "WELCOME");
        mainPanel.add(stationScreen, "STATION");
        mainPanel.add(paymentScreen, "PAYMENT");
        mainPanel.add(ticketScreen, "TICKET");

        setContentPane(mainPanel);
        showScreen("WELCOME");
    }

    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }

    public StationSelectionScreen getStationScreen() {
        return stationScreen;
    }

    public PaymentScreen getPaymentScreen() {
        return paymentScreen;
    }

    public TicketDisplayScreen getTicketScreen() {
        return ticketScreen;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicketPurchaseUI().setVisible(true));
    }
}
