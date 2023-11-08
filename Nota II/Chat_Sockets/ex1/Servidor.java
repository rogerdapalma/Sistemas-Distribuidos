package ex1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
    public static void main(String[] args) {
        
        try {
            //cria um servidor e aloca a porta 1234 para ele
            ServerSocket servidor = new ServerSocket(1234);
            
            //aguarda uma conexão
            Socket cliente = servidor.accept(); //bloqueante
            System.out.println("Recebi uma conexão");
            
            //aguarda uma mensagem do cliente
            DataInputStream in = new DataInputStream(cliente.getInputStream());
            String msgRecebida = in.readUTF();//bloqueante
            System.out.println("Recebi "+msgRecebida);
            
            //envia uma resposta ao cliente
            DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
            String resposta = "Recebi sua mensagem";
            out.writeUTF(resposta);
            
            
            //fecha o servidor e libera a porta
            servidor.close();
        } catch (IOException ex) {
            System.out.println("Erro - porta já em uso");
        }
    }
}
