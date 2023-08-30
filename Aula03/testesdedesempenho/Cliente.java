package testesdedesempenho;

import ex1.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    public static void main(String[] args) {
        try {
            //Criação do socket UDP
            DatagramSocket socket = new DatagramSocket();
            
            for (int i = 0; i < 100000; i++) {
                //Criação de um pacote datagrama para ser enviado ao servidor
                String mensagem = "pacote "+i;
                int porta = 1234+(i%3);
                byte buffer[] = mensagem.getBytes();//converte em bytes
                DatagramPacket pacoteEnviar = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), porta);
                socket.send(pacoteEnviar);
            }
            
        } catch (UnknownHostException ex) {
            System.out.println("Host desconhecido");
        } catch (SocketException ex) {
            System.out.println("Erro na criação do socket");
        } catch (IOException ex) {
            System.out.println("Erro de E/S: "+ex.getMessage());
        }
    }
}
