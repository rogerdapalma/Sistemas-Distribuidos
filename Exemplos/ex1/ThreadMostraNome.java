package ex1;

public class ThreadMostraNome extends Thread {
    
    String nome;
    
    public ThreadMostraNome(String _nome){
        this.nome = _nome;
    }
    
    public void run(){
        while(true){
            System.out.println(this.nome);
        }
    }
}
