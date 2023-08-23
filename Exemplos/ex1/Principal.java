package ex1;

public class Principal {

    //Thread principal
    public static void main(String[] args) {
        
        //1 trhread para bom dia
        Thread t1 = new Thread(){
            //método run define o que a thread deve executar
            public void run(){
                while(true){
                    System.out.println("Bom dia");
                }
            }
        };
        
        //1 thread para boa noite
        Thread t2 = new Thread(){
            public void run(){
                while(true){
                    System.out.println("Boa noite");
                }
            }
        };
        
        t1.start();//start() coloca em execução o run() em PARALELO
        t2.start();//start() coloca em execução o run() em PARALELO
     
        //fluxo principal segue
        while(true){
            System.out.println("Boa tarde");
        }
    }
    
}
