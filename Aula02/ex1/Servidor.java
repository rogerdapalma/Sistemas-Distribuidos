package ex1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {

        try {
            //Servidor cria um socket (socket->bind->listen)
            ServerSocket servidor = new ServerSocket(1234);

            while (true) {
                //Servidor aguarda uma conexão de um cliente
                System.out.println("Aguardando conexão de um cliente...");
                Socket cliente = servidor.accept(); //bloqueante

                //Mostra dados do cliente que se conectou
                System.out.println("Recebi uma conexao de um cliente: " + cliente.getInetAddress().getHostAddress() + ":" + cliente.getPort());

                //Servidor aguarda uma mensagem do cliente
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                System.out.println("Vou aguardar uma mensagem do cliente...");
                String mensagem = dis.readUTF();//aguarda uma mensagem -> bloqueante
                System.out.println("Recebi do cliente: " + mensagem);

                //Servidor manda uma resposta ao cliente
                String resposta = mensagem.toUpperCase();//deixa a mensagem do cliente toda maiúscula
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                dos.writeUTF(resposta);//envia uma mensagem ao cliente

                cliente.close();
            }

        } catch (IOException ex) {
            System.out.println("Porta TCP 1234 já está sendo utilizada");
        }
    }
}
