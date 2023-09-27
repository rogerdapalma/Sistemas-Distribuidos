package ex4;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinhaThread extends Thread{
    
    int n;
    public MinhaThread(int _n){
        this.n = _n;
        System.out.println("thread "+getId()+" criada");
    }
    
    public void run(){
        for (int i = 0; i < this.n; i++) {
            System.out.println(getId()+": "+i);
        }
        System.out.println("thread "+getId()+" entrando em espera");
        
        Random r = new Random();
        
        try {
            long tempo = r.nextLong(5000);
            System.out.println(getId()+" vai dormir por "+tempo);
            sleep(tempo);
            System.out.println("thread "+getId()+" saindo da espera");
            System.out.println("thread "+getId()+" - execução finalizada");
        } catch (InterruptedException ex) {
            System.out.println("Falha no sleep");
        }
    }
}
