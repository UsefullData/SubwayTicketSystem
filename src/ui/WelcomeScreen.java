package ui;


import javax.swing.*;
import java.awt.*;

import model.Station;
import java.util.List;


public class WelcomeScreen extends JPanel {
    private final TicketPurchaseUI mainFrame;

    public WelcomeScreen(TicketPurchaseUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertical layout to stack components
        setAlignmentX(Component.CENTER_ALIGNMENT); // Ensure the whole panel is centered
        initComponents();
    }

    private void initComponents() {
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(242, 242, 242)); // Light gray background for header
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the header

        // Add space before the title
        headerPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Adjust space above the title

        JLabel titleLabel = new JLabel("Hangzhou Metro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 0, 0)); // Red text for the title
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Line 3 Ticket System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        subtitleLabel.setForeground(new Color(100, 100, 100)); // Slightly lighter gray for subtitle
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between title and subtitle
        headerPanel.add(subtitleLabel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(242, 242, 242)); // Light gray background for button panel
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button panel

        JButton purchaseButton = createButton("Purchase Ticket", new Color(100, 100, 100)); // Dark gray button
        JButton viewStationsButton = createButton("View All Stations", new Color(100, 100, 100)); // Dark gray button
        JButton exitButton = createButton("Exit", new Color(192, 57, 43)); // Red accent button

        purchaseButton.addActionListener(e -> mainFrame.showScreen("STATION"));
        viewStationsButton.addActionListener(e -> showStationsDialog());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Space before buttons
        buttonPanel.add(purchaseButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Space between buttons
        buttonPanel.add(viewStationsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Space between buttons
        buttonPanel.add(exitButton);

        // Create a parent panel and add header and buttons, and center the content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(242, 242, 242));
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Space between header and buttons
        mainPanel.add(buttonPanel);

        // Add the entire main panel to the center of the screen
        add(mainPanel);
    }

    private JButton createButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE); // White text for all buttons
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void showStationsDialog() {
        // Show stations dialog implementation
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Line 3 Stations", true);
        dialog.setSize(480, 600);
        dialog.setLocationRelativeTo(this);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Example station names, replace with actual data from your model
        List<String> stations = Station.getStationNames();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stations.size(); i++) {
            sb.append(String.format("%2d. %s%n", i + 1, stations.get(i)));
        }
        area.setText(sb.toString());


        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        root.add(new JScrollPane(area), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        root.add(closeButton, BorderLayout.SOUTH);

        dialog.setContentPane(root);
        dialog.setVisible(true);
    }
}
