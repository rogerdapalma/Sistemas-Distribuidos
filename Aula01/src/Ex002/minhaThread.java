
package Ex002;


public class minhaThread extends Thread{
    
    String mensagem = "";
    int delay = 0;
    
    public void run(){
        while(true){
            System.out.println(mensagem);
            
            try {
                sleep(delay);
            } catch (Exception e) {
                System.out.println("erro no sleep");
            }
        }
    }
}
