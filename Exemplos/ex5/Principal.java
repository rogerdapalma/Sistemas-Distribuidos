package ex5;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal {
    
    class MinhaThread extends Thread {

        public void run() {
            Random r = new Random();
            long tempo = r.nextLong(5000);
            //System.out.println("Thread " + getId() + " vai dormir por " + tempo);
            try {
                sleep(tempo);
            } catch (InterruptedException ex) {
                System.out.println("Deu pau no sleep da thread " + getId());
            }
            //System.out.println("Thread " + getId() + " acordou e terminou");
            
            //a própria thread cria outra thread da mesma classe
            MinhaThread t = new MinhaThread();
            t.start();
            
        }
    }

    public Principal(){
        //criação de 10 threads
        for (int i = 0; i < 10; i++) {
            MinhaThread t = new MinhaThread();
            t.start();
        }
        
        while(true){
            System.out.println("Threads ativas: "+Thread.activeCount());
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println();
            }*/
        }
    }
    public static void main(String[] args) {
        new Principal();
    }
}
