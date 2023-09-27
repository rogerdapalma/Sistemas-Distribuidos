package ex2;

public class Contador extends Thread{
    public void run(){
        for (int i = 0; i < 10; i++) {
            System.out.println(getId()+":"+i);            
        }
    }
}
