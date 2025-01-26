import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private Connection connection;

    public PaymentService(Connection connection) {
        this.connection = connection;
    }

    private static final String INSERT_PAYMENT = "INSERT INTO payments (order_id, amount, payment_method, status) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_PAYMENT = "UPDATE payments SET status = ? WHERE id = ?";
    private static final String DELETE_PAYMENT = "DELETE FROM payments WHERE id = ?";
    private static final String SELECT_PAYMENT = "SELECT * FROM payments WHERE id = ?";
    private static final String SELECT_PAYMENTS_BY_ORDER = "SELECT * FROM payments WHERE order_id = ?";

    public Payment createPayment(int orderId, double amount, String paymentMethod, String status) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_PAYMENT)) {
            stmt.setInt(1, orderId);
            stmt.setDouble(2, amount);
            stmt.setString(3, paymentMethod);
            stmt.setString(4, status);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Payment(rs.getInt("id"), orderId, amount, paymentMethod, status);
                }
                throw new SQLException("Failed to create payment");
            }
        }
    }

    public Payment getPayment(int paymentId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_PAYMENT)) {
            stmt.setInt(1, paymentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Payment(
                            rs.getInt("id"),
                            rs.getInt("order_id"),
                            rs.getDouble("amount"),
                            rs.getString("payment_method"),
                            rs.getString("status")
                    );
                }
                return null;
            }
        }
    }

    public boolean updatePaymentStatus(int paymentId, String status) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_PAYMENT)) {
            stmt.setString(1, status);
            stmt.setInt(2, paymentId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deletePayment(int paymentId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_PAYMENT)) {
            stmt.setInt(1, paymentId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Payment> getPaymentsByOrder(int orderId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_PAYMENTS_BY_ORDER)) {
            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(new Payment(
                            rs.getInt("id"),
                            rs.getInt("order_id"),
                            rs.getDouble("amount"),
                            rs.getString("payment_method"),
                            rs.getString("status")
                    ));
                }
            }
        }
        return payments;
    }


}
