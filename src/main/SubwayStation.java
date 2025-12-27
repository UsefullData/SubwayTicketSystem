package main;

import ui.TicketPurchaseUI;

import javax.swing.*;

public class SubwayStation {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicketPurchaseUI ui = new TicketPurchaseUI();
            ui.setVisible(true);
        });
    }
}
