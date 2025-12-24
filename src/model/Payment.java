public class Payment {
    private int requiredAmount;
    private int paidAmount;

    public Payment(int requiredAmount) {
        this.requiredAmount = requiredAmount;
        this.paidAmount = 0;
    }

    public void insertCoin(int amount) {
        if (amount > 0)
            paidAmount += amount;
    }

    public void insertBill(int amount) {
        if (amount > 0)
            paidAmount += amount;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public int getRemainingAmount() {
        int remaining = requiredAmount - paidAmount;
        return remaining > 0 ? remaining : 0;
    }

    public boolean isPaymentComplete() {
        return paidAmount >= requiredAmount;
    }

    public int getChangeAmount() {
        int change = paidAmount - requiredAmount;
        return change > 0 ? change : 0;
    }

    public void resetPayment() {
        paidAmount = 0;
    }
}