package Ex4;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int port = 12345;
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Servidor aguardando conexões na porta " + port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                System.out.println("Recebido: " + message);
            }
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
