import java.sql.*;
import java.util.ArrayList;
import java.util.List;

             public class OrderService {
                    private Connection connection;

                    public OrderService(Connection connection){
                        this.connection=connection;
                    }
                    private static final String INSERT_ORDER = "INSERT INTO orders (product_id, quantity, total_price, status) VALUES (?, ?, ?, ?) RETURNING id";
                    private static final String UPDATE_ORDER = "UPDATE orders SET product_id = ?, quantity = ?, total_price = ?, status = ? WHERE id = ?";
                    private static final String DELETE_ORDER = "DELETE FROM orders WHERE id = ?";
                    private static final String SELECT_ORDER = "SELECT * FROM orders WHERE id = ?";
                    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders";

                    public Order createOrder(int productId, int quantity, double totalPrice, String status) throws SQLException {
                        try (PreparedStatement stmt = connection.prepareStatement(INSERT_ORDER)) {
                            stmt.setInt(1, productId);
                            stmt.setInt(2, quantity);
                            stmt.setDouble(3, totalPrice);
                            stmt.setString(4, status);

                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                    return new Order(rs.getInt("id"), productId, quantity, totalPrice, status);
                                }
                                throw new SQLException("Failed to create order");
                            }
                        }
                    }

                    public Order getOrder(Integer id) throws SQLException {
                        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ORDER)) {
                            stmt.setInt(1, id);

                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                    return new Order(
                                            rs.getInt("id"),
                                            rs.getInt("product_id"),
                                            rs.getInt("quantity"),
                                            rs.getDouble("total_price"),
                                            rs.getString("status")
                                    );
                                }
                                return null;
                            }
                        }
                    }

                    public List<Order> getAllOrders() throws SQLException {
                        List<Order> orders = new ArrayList<>();
                        try (Statement stmt = connection.createStatement();
                             ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS)) {

                            while (rs.next()) {
                                orders.add(new Order(
                                        rs.getInt("id"),
                                        rs.getInt("product_id"),
                                        rs.getInt("quantity"),
                                        rs.getDouble("total_price"),
                                        rs.getString("status")

                ));
            }
        }
        return orders;
    }

    public boolean updateOrder(Order order) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_ORDER)) {
            stmt.setInt(1, order.getProductId());
            stmt.setInt(2, order.getQuantity());
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setString(4, order.getStatus());
            stmt.setInt(5, order.getOrderId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteOrder(Integer id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_ORDER)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
