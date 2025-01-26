public class Products {
    private int productId;
    private String name;
    private String category;
    private double price;
    private int stock;

    public Products(int productId, String name, String category, double price, int stock) {
        this.productId=productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
