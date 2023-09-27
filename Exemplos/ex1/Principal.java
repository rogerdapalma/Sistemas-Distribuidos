package ex1;

public class Principal {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ThreadMostraNome t = new ThreadMostraNome("t"+i);
            t.start();
        }

    }
}
