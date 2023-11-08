package ex2;

import ex1.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        try {
            //Cria um socket e tenta se conectar ao servidor na porta 1234
            Socket s = new Socket("localhost", 1234);

            //cria uma thread para receber mensagens em paralelo a thread principal
            Thread t = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            //aguarda uma mensagem do servidor
                            DataInputStream in = new DataInputStream(s.getInputStream());
                            String msgRecebida = in.readUTF(); //bloqueante
                            System.out.println("Recebi " + msgRecebida);
                        } catch (IOException ex) {
                            
                            System.out.println("Erro na thread de recebimento de mensagens");
                        }
                    }
                }
            };
            t.start();
            
            while (true) {
                //Envia uma mensagem ao servidor
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                String msgEnviar = teclado.nextLine();//lê do teclado
                out.writeUTF(msgEnviar);
            }

            

        } catch (IOException ex) {
            System.out.println("Erro - servidor não encontrado");
        }
    }
}
