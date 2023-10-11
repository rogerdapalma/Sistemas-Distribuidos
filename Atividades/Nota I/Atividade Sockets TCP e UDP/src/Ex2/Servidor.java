package Ex2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static double saldo = 1000.0;  // Saldo inicial

    public static void main(String[] args) {
        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Servidor aguardando conexoes na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexoo estabelecida com " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();

                if (request != null) {
                    String[] tokens = request.split(",");
                    if (tokens.length == 2) {
                        String operation = tokens[0];
                        double amount = Double.parseDouble(tokens[1]);

                        if (operation.equals("deposito")) {
                            saldo += amount;
                            out.println("Deposito realizado com sucesso. Saldo atual: " + saldo);
                        } else if (operation.equals("saque")) {
                            if (saldo >= amount) {
                                saldo -= amount;
                                out.println("Saque realizado com sucesso. Saldo atual: " + saldo);
                            } else {
                                out.println("Saldo insuficiente.");
                            }
                        } else if (operation.equals("saldo")) {
                            out.println("Saldo atual: " + saldo);
                        } else {
                            out.println("Operacao invalida.");
                        }
                    } else {
                        out.println("Requisicao invalida.");
                    }
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
