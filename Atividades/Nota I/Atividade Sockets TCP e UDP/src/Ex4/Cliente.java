package Ex4;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int port = 12345;
            try (Socket clientSocket = new Socket(serverAddress, port)) {
                System.out.println("Conexão estabelecida com o servidor");
                
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("Digite uma mensagem para enviar (ou 'exit' para sair): ");
                    String message = scanner.nextLine();
                    out.println(message);
                    
                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
