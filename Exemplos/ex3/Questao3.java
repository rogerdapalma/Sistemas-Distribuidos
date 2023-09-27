package ex3;

import java.util.Random;

public class Questao3 {
    
    class SomaLinha extends Thread{
        
        int linha, matriz[][];
        
        SomaLinha(int linha, int[][] matriz){
            this.linha = linha;
            this.matriz = matriz;
            
        }
        public void run(){
            int qtdColunas = matriz[this.linha].length;
            int soma=0;
            for (int i = 0; i < qtdColunas; i++) {
                soma+=matriz[this.linha][i];
            }
            System.out.println("Soma dos emenetos da linha "+this.linha+" = "+soma);
        }
    }
    
    public Questao3(){
        int L = 5, C = 10;
        int[][] matriz = new int[L][C];
        System.out.println("Matriz:");
        Random r = new Random();
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < C; j++) {
                matriz[i][j] = r.nextInt(10);
                System.out.print(matriz[i][j]+"\t");
            }
            System.out.println();
        }
        
        for (int i = 0; i < L; i++) {
            SomaLinha t = new SomaLinha(i, matriz);
            t.start();
        }
        
        
    }
    public static void main(String[] args) {
        Questao3 q = new Questao3();
    }
}
