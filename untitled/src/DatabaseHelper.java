
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper implements AutoCloseable {
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";



    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DatabaseHelper() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
        }
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }



    public Order createOrder(int productId, int quantity, double total_price, String status) {
        return null;
    }

    public Products createProduct(String name, String category, double price, int stock) {
        return null;
    }
}
