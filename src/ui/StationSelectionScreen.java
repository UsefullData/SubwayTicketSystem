package ui;

import model.Station;
import service.TicketService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StationSelectionScreen extends JPanel {
    private final TicketPurchaseUI mainFrame;
    private final TicketService ticketService = new TicketService();

    private JComboBox<String> startCombo;
    private JComboBox<String> endCombo;
    private JLabel stopsLabel;
    private JLabel fareLabel;

    private JButton proceedButton;

    private int calculatedFare = 0;

    public StationSelectionScreen(TicketPurchaseUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        JPanel header = new JPanel();
        header.setBackground(new Color(242, 242, 242));
        header.setPreferredSize(new Dimension(800, 80));

        JLabel title = new JLabel("Select Your Journey");
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setForeground(new Color(50, 50, 50));
        header.add(title);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(40, 90, 40, 90));

        content.add(createStationRow("From Station:", true));
        content.add(Box.createRigidArea(new Dimension(0, 18)));
        content.add(createStationRow("To Station:", false));
        content.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel farePanel = new JPanel(new GridLayout(2, 1, 4, 4));
        farePanel.setBackground(new Color(236, 240, 241));
        farePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        farePanel.setMaximumSize(new Dimension(620, 110));

        stopsLabel = new JLabel("Stops: -", SwingConstants.CENTER);
        stopsLabel.setFont(new Font("Arial", Font.PLAIN, 22));

        fareLabel = new JLabel("Fare: ¥ -", SwingConstants.CENTER);
        fareLabel.setFont(new Font("Arial", Font.BOLD, 30));
        fareLabel.setForeground(new Color(192, 57, 43));

        farePanel.add(stopsLabel);
        farePanel.add(fareLabel);

        content.add(farePanel);
        content.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        buttons.setBackground(Color.WHITE);
        buttons.setMaximumSize(new Dimension(700, 60));

        JButton calc = createButton("Calculate Fare", new Color(192, 57, 43));
        proceedButton = createButton("Proceed to Payment", new Color(46, 204, 113));
        JButton back = createButton("Back", new Color(149, 165, 166));
        
        
        proceedButton.setForeground(Color.WHITE);

        calc.addActionListener(e -> calculateFare());
        proceedButton.addActionListener(e -> goPayment());
        back.addActionListener(e -> {
            reset();
            mainFrame.showScreen("WELCOME");
        });

        buttons.add(calc);
        buttons.add(proceedButton);
        buttons.add(back);

        content.add(buttons);

        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    private JPanel createStationRow(String label, boolean start) {
    JPanel row = new JPanel(new BorderLayout(12, 0));
    row.setBackground(Color.WHITE); // Keep the background of the row white

    JLabel l = new JLabel(label);
    l.setFont(new Font("Arial", Font.BOLD, 25));
    l.setPreferredSize(new Dimension(160, 40));
    l.setForeground(new Color(50, 50, 50)); // Dark gray color for the label text

    List<String> stations = Station.getStationNames();
    JComboBox<String> combo = new JComboBox<>(stations.toArray(new String[0]));
    combo.setFont(new Font("Arial", Font.PLAIN, 24)); // Set default font size for the combo box

    // Set the desired width and height for the combo box
    combo.setPreferredSize(new Dimension(440, 50)); // 440px width and 50px height

    // Set the background and text color for the combo box (stations)
    combo.setBackground(new Color(100, 100, 100)); // Dark gray background for the combo box
    combo.setForeground(Color.WHITE); // White text for the combo box

    // Set the font size for the station items in the combo box
    combo.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
            label.setFont(new Font("Arial", Font.PLAIN, 18)); // Adjust the font size for station names
            return label;
        }
    });

    if (start) {
        startCombo = combo;
    } else {
        endCombo = combo;
        endCombo.setSelectedIndex(Math.min(5, stations.size() - 1));
    }

    row.add(l, BorderLayout.WEST);
    row.add(combo, BorderLayout.CENTER);
    return row;
}





    private JButton createButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(210, 45));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void calculateFare() {
        String start = (String) startCombo.getSelectedItem();
        String end = (String) endCombo.getSelectedItem();

        try {
            calculatedFare = ticketService.calculateFare(start, end);
            int stops = Station.stopsBetween(start, end);
            stopsLabel.setText("Stops: " + stops);
            fareLabel.setText("Fare: ¥" + calculatedFare);
            proceedButton.setEnabled(true);
        } catch (Exception ex) {
            calculatedFare = 0;
            proceedButton.setEnabled(false);
            stopsLabel.setText("Stops: -");
            fareLabel.setText("Fare: ¥ -");
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Selection", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goPayment() {
        if (calculatedFare <= 0) return;

        String start = (String) startCombo.getSelectedItem();
        String end = (String) endCombo.getSelectedItem();

        mainFrame.getPaymentScreen().initializePayment(start, end);
        mainFrame.showScreen("PAYMENT");
    }

    public void reset() {
        startCombo.setSelectedIndex(0);
        endCombo.setSelectedIndex(Math.min(5, Station.getStationNames().size() - 1));
        stopsLabel.setText("Stops: -");
        fareLabel.setText("Fare: ¥ -");
        calculatedFare = 0;
        proceedButton.setEnabled(false);
    }
}
