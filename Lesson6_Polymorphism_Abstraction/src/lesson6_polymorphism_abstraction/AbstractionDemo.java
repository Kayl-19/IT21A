package lesson6_polymorphism_abstraction;

abstract class OnlinePayment {

    protected String customerName;

    public OnlinePayment(String customerName) {
        this.customerName = customerName;
    }

    public abstract boolean processPayment(double amount);

    public void printReceipt(double amount, boolean success) {
        System.out.println("---------- RECEIPT ----------");
        System.out.println("Customer : " + customerName);
        System.out.println("Amount   : PHP " + amount);
        System.out.println("Status   : " + (success ? "PAID" : "FAILED"));
        System.out.println("-----------------------------\n");
    }

    protected boolean isValidAmount(double amount) {
        return amount > 0;
    }
}

class GCashPayment extends OnlinePayment {

    private double walletBalance;

    public GCashPayment(String customerName, double walletBalance) {
        super(customerName);
        this.walletBalance = walletBalance;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("[GCash] Checking wallet balance of " + customerName + "...");
        if (!isValidAmount(amount) || amount > walletBalance) {
            System.out.println("[GCash] Not enough balance.");
            return false;
        }
        walletBalance -= amount;
        System.out.println("[GCash] Payment sent. Remaining balance: PHP " + walletBalance);
        return true;
    }
}

class CreditCardPayment extends OnlinePayment {

    private String cardNumber;
    private double creditLimit;

    public CreditCardPayment(String customerName, String cardNumber, double creditLimit) {
        super(customerName);
        this.cardNumber = cardNumber;
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean processPayment(double amount) {
        String masked = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        System.out.println("[Card] Contacting bank for card " + masked + "...");
        if (!isValidAmount(amount) || amount > creditLimit) {
            System.out.println("[Card] Transaction declined: credit limit exceeded.");
            return false;
        }
        creditLimit -= amount;
        System.out.println("[Card] Transaction approved. Remaining limit: PHP " + creditLimit);
        return true;
    }
}

class CashOnDeliveryPayment extends OnlinePayment {

    public CashOnDeliveryPayment(String customerName) {
        super(customerName);
    }

    @Override
    public boolean processPayment(double amount) {
        if (!isValidAmount(amount)) {
            return false;
        }
        System.out.println("[COD] Rider will collect PHP " + amount + " from " + customerName + " at the door.");
        return true;
    }
}

public class AbstractionDemo {

    public static void main(String[] args) {
        System.out.println("===== Online Store: Abstraction Demo =====\n");

        GCashPayment gcash = new GCashPayment("Maria Santos", 1500.0);
        CreditCardPayment card = new CreditCardPayment("Juan Dela Cruz", "4111222233334444", 5000.0);
        CashOnDeliveryPayment cod = new CashOnDeliveryPayment("Ana Reyes");

        double amount1 = 1200.0;
        boolean ok1 = gcash.processPayment(amount1);
        gcash.printReceipt(amount1, ok1);

        double amount2 = 8000.0;
        boolean ok2 = card.processPayment(amount2);
        card.printReceipt(amount2, ok2);

        double amount3 = 750.0;
        boolean ok3 = cod.processPayment(amount3);
        cod.printReceipt(amount3, ok3);
    }
}
