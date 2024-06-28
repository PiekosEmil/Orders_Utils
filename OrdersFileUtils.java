import java.io.*;
import java.util.*;

public class OrdersFileUtils {

    public static Map<String, Order> readOrdersFromFile(String fileName) {
        Map<String, Order> orders = new TreeMap<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(fileName))
        ) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String order = scanner.nextLine();
                String[] split = order.split(";");
                orders.put(split[0], new Order(split[0], split[1], Double.parseDouble(split[2]), Status.valueOf(split[3])));
            }
        } catch (IOException e) {
            System.err.println("File not found");
        }
        return orders;
    }

    public static void writeOrdersToFile(Map<String, Order> orders, String fileName) {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))
        ) {
            Collection<Order> o = orders.values();
            for (Order order : o) {
                writer.write(order.getId());
                writer.write(";");
                writer.write(order.getName());
                writer.write(";");
                writer.write(String.valueOf(order.getPrice()));
                writer.write(";");
                writer.write(order.getStatus().name());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("File not found");
        }
    }
}
