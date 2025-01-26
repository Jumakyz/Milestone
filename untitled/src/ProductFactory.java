public class ProductFactory {
    public static Products createProduct(int productId,String name, String category, double price, int stock) {
        return new Products(productId,name, category, price, stock);
    }
}
