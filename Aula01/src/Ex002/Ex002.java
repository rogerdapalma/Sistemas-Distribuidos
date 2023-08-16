
package Ex002;


public class Ex002 {
    
    public static void main(String[] args) {
        
        minhaThread t1 = new minhaThread();
        minhaThread t2 = new minhaThread();
        minhaThread t3 = new minhaThread();
        
        t1.mensagem = "bom dia";
        t1.delay = 200;
        t2.mensagem = "bom tarde";
        t2.delay = 500;
        t3.mensagem = "bom noite";
        t3.delay = 700;
        
        
        t1.start();
        t2.start();
        t3.start();
    }
    
}
