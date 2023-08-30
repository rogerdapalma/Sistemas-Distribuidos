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

public class Servidor {

    public static void main(String[] args) {
        new Thread() {
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(1234, InetAddress.getByName("localhost"));
                    int count = 0;
                    while (true) {
                        //espera chegar uma mensagem
                        byte buffer[] = new byte[100];
                        //pacote sem destinatário, pois é para recber dados
                        DatagramPacket pacoteReceber = new DatagramPacket(buffer, buffer.length);

                        socket.receive(pacoteReceber);//bloqueante

                        //Retira os dados recebidos do pacote
                        String mensagemRecebida = new String(pacoteReceber.getData());
                        System.out.println("Thread 1 " + count);
                        count++;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }.start();

        new Thread() {
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(1235, InetAddress.getByName("localhost"));
                    int count = 0;
                    while (true) {
                        //espera chegar uma mensagem
                        byte buffer[] = new byte[100];
                        //pacote sem destinatário, pois é para recber dados
                        DatagramPacket pacoteReceber = new DatagramPacket(buffer, buffer.length);

                        socket.receive(pacoteReceber);//bloqueante

                        //Retira os dados recebidos do pacote
                        String mensagemRecebida = new String(pacoteReceber.getData());
                        System.out.println("Thread 2 " + count);
                        count++;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
        
        new Thread() {
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(1236, InetAddress.getByName("localhost"));
                    int count = 0;
                    while (true) {
                        //espera chegar uma mensagem
                        byte buffer[] = new byte[100];
                        //pacote sem destinatário, pois é para recber dados
                        DatagramPacket pacoteReceber = new DatagramPacket(buffer, buffer.length);

                        socket.receive(pacoteReceber);//bloqueante

                        //Retira os dados recebidos do pacote
                        String mensagemRecebida = new String(pacoteReceber.getData());
                        System.out.println("Thread 3 " + count);
                        count++;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

    }
}
