package ex1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            //Cria um socket e tenta se conectar ao servidor na porta 1234
            Socket s = new Socket("localhost", 1234);
            
            //Envia uma mensagem ao servidor
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeUTF("hello world");
            
            //aguarda uma resposta do servidor
            DataInputStream in = new DataInputStream(s.getInputStream());
            String resposta = in.readUTF(); //bloqueante
            System.out.println("Recebi "+resposta);
            
        } catch (IOException ex) {
            System.out.println("Erro - servidor não encontrado");
        }
    }
}
