public class OnlinePayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing online payment of: " + amount);
    }
}