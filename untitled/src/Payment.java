public class Payment {
        private int paymentId;
        private int orderId;
        private double amount;
        private String paymentMethod;
        private String status;

        public Payment(int paymentId, int orderId, double amount, String paymentMethod, String status) {
            this.paymentId = paymentId;
            this.orderId = orderId;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
            this.status = status;
        }

        public int getPaymentId() {
            return paymentId;
        }

        public int getOrderId() {
            return orderId;
        }

        public double getAmount() {
            return amount;
        }

    public String getStatus() {
        return status;
    }

    public String getPaymentMethod() {
            return paymentMethod;
        }
    public void setStatus(String status) {
        this.status = status;
    }
}
