package service;
import exceptions.InsufficientFundsException;
import model.Payment;
import model.Ticket;

/**
 * PaymentService: creates Payment, checks completion, generates Ticket.
 */
public class PaymentService {

    private final TicketService ticketService;

    public PaymentService(TicketService ticketService) {
        if (ticketService == null) throw new IllegalArgumentException("TicketService cannot be null");
        this.ticketService = ticketService;
    }

    /**
     * Start a payment session for a selected route.
     * UI calls this after selecting stations.
     */
    public Payment createPayment(String startStation, String endStation) {
        int fare = ticketService.calculateFare(startStation, endStation);
        return new Payment(fare);
    }

    /**
     * Complete purchase: if enough money is paid, return Ticket.
     * Otherwise throw InsufficientFundsException.
     */
    public Ticket generateTicket(String startStation, String endStation, Payment payment)
            throws InsufficientFundsException {

        if (payment == null) throw new IllegalArgumentException("Payment cannot be null");

        // Recalculate fare (avoids mismatch if stations changed)
        int fare = ticketService.calculateFare(startStation, endStation);

        // Safety check: ensure payment was created for the same required fare
        if (payment.getRequiredAmount() != fare) {
            // This prevents logic bugs if UI uses an old Payment object
            throw new IllegalStateException(
                    "Payment required amount (" + payment.getRequiredAmount() +
                    ") does not match current fare (" + fare + "). Create a new Payment."
            );
        }

        if (!payment.isPaymentComplete()) {
            throw new InsufficientFundsException();
        }

        // Payment complete -> issue ticket (auto issue time constructor)
        return new Ticket(startStation, endStation, fare);
    }

    /**
     * Convenience for UI: show change after successful payment.
     */
    public int getChange(Payment payment) {
        if (payment == null) return 0;
        return payment.getChangeAmount();
    }

    /**
     * Reset payment (useful for cancel/finish).
     */
    public void resetPayment(Payment payment) {
        if (payment != null) payment.resetPayment();
    }
}
