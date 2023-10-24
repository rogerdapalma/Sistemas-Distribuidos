import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String[] args) {
        try {
            ICalculadora calculadora = new Calculadora();
            LocateRegistry.createRegistry(1099); // Registro RMI na porta 1099
            Naming.rebind("Calculadora", calculadora); // Associe o objeto calculadora com o nome "Calculadora"
            System.out.println("Servidor da calculadora está pronto.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }
}
