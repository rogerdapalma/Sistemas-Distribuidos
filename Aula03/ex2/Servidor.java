package ex2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Servidor {

    class ClienteInfo {

        InetAddress ip;
        int porta;

        ClienteInfo(InetAddress ip, int porta) {
            this.ip = ip;
            this.porta = porta;
        }
    }

    class PixelInfo {

        int x, y, r, g, b;

        public PixelInfo(int x, int y, int r, int g, int b) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.g = g;
            this.b = b;
        }

    }

    //lista de clientes
    ArrayList<ClienteInfo> listaClientes = new ArrayList<>();
    ArrayList<PixelInfo> listaPixeis = new ArrayList<>();

    public void adicionaPixelLista(int x, int y, int r, int g, int b) {
        boolean achei = false;
        //verifica se já não tem um pixel nessa posição
        for (PixelInfo pixel : listaPixeis) {
            if (pixel.x == x && pixel.y == y) {
                pixel.r = r;
                pixel.g = g;
                pixel.b = b;
                achei = true;
                break;
            }
        }
        if (!achei) {//se não achou o pixel na lista, poe na lista
            PixelInfo pixel = new PixelInfo(x, y, r, g, b);
            listaPixeis.add(pixel);
        }
    }

    public Servidor() {

        try {
            DatagramSocket socket = new DatagramSocket(1234);
            while (true) {
                //espera chegar uma mensagem
                byte buffer[] = new byte[100];
                //pacote sem destinatário, pois é para recber dados
                DatagramPacket pacoteReceber = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacoteReceber);//bloqueante

                //Retira os dados recebidos do pacote
                String mensagemRecebida = new String(pacoteReceber.getData());

                //Servidor recebeu uma mensagem de um cliente pedindo para ser colocado na lista
                if (mensagemRecebida.contains("ME_POE_NA_LISTA")) {
                    InetAddress ipCliente = pacoteReceber.getAddress();
                    int portaCliente = pacoteReceber.getPort();
                    ClienteInfo info = new ClienteInfo(ipCliente, portaCliente);
                    listaClientes.add(info);
                    //System.out.println("Cliente " + ipCliente.toString() + ":" + portaCliente + " pediu para ser colocado na lista");

                    //responde com a lista de pixeis já desenhados
                    String mensagemResposta = "LISTA_PIXEIS:";
                    for (PixelInfo pixel : listaPixeis) {
                        mensagemResposta = mensagemResposta + pixel.x + "," + pixel.y + "," + pixel.r + "," + pixel.g + "," + pixel.b + "_";

                    }
                    byte bufferEnviar[] = mensagemResposta.getBytes();
                    DatagramPacket pacoteEnviar = new DatagramPacket(bufferEnviar, bufferEnviar.length, ipCliente, portaCliente);
                    socket.send(pacoteEnviar);

                } else if (mensagemRecebida.contains("PIXEL")) {
                    /*System.out.println("Recebi um pixel");
                    System.out.println(mensagemRecebida);*/
                    String msgPartes[] = mensagemRecebida.split(":");
                    String dadosPixel[] = msgPartes[1].split(",");
                    int x = Integer.parseInt(dadosPixel[0]);
                    int y = Integer.parseInt(dadosPixel[1]);
                    int red = Integer.parseInt(dadosPixel[2]);
                    int green = Integer.parseInt(dadosPixel[3]);
                    int blue = Integer.parseInt(dadosPixel[4].trim());
                    /*PixelInfo pixel = new PixelInfo(x, y, red, green, blue);
                    listaPixeis.add(pixel);*/
                    adicionaPixelLista(x, y, red, green, blue);

                    //Repassa para os demais clientes
                    for (ClienteInfo cli : listaClientes) {
                        byte bufferEnviar[] = mensagemRecebida.getBytes();
                        DatagramPacket pacoteEnviar = new DatagramPacket(bufferEnviar, bufferEnviar.length, cli.ip, cli.porta);
                        socket.send(pacoteEnviar);
                    }
                }

            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        new Servidor();
    }
}
