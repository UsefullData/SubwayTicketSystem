package main;

import exceptions.InsufficientFundsException;
import model.Payment;
import model.Station;
import model.Ticket;
import service.PaymentService;
import service.TicketService;
import utils.NumberFormatter;

import java.util.Scanner;

public class SubwayStation {

    public static void main(String[] args) {
        TicketService ticketService = new TicketService();
        PaymentService paymentService = new PaymentService(ticketService);

        try (Scanner scanner = new Scanner(System.in)) {

            // Show station list to help testing
            System.out.println("=== Available Stations (Line 3) ===");
            for (String s : Station.getStationNames()) {
                System.out.println("- " + s);
            }
            System.out.println("==================================");

            // Input start/end
            System.out.print("Enter start station: ");
            String startStation = scanner.nextLine().trim();

            System.out.print("Enter end station: ");
            String endStation = scanner.nextLine().trim();

            // Validate using Station registry (most reliable)
            if (!Station.isValidStationName(startStation) || !Station.isValidStationName(endStation)) {
                System.out.println("Invalid station name(s). Please choose from the list above.");
                return;
            }

            // Calculate fare
            int fare = ticketService.calculateFare(startStation, endStation);
            System.out.println("Fare: " + NumberFormatter.formatCurrency(fare));

            // Create payment session
            Payment payment = paymentService.createPayment(startStation, endStation);

            // Keep taking money until complete (test core business logic)
            while (!payment.isPaymentComplete()) {
                System.out.println("Paid: " + NumberFormatter.formatCurrency(payment.getPaidAmount())
                        + " | Remaining: " + NumberFormatter.formatCurrency(payment.getRemainingAmount()));

                System.out.print("Insert amount (e.g., 1/2/5 coins or 10/20 bills): ");

                // Safe integer input
                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid integer amount.");
                    scanner.nextLine(); // clear invalid input
                    continue;
                }

                int amount = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (amount <= 0) {
                    System.out.println("Amount must be > 0.");
                    continue;
                }

                // Simple rule: treat >=10 as bill, otherwise coin
                if (amount >= 10) {
                    payment.insertBill(amount);
                } else {
                    payment.insertCoin(amount);
                }
            }

            // Payment complete -> generate ticket
            try {
                Ticket ticket = paymentService.generateTicket(startStation, endStation, payment);
                System.out.println("\nTicket generated!");
                System.out.println(ticket);

                int change = paymentService.getChange(payment);
                System.out.println("Change to return: " + NumberFormatter.formatCurrency(change));

            } catch (InsufficientFundsException e) {
                // Should not happen due to while-loop, but kept for safety
                System.out.println("Insufficient funds! Please insert more money.");
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
