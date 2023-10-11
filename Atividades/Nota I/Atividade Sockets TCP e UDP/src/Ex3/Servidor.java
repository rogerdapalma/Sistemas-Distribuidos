package Ex3;

import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Configura o socket UDP na porta 12345
            socket = new DatagramSocket(12345);

            System.out.println("Servidor UDP esperando por mensagens...");

            while (true) {
                // Prepara o pacote para receber dados
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Recebe os dados do cliente
                socket.receive(receivePacket);

                // Extrai os dados e informações do cliente
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Converte os dados recebidos em uma String
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Inverte a mensagem
                String reversedMessage = new StringBuilder(message).reverse().toString();

                // Prepara os dados para enviar de volta ao cliente
                byte[] sendData = reversedMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);

                // Envia a mensagem invertida de volta para o cliente
                socket.send(sendPacket);

                System.out.println("Enviou: " + reversedMessage + " para " + clientAddress + ":" + clientPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
