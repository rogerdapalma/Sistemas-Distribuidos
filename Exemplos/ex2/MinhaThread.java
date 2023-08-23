package ex2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MinhaThread extends Thread {
    
    String mensagem = "";
    int delay = 0;
    
    public void run(){
        while(true){
            System.out.println(mensagem);
            try {
                sleep(delay); //dorme 0.5 seg
            } catch (InterruptedException ex) {
                System.out.println("Deu erro no sleep");
            }
        }
    }
}
