package ex_wait_notify;

class Mensagem {

    private String text;

    public Mensagem() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

class Receptor extends Thread {

    Mensagem mensagem;

    public Receptor(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public void run() {
        synchronized (mensagem) {
            try {
                System.out.println("Receptor " + getName() + " está esperando pelo notificador");
                mensagem.wait(); //dorme enquanto ninguem mandar ela acordar
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Receptor " + getName() + " terminou de esperar");
        System.out.println("Receptor " + getName() + " recebeu a seguinte mensagem: " + mensagem.getText());
    }
}

class Entregador extends Thread {

    Mensagem mensagem;

    public Entregador(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public void run() {
        System.out.println("Entregador vai dormir por 3 segundos");
        try {
            sleep(3000);
        } catch (InterruptedException e1) {
        }
        synchronized (mensagem) {
            mensagem.setText("Entregador cochilou por 3 segundos");
            System.out.println("Entregador irá notificar a thread que está esperando para ela acordar");
            mensagem.notify(); //acorda uma das receptoras
            //mensagem.notifyAll(); //acorda todas as receptoras
        }

    }
}

public class Ex_wait_notify {

    public static void main(String args[]) {

        Mensagem mensagem = new Mensagem();

        Receptor receptor = new Receptor(mensagem); // Criação da thread Receptor (que irá esperar a notificação) 
        receptor.setName("receptor 1"); //define o nome da thread
        receptor.start();

        /* Criação de 10 threads Receptor (que irão esperar a notificação) */
        /*for (int i = 0; i < 10; i++) {
            Receptor receptor = new Receptor(mensagem);
            receptor.setName("receptor " + i); //define o nome da thread
            receptor.start();
        }*/

        Entregador entregador = new Entregador(mensagem);// Criação da thread Entregadora (que irá notificar a Receptora e fazer com que ela desbloqueie)
        entregador.setName("notifierThread");//define o nome da thread
        entregador.start();
    }
}