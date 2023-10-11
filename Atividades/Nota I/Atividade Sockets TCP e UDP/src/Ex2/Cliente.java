package Ex2;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String serverHost = "127.0.0.1";
        int serverPort = 12345;

        try {
            Socket clientSocket = new Socket(serverHost, serverPort);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {
                System.out.println("Escolha uma operacoo:");
                System.out.println("1. Deposito");
                System.out.println("2. Saque");
                System.out.println("3. Consultar Saldo");
                System.out.println("4. Sair");
                System.out.print("Opcoo (1/2/3/4): ");

                String choice = userInput.readLine();

                if (choice.equals("1") || choice.equals("2")) {
                    System.out.print("Digite o valor: ");
                    double amount = Double.parseDouble(userInput.readLine());
                    String operation = choice.equals("1") ? "deposito" : "saque";
                    out.println(operation + "," + amount);
                } else if (choice.equals("3")) {
                    out.println("saldo");
                } else if (choice.equals("4")) {
                    break;
                } else {
                    System.out.println("Opcao invalida.");
                }

                String response = in.readLine();
                System.out.println("Resposta do servidor: " + response);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
