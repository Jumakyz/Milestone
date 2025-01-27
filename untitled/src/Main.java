//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (DatabaseHelper dbHelper = new DatabaseHelper()) {
            dbHelper.connect();
            PaymentService paymentService = new PaymentService(dbHelper.getConnection());
            OrderServer orderService=new OrderServer(dbHelper.getConnection());
            ProductServer productServer=new ProductServer(dbHelper.getConnection());
            System.out.println("\n=== Creating Records ===");

            Products product1 = productServer.createProduct("MacBook", "Laptop", 1200000, 12);
            Products product2 = productServer.createProduct("IPhone 13 Pro", "Smartphone", 300000, 23);
            Products product3 = productServer.createProduct("Oppo A9 2020", "Smartphone", 120000, 50);
            Products product4=productServer.createProduct("Asus","laptop",600000,13);
            Products product5=productServer.createProduct("Samsung Watch 6","Smartwatches",180000,11);
            Products product6=productServer.createProduct("Airpods","earphones",130000,9);
            Products product7=productServer.createProduct("Hwawei Matebook 3","laptop",350000,21);
            System.out.println("Created products: " + product1.getName() + ", " + product2.getName()+" "+product3.getName()+product4.getName()+product5.getName()+product6.getName()+product7.getName());


            Order order1 = orderService.createOrder(product1.getProductId(), 2, product1.getPrice() * 2, "Waiting");
            Order order2 =orderService .createOrder(product2.getProductId(), 3, product2.getPrice() * 3, "Shipped");
            Order order3=orderService.createOrder(product3.getProductId(), 4, product3.getPrice()*4,"Shipped" );
            Order order4=orderService.createOrder(product4.getProductId(), 5, product4.getPrice()*5,"Shipped" );
            Order order5=orderService.createOrder(product5.getProductId(),1, product5.getPrice()*1,"Shippped" );
            Order order6=orderService.createOrder(product6.getProductId(), 3, product6.getPrice()*3,"Waiting" );
            Order order7=orderService.createOrder(product7.getProductId(), 2, product7.getPrice()*2,"Waiting" );
            System.out.println("Created orders: " + order1.getOrderId() + ", " + order2.getOrderId()+", "+order3.getOrderId()+", "+order4.getOrderId()+", "+order5.getOrderId()+", "+order6.getOrderId()+", "+order7.getOrderId());


            Payment payment1 = paymentService.createPayment(order1.getOrderId(), order1.getTotalPrice(), "Master Card", "Waitiing");
            Payment payment2 = paymentService.createPayment(order2.getOrderId(), order2.getTotalPrice(), "Kaspi", "Completed");
            Payment payment3=paymentService.createPayment(order3.getOrderId(),order3.getTotalPrice(),"Forte Bank","Completed" );
            Payment payment4 = paymentService.createPayment(order4.getOrderId(), order4.getTotalPrice(), "Halyk Bank", "Completed");
            Payment payment5 = paymentService.createPayment(order5.getOrderId(), order5.getTotalPrice(), "Alpha Bank", "Completed");
            Payment payment6 = paymentService.createPayment(order6.getOrderId(), order2.getTotalPrice(), "Kaspi", "Completed");
            Payment payment7 = paymentService.createPayment(order7.getOrderId(), order7.getTotalPrice(), "Halyk Bank", "Waiting");
            System.out.println("Created payments: " + payment1.getPaymentId() + ", " + payment2.getPaymentId()+","+payment3.getPaymentId()+","+payment4.getPaymentId()+","+payment5.getPaymentId()+","+payment6.getPaymentId()+","+payment7.getPaymentId());


            System.out.println("\n=== Reading Records ===");
            List<Payment> paymentsByOrder1 = paymentService.getPaymentsByOrder(order1.getOrderId());
            System.out.println("Payments for Order " + order1.getOrderId() + ": " + paymentsByOrder1.size());
            List<Payment> paymentsByOrder2 = paymentService.getPaymentsByOrder(order2.getOrderId());
            System.out.println("Payments for Order " + order2.getOrderId() + ": " + paymentsByOrder2.size());
            List<Payment> paymentsByOrder3 = paymentService.getPaymentsByOrder(order3.getOrderId());
            System.out.println("Payments for Order " + order3.getOrderId() + ": " + paymentsByOrder3.size());
            List<Payment> paymentsByOrder4 = paymentService.getPaymentsByOrder(order4.getOrderId());
            System.out.println("Payments for Order " + order4.getOrderId() + ": " + paymentsByOrder4.size());
            List<Payment> paymentsByOrder5 = paymentService.getPaymentsByOrder(order5.getOrderId());
            System.out.println("Payments for Order " + order5.getOrderId() + ": " + paymentsByOrder5.size());
            List<Payment> paymentsByOrder6 = paymentService.getPaymentsByOrder(order6.getOrderId());
            System.out.println("Payments for Order " + order6.getOrderId() + ": " + paymentsByOrder6.size());
            List<Payment> paymentsByOrder7= paymentService.getPaymentsByOrder(order7.getOrderId());
            System.out.println("Payments for Order " + order7.getOrderId() + ": " + paymentsByOrder7.size());

            System.out.println("\n=== Updating Records ===");

            boolean paymentUpdated = paymentService.updatePaymentStatus(payment1.getPaymentId(), "Completed");
            System.out.println("Payment status updated: " + paymentUpdated);

            System.out.println("\n=== Deleting Records ===");

            boolean paymentDeleted = paymentService.deletePayment(payment2.getPaymentId());
            System.out.println("Deleted payment " + payment2.getPaymentId() + ": " + paymentDeleted);


            List<Payment> remainingPayments = paymentService.getPaymentsByOrder(order1.getOrderId());
            System.out.println("Remaining payments for Order " + order1.getOrderId() + ": " + remainingPayments.size());

        } catch (SQLException e) {
            System.err.println("Database error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }
