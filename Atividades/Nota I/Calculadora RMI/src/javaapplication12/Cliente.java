import java.rmi.Naming;

public class Cliente {
    public static void main(String[] args) {
        try {
            ICalculadora calculadora = (ICalculadora) Naming.lookup("rmi://localhost/Calculadora");

            double a = 10.0;
            double b = 5.0;

            System.out.println("Soma: " + calculadora.soma(a, b));
            System.out.println("Subtracao: " + calculadora.subtracao(a, b));
            System.out.println("Multiplicacao: " + calculadora.multiplicacao(a, b));
            System.out.println("Divicao: " + calculadora.divisao(a, b));
            System.out.println("Potencia: " + calculadora.potencia(a, b));
            System.out.println("Raiz Quadrada de a: " + calculadora.raizQuadrada(a));
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }
}
