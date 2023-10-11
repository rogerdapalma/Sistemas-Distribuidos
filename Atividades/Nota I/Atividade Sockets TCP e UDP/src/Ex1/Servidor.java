package Ex1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Servidor aguardando conexões na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();

                if (request != null) {
                    String[] tokens = request.split(",");
                    if (tokens.length == 3) {
                        int operation = Integer.parseInt(tokens[0]);
                        double num1 = Double.parseDouble(tokens[1]);
                        double num2 = Double.parseDouble(tokens[2]);

                        double result = 0.0;
                        if (operation == 1) {
                            result = num1 + num2;
                        } else if (operation == 2) {
                            result = num1 - num2;
                        } else if (operation == 3) {
                            result = num1 * num2;
                        } else if (operation == 4) {
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                out.println("Erro: divisão por zero");
                                clientSocket.close();
                                continue;
                            }
                        }

                        out.println("Resultado: " + result);
                    } else {
                        out.println("Requisição inválida.");
                    }
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
