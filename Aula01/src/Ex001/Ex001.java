package Ex001;

public class Ex001 {

    public static void main(String[] args) {

        //trhread 1
        Thread t1 = new Thread() {
            public void run() { //define oque a thread deve executar
                while (true) { //laço infinito 
                    System.out.println("bom dia");
                }
            }

        };
        //trhread 2
        Thread t2 = new Thread() {
            public void run() { //define oque a thread deve executar
                while (true) { //laço infinito 
                    System.out.println("boa noite");
                }
            }

        };

        t1.start(); //start() coloca em execução o run() em paralelo
        t2.start(); //start() coloca em execução o run() em paralelo

        //fluxo principal segue 
        while (true) {
            System.out.println("boa tarde");
        }
    }
}
