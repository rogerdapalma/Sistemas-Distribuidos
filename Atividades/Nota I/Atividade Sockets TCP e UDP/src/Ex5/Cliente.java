package Ex5;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 12345;

            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o seu identificador de cliente: ");
            String clientId = scanner.nextLine();

            while (true) {
                System.out.print("Digite o nome do produto (ou 'EXIT' para encerrar): ");
                String product = scanner.nextLine();

                if (product.equals("EXIT")) {
                    break;
                }

                System.out.print("Digite a quantidade vendida: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha pendente

                String sale = clientId + ":" + product + ":" + quantity;
                byte[] sendData = sale.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);
            }

            String endMessage = clientId + ":LISTAGEM FINALIZADA";
            byte[] sendData = endMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
