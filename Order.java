public class Order implements Comparable<Order> {
    private final String id;
    private final String name;
    private final Double price;
    private Status status;

    public Order(String id, String name, Double price, Status status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + " " +
                " " + name +
                " " + price +
                "PLN " + status;
    }

    @Override
    public int compareTo(Order o) {
        return 0;
    }
}