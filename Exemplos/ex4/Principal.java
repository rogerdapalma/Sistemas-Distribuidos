package ex4;

import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Quantas threads? ");
        int qtdThreads = teclado.nextInt();
        System.out.println(qtdThreads);
        
        Random r = new Random();
        
        for (int i = 0; i < qtdThreads; i++) {
            MinhaThread t = new MinhaThread(r.nextInt(100));
            t.start();
        }
        
        
    }
}
