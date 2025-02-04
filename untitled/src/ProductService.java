import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class ProductService {
    private Connection connection;

    public ProductService(Connection connection){
        this.connection=connection;
    }

    private static final String INSERT_PRODUCT = "INSERT INTO products (name, category, price, stock) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_PRODUCT = "UPDATE products SET name = ?, category = ?, price = ?, stock = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    private static final String SELECT_PRODUCT = "SELECT * FROM products WHERE id = ?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";



    public Products createProduct(String name, String category, double price, int stock) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_PRODUCT)) {
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setDouble(3, price);
            stmt.setInt(4, stock);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Products(rs.getInt("id"), name, category, price, stock);
                }
                throw new SQLException("Failed to create product");
            }
        }
    }

    public Products getProduct(Integer id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_PRODUCT)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Products(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    );
                }
                return null;
            }
        }
    }

    public List<Products> getAllProducts() throws SQLException {
        List<Products> products = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_PRODUCTS)) {

            while (rs.next()) {
                products.add(new Products(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
        }
        return products;
    }

    public boolean updateProduct(Products product) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_PRODUCT)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setInt(5, product.getProductId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteProduct(Integer id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_PRODUCT)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

}
