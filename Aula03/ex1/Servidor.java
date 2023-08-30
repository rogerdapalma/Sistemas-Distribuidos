package ex1;

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
        try {
            //cria o socket UDP (com ip e porta especificados para receber mensagens)
            DatagramSocket socket = new DatagramSocket(1234, InetAddress.getByName("localhost"));

            //espera chegar uma mensagem
            byte buffer[] = new byte[100];
            //pacote sem destinatário, pois é para recber dados
            DatagramPacket pacoteReceber = new DatagramPacket(buffer, buffer.length);
            socket.receive(pacoteReceber);//bloqueante
            
            //Retira os dados recebidos do pacote
            String mensagemRecebida = new String(pacoteReceber.getData());
            InetAddress ipCliente = pacoteReceber.getAddress();
            int portaCliente = pacoteReceber.getPort();
            System.out.println("Recebi do cliente "+ipCliente.toString()+":"+portaCliente+" --> "+mensagemRecebida);
            
            //Monta uma resposta ao cliente
            String mensagemResposta = mensagemRecebida.toUpperCase();
            byte bufferEnviar[] = mensagemResposta.getBytes();
            DatagramPacket pacoteEnviar = new DatagramPacket(bufferEnviar, bufferEnviar.length, ipCliente, portaCliente);
            socket.send(pacoteEnviar);
            
        } catch (UnknownHostException ex) {
            System.out.println("Host desconhecido");
        } catch (SocketException ex) {
            System.out.println("Erro na criação do socket");
        } catch (IOException ex) {
            System.out.println("Erro na criação do pacote de recebimento");
        }
    }
}
