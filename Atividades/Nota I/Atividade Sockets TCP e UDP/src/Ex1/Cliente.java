package Ex1;

import java.io.*;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        String serverHost = "127.0.0.1";
        int serverPort = 12345;

        try {
            try ( Socket clientSocket = new Socket(serverHost, serverPort)) {
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                System.out.println("Escolha uma operação:");
                System.out.println("1. Soma");
                System.out.println("2. Subtração");
                System.out.println("3. Multiplicação");
                System.out.println("4. Divisão");

                int operation = Integer.parseInt(userInput.readLine());
                System.out.print("Digite o primeiro número: ");
                double num1 = Double.parseDouble(userInput.readLine());
                System.out.print("Digite o segundo número: ");
                double num2 = Double.parseDouble(userInput.readLine());

                out.println(operation + "," + num1 + "," + num2);

                String response = in.readLine();
                System.out.println("Resposta do servidor: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
