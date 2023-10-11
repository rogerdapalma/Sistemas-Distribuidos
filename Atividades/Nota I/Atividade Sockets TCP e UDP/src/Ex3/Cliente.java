package Ex3;

import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Configura o socket UDP
            socket = new DatagramSocket();

            // Endereço e porta do servidor
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 12345;

            // Mensagem a ser enviada
            String message = "Olá, servidor UDP!";

            // Converte a mensagem em bytes
            byte[] sendData = message.getBytes();

            // Cria o pacote com os dados e as informações do servidor
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            // Envia a mensagem para o servidor
            socket.send(sendPacket);

            System.out.println("Enviou: " + message);

            // Prepara o pacote para receber a resposta do servidor
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // Aguarda a resposta do servidor
            socket.receive(receivePacket);

            // Extrai a resposta e imprime
            String reversedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Recebeu: " + reversedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
