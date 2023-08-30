package ex1;

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
            
            //Criação de um pacote datagrama para ser enviado ao servidor
            String mensagem = "bom dia";
            byte buffer[] = mensagem.getBytes();//converte em bytes
            DatagramPacket pacoteEnviar = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 1234);
            
            //Envia o pacote datagrama para o servidor
            socket.send(pacoteEnviar);
            
            //espera chegar uma resposta
            //socket.setSoTimeout(5000);//define um timeout de 5seg para receber
            byte bufferReceber[] = new byte[100];
            //pacote sem destinatário, pois é para recber dados
            DatagramPacket pacoteReceber = new DatagramPacket(bufferReceber, bufferReceber.length);
            socket.receive(pacoteReceber);//bloqueante
            
            //Retira os dados recebidos do pacote
            String mensagemRecebida = new String(pacoteReceber.getData());
            System.out.println("Recebi do servidor --> "+mensagemRecebida);
            
            
        } catch (UnknownHostException ex) {
            System.out.println("Host desconhecido");
        } catch (SocketException ex) {
            System.out.println("Erro na criação do socket");
        } catch (IOException ex) {
            System.out.println("Erro de E/S: "+ex.getMessage());
        }
    }
}
