import java.util.*;

public class Shop {
    private static final String fileName = "orders.csv";
    private static Map<String, Order> orders = OrdersFileUtils.readOrdersFromFile(fileName);

    private static final int STOP = 0;
    private static final int SORT_ORDERS = 1;
    private static final int ADD_ORDER = 2;
    private static final int CHANGE_ORDER_STATUS = 3;
    private static final int PRINT_ORDERS = 4;

    private static final int SORT_BY_NAME = 0;
    private static final int SORT_BY_PRICE = 1;
    private static final int SORT_BY_STATUS = 2;
    private static final int SORT_BY_ID = 3;

    public void app(Scanner scanner) {
        int choice;
        System.out.println("Welcome in your shop!");
        printOrders(scanner);
        do {
            System.out.println("What would you want to do?");
            printOptions();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case SORT_ORDERS -> sortOrders(scanner);
                case ADD_ORDER -> addOrder(scanner);
                case CHANGE_ORDER_STATUS -> changeOrderStatus(scanner);
                case PRINT_ORDERS -> printOrders(scanner);
                case STOP -> {
                    break;
                }
                default -> System.out.println("Invalid choice");
            }
        } while (choice != STOP);
        OrdersFileUtils.writeOrdersToFile(orders, fileName);
        scanner.close();
    }

    private void changeOrderStatus(Scanner scanner) {
        System.out.println("Which order status do you want to change?");
        String id = scanner.nextLine();
        if (orders.get(id).getStatus().name().equals(Status.IN_TRANSPORT.name())
                || orders.get(id).getStatus().name().equals(Status.COMPLETED.name())) {
            System.out.println("You can't change this order status");
        } else {
            System.out.println("Input status");
            orders.get(id).setStatus(Status.valueOf(scanner.nextLine().toUpperCase()));
        }
    }

    private void addOrder(Scanner scanner) {
        System.out.print("Input ID: ");
        String id = scanner.nextLine();
        System.out.print("Input name: ");
        String name = scanner.nextLine();
        System.out.print("Input price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        orders.put(id, new Order(id, name, price, Status.ORDERED));
    }

    private void sortOrders(Scanner scanner) {
        System.out.println("How would you like to sort?");
        System.out.println("By name - " + SORT_BY_NAME);
        System.out.println("By price - " + SORT_BY_PRICE);
        System.out.println("By status - " + SORT_BY_STATUS);
        System.out.println("By ID - " + SORT_BY_ID);
        int choice = scanner.nextInt();
        if (choice == SORT_BY_NAME) {
            List<Map.Entry<String, Order>> list = new LinkedList<Map.Entry<String, Order>>(orders.entrySet());
            list.sort((o1, o2) -> (o1.getValue()).getName().compareTo(o2.getValue().getName()));
            orders = new LinkedHashMap<String, Order>();
            for (Map.Entry<String, Order> entry : list) {
                orders.put(entry.getKey(), entry.getValue());
            }
        } else if (choice == SORT_BY_PRICE) {
            List<Map.Entry<String, Order>> list = new LinkedList<Map.Entry<String, Order>>(orders.entrySet());
            list.sort((o1, o2) -> (o1.getValue()).getPrice().compareTo(o2.getValue().getPrice()));
            orders = new LinkedHashMap<String, Order>();
            for (Map.Entry<String, Order> entry : list) {
                orders.put(entry.getKey(), entry.getValue());
            }
        } else if (choice == SORT_BY_STATUS) {
            List<Map.Entry<String, Order>> list = new LinkedList<Map.Entry<String, Order>>(orders.entrySet());
            list.sort((o1, o2) -> (o1.getValue()).getStatus().name().compareTo(o2.getValue().getStatus().name()));
            orders = new LinkedHashMap<String, Order>();
            for (Map.Entry<String, Order> entry : list) {
                orders.put(entry.getKey(), entry.getValue());
            }
        } else if (choice == SORT_BY_ID) {
            List<Map.Entry<String, Order>> list = new LinkedList<Map.Entry<String, Order>>(orders.entrySet());
            list.sort((o1, o2) -> (o1.getKey()).compareTo(o2.getKey()));
            orders = new LinkedHashMap<String, Order>();
            for (Map.Entry<String, Order> entry : list) {
                orders.put(entry.getKey(), entry.getValue());
            }
        } else {
            System.out.println("Invalid choice");
        }
    }

    private static void printOptions() {
        System.out.println(STOP + " stops the program");
        System.out.println(SORT_ORDERS + " sort orders");
        System.out.println(ADD_ORDER + " add order");
        System.out.println(CHANGE_ORDER_STATUS + " change order status");
        System.out.println(PRINT_ORDERS + " print orders");
    }

    private static void printOrders(Scanner scanner) {
        System.out.println("Would you like to print orders? (Yes or no)");
        String choice = scanner.nextLine().toUpperCase();
        if (choice.equals("YES")) {
            Collection<Order> o = orders.values();
            for (Order order : o) {
                System.out.printf("%-10s %-30s %-10s %-10s", order.getId(), order.getName(), order.getPrice() + "PLN", order.getStatus());
                System.out.println();
            }
        } else if (choice.equals("NO")) {
            return;
        } else {
            System.out.println("Invalid choice");
        }
    }

    static class CompareByName implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    static class CompareByPrice implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    }

    static class CompareByStatus implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o1.getStatus().name().compareTo(o2.getStatus().name());
        }
    }
}