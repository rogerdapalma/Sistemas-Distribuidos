package ex3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    ArrayList<Socket> listaClientes = new ArrayList<>();

    public Servidor() {
        try {
            //cria o servidor
            ServerSocket servidor = new ServerSocket(1234);

            while (true) {
                Socket cliente = servidor.accept();//aguarda uma conexao
                listaClientes.add(cliente);
                System.out.println("Recebi uma conexão");

                //cria uma thread para cada cliente que conecta para receber as mensagens dele
                Thread t = new Thread() {
                    Socket meuCliente = cliente;
                    
                    public void run() {
                        try {
                            String dadosCliente = meuCliente.getInetAddress().toString()+":"+meuCliente.getPort();
                            DataInputStream in = new DataInputStream(meuCliente.getInputStream());
                            while (true) {
                                String msgRec = in.readUTF();//espera uma msg do cliente
                                //percorre a lista de clientes e repassa a mensagem
                                for (Socket cli : listaClientes) {
                                    DataOutputStream out = new DataOutputStream(cli.getOutputStream());
                                    
                                    out.writeUTF(dadosCliente+" disse: "+msgRec);
                                }

                            }
                        } catch (IOException e) {
                            System.out.println("Erro em uma das threads");
                            listaClientes.remove(meuCliente);
                        }
                    }
                };
                t.start();
            }
        } catch (IOException ex) {
            System.out.println("Porta já utilizada");
        }

    }

    public static void main(String[] args) {
        Servidor serv = new Servidor();
    }
}
