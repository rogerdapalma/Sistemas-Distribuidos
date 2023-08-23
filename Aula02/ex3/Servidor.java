package ex3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    public Servidor() {
        //lista de clientes
        ArrayList<Socket> listaClientes = new ArrayList<>();
        try {
            //criação do servidor
            ServerSocket servidor = new ServerSocket(1234);

            while (true) {
                //aguarda uma conexão de um cliente
                Socket cliente = servidor.accept();//bloqueante
                System.out.println("Recebi um cliente");
                //coloca o cliente na lista
                listaClientes.add(cliente);

                //Cria uma thread para receber mensagens do cliente
                Thread t = new Thread() {
                    public void run() {
                        try {
                            String ip = cliente.getInetAddress().getHostAddress();
                            int porta = cliente.getPort();
                            DataInputStream dis = new DataInputStream(cliente.getInputStream());
                            while (true) {
                                //aguarda uma mensagem do cliente
                                String mensagem = dis.readUTF();
                                System.out.println("Recebi e vou repassar: " + mensagem);
                                //repassa a mensagem para os demais clientes
                                for (int i = 0; i < listaClientes.size(); i++) {
                                    Socket cli = listaClientes.get(i);

                                    DataOutputStream dos = new DataOutputStream(cli.getOutputStream());
                                    dos.writeUTF(ip + ":" + porta + " disse: " + mensagem);
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Erro na thread de recebimento de mensagens do servidor");
                        }
                    }
                };
                t.start();
            }

        } catch (IOException ex) {
            System.out.println("Erro na criação do servidor");
        }
    }

    public static void main(String[] args) {

        Servidor app = new Servidor();
    }
}
