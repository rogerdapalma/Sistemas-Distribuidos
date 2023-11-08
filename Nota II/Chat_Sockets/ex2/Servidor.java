package ex2;

import ex1.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        try {
            //cria um servidor e aloca a porta 1234 para ele
            ServerSocket servidor = new ServerSocket(1234);

            //aguarda uma conexão
            Socket cliente = servidor.accept(); //bloqueante
            System.out.println("Recebi uma conexão");

            //cria uma thread para receber mensagens do cliente
            Thread t = new Thread() {
                public void run() {
                    //aguarda uma mensagem do cliente
                    while (true) {
                        try {
                            DataInputStream in = new DataInputStream(cliente.getInputStream());
                            String msgRecebida = in.readUTF();//bloqueante
                            System.out.println("Recebi " + msgRecebida);
                        } catch (IOException e) {
                            System.out.println("Erro na thread de recebimento de mensagens do servidor");
                        }
                    }
                }
            };
            t.start();

            //envia uma mensagem ao cliente
            while (true) {
                DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
                String msgEnviar = teclado.nextLine(); //lê do teclado
                out.writeUTF(msgEnviar);
            }
        } catch (IOException ex) {
            System.out.println("Erro - porta já em uso");
        }
    }
}
