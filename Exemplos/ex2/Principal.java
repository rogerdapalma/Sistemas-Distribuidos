package ex2;

public class Principal {
    public static void main(String[] args) {
        
        MinhaThread t1 = new MinhaThread();
        MinhaThread t2 = new MinhaThread();
        MinhaThread t3 = new MinhaThread();
        
        t1.mensagem = "bom dia";
        t1.delay = 200;
        t2.mensagem = "boa tarde";
        t2.delay = 500;
        t3.mensagem = "boa noite";
        t3.delay = 100;
        
        t1.start();
        t2.start();
        t3.start();
    }
}
