package ex2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        try {
            //Cria um socket e tenta se conectar ao servidor (socket->connect)
            Socket s = new Socket("localhost", 1234);
            System.out.println("Conectado so servidor");

            Thread t = new Thread() {
                public void run() {
                    try {
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        while (true) {
                            //Cliente aguarda uma mensagem do servidor
                            String mensagem = dis.readUTF();//cliente aguarda mensagem do servidor
                            System.out.println("Servidor disse: " + mensagem);
                        }
                    } catch (IOException e) {
                        System.out.println("Erro na thread de recebimento de mensagens do cliente");
                    }
                }
            };
            t.start();

            //thread principal do cliente fica lendo pelo teclado e mandando pro servidor
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            while (true) {
                //Cliente envia uma mensagem ao servidor
                String mensagem = teclado.nextLine();//lê pelo teclado
                dos.writeUTF(mensagem);//envia a mensagem
            }

            //fecha o socket
            //s.close();
        } catch (IOException ex) {
            System.out.println("Servidor não encontrado");
        }

    }
}
