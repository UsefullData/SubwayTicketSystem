package ui;

import exceptions.InsufficientFundsException;
import model.Payment;
import model.Ticket;
import service.PaymentService;
import service.TicketService;
import utils.NumberFormatter;

import javax.swing.*;
import java.awt.*;

public class PaymentScreen extends JPanel {
    private final TicketPurchaseUI mainFrame;
    private final PaymentService paymentService = new PaymentService(new TicketService());

    private String startStation;
    private String endStation;
    private Payment payment;

    private JLabel routeLabel;
    private JLabel requiredLabel;
    private JLabel paidLabel;
    private JLabel remainingLabel;
    private JProgressBar progress;

    private JButton confirmButton;

    public PaymentScreen(TicketPurchaseUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        JPanel header = new JPanel();
        header.setBackground(new Color(242, 242, 242));
        header.setPreferredSize(new Dimension(800, 80));

        JLabel title = new JLabel("Payment");
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setForeground(new Color(50, 50, 50));
        header.add(title);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JPanel info = new JPanel(new GridLayout(3, 1, 4, 4));
        info.setBackground(new Color(242, 242, 242));
        info.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));
        info.setMaximumSize(new Dimension(640, 120));

        routeLabel = new JLabel("Route: - → -", SwingConstants.CENTER);
        routeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        requiredLabel = new JLabel("Required: ¥0", SwingConstants.CENTER);
        requiredLabel.setFont(new Font("Arial", Font.BOLD, 30));
        requiredLabel.setForeground(new Color(192, 57, 43));

        remainingLabel = new JLabel("Remaining: ¥0", SwingConstants.CENTER);
        remainingLabel.setFont(new Font("Arial", Font.PLAIN, 19));

        info.add(routeLabel);
        info.add(requiredLabel);
        info.add(remainingLabel);

        JPanel status = new JPanel(new BorderLayout(10, 8));
        status.setBackground(Color.WHITE);
        status.setMaximumSize(new Dimension(640, 80));

        paidLabel = new JLabel("Paid: ¥0", SwingConstants.CENTER);
        paidLabel.setFont(new Font("Arial", Font.BOLD, 30));
        paidLabel.setForeground(new Color(46, 204, 113));

        progress = new JProgressBar(0, 100);
        progress.setStringPainted(true);
        progress.setPreferredSize(new Dimension(600, 26));
        progress.setForeground(new Color(46, 204, 113));

        status.add(paidLabel, BorderLayout.NORTH);
        status.add(progress, BorderLayout.CENTER);

        content.add(info);
        content.add(Box.createRigidArea(new Dimension(0, 18)));
        content.add(status);
        content.add(Box.createRigidArea(new Dimension(0, 20)));

        content.add(createMoneyPanel("Insert Coins:", new int[]{1, 2, 5}));
        content.add(Box.createRigidArea(new Dimension(0, 12)));
        content.add(createMoneyPanel("Insert Bills:", new int[]{10, 20, 50, 100}));
        content.add(Box.createRigidArea(new Dimension(0, 22)));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        actions.setBackground(Color.WHITE);

        confirmButton = createActionButton("Confirm Purchase", new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setEnabled(false);
        JButton cancelButton = createActionButton("Cancel", new Color(192, 57, 43));
        JButton backButton = createActionButton("Back", new Color(149, 165, 166));

        

        confirmButton.addActionListener(e -> completePurchase());
        cancelButton.addActionListener(e -> cancelPayment());
        backButton.addActionListener(e -> cancelPayment());

        actions.add(confirmButton);
        actions.add(cancelButton);
        actions.add(backButton);

        content.add(actions);

        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    private JPanel createMoneyPanel(String title, int[] amounts) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(640, 90));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttons.setBackground(Color.WHITE);

        for (int amt : amounts) {
            JButton b = new JButton("¥" + amt);
            b.setFont(new Font("Arial", Font.BOLD, 16));
            b.setForeground(Color.WHITE);
            b.setBackground(new Color(44, 62, 80));
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setPreferredSize(new Dimension(100, 45));
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.addActionListener(e -> insertMoney(amt));
            buttons.add(b);
        }

        panel.add(label, BorderLayout.WEST);
        panel.add(buttons, BorderLayout.CENTER);
        return panel;
    }

    private JButton createActionButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(180, 45));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    /** Called by StationSelectionScreen */
    public void initializePayment(String start, String end) {
        this.startStation = start;
        this.endStation = end;

        this.payment = paymentService.createPayment(start, end);

        routeLabel.setText("Route: " + start + " → " + end);
        requiredLabel.setText("Required: " + NumberFormatter.formatCurrency(payment.getRequiredAmount()));
        confirmButton.setEnabled(false);
        updateDisplay();
    }

    private void insertMoney(int amount) {
        if (payment == null) return;

        // Your Payment supports insertCoin + insertBill, but both do the same thing
        payment.insertCoin(amount);

        updateDisplay();

        if (payment.isPaymentComplete()) {
            confirmButton.setEnabled(true);
            confirmButton.setForeground(Color.WHITE);
        }
    }

    private void updateDisplay() {
        if (payment == null) return;

        int required = payment.getRequiredAmount();
        int paid = payment.getPaidAmount();
        int remaining = payment.getRemainingAmount();

        paidLabel.setText("Paid: " + NumberFormatter.formatCurrency(paid));
        remainingLabel.setText("Remaining: " + NumberFormatter.formatCurrency(remaining));

        int pct = required > 0 ? (int) Math.round((paid * 100.0) / required) : 0;
        pct = Math.min(100, Math.max(0, pct));

        progress.setValue(pct);
        progress.setString(pct + "%");
    }

    private void completePurchase() {
        if (payment == null) return;

        try {
            Ticket ticket = paymentService.generateTicket(startStation, endStation, payment);
            int change = paymentService.getChange(payment);

            mainFrame.getTicketScreen().displayTicket(ticket, change);

            reset();
            mainFrame.showScreen("TICKET");
        } catch (InsufficientFundsException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Payment Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelPayment() {
        if (payment != null && payment.getPaidAmount() > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Returned: " + NumberFormatter.formatCurrency(payment.getPaidAmount()),
                    "Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        reset();
        mainFrame.showScreen("STATION");
    }

    private void reset() {
        if (payment != null) paymentService.resetPayment(payment);
        payment = null;

        routeLabel.setText("Route: - → -");
        requiredLabel.setText("Required: ¥0");
        paidLabel.setText("Paid: ¥0");
        remainingLabel.setText("Remaining: ¥0");
        progress.setValue(0);
        progress.setString("0%");
        confirmButton.setEnabled(false);
    }
}
