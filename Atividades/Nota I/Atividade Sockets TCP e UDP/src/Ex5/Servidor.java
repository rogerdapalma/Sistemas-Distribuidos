import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        Map<String, Map<String, Integer>> salesData = new HashMap<>();

        try {
            socket = new DatagramSocket(12345);

            System.out.println("Servidor UDP está rodando...");

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (message.contains("LISTAGEM FINALIZADA")) {
                    String[] parts = message.split(":");
                    String clientId = parts[0];
                    Map<String, Integer> clientSales = salesData.get(clientId);

                    int totalSales = clientSales.values().stream().mapToInt(Integer::intValue).sum();
                    String response = "Total de vendas para cliente " + clientId + ": R$" + totalSales;
                    DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.length(), receivePacket.getAddress(), receivePacket.getPort());
                    socket.send(sendPacket);
                    clientSales.clear();
                } else {
                    String[] parts = message.split(":");
                    if (parts.length == 3) {
                        String clientId = parts[0];
                        String product = parts[1];
                        int quantity = Integer.parseInt(parts[2]);


                        Map<String, Integer> clientSales = salesData.computeIfAbsent(clientId, k -> new HashMap<>());
                        int productPrice = getProductPrice(product);
                        clientSales.put(product, clientSales.getOrDefault(product, 0) + (productPrice * quantity));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private static int getProductPrice(String product) {
        Map<String, Integer> priceList = new HashMap<>();
        priceList.put("Farinha de Trigo", 20);
        priceList.put("Leite", 30);
        priceList.put("Batata", 15);
        priceList.put("Azeite", 20);
        return priceList.getOrDefault(product, 0);
    }
}
