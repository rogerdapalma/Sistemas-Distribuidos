package ex2;

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
            //Servidor cria um socket (socket->bind->listen)
            ServerSocket servidor = new ServerSocket(1234);

            //Servidor aguarda uma conexão de um cliente
            System.out.println("Aguardando conexão de um cliente...");
            Socket cliente = servidor.accept(); //bloqueante
            //Mostra dados do cliente que se conectou
            System.out.println("Recebi uma conexao de um cliente: " + cliente.getInetAddress().getHostAddress() + ":" + cliente.getPort());

            //Thread do servidor que fica aguardando mensagens do cliente
            Thread t = new Thread() {
                public void run() {
                    try {
                        DataInputStream dis = new DataInputStream(cliente.getInputStream());
                        while (true) {
                            String mensagem = dis.readUTF();//aguarda uma mensagem -> bloqueante
                            System.out.println("Cliente disse: " + mensagem);
                        }
                    } catch (IOException e) {
                        System.out.println("Erro na thread de recebimento de mensagens do servidor");
                    }
                }
            };
            t.start();

            //Thread principal (main) fica responsável por mandar mensagens ao cliente
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            while (true) {
                String mensagem = teclado.nextLine();//deixa a mensagem do cliente toda maiúscula
                dos.writeUTF(mensagem);//envia uma mensagem ao cliente
            }

            //cliente.close();
        } catch (IOException ex) {
            System.out.println("Porta TCP 1234 já está sendo utilizada");
        }
    }
}
