public class OrderObserver implements Observer {
    private String name;

    public OrderObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String status) {
        System.out.println("OrderObserver " + name + ": Order status changed to " + status);
    }
}
