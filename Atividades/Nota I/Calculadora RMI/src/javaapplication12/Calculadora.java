import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Calculadora extends UnicastRemoteObject implements ICalculadora {

    public Calculadora() throws RemoteException {
        super();
    }

    public double soma(double a, double b) throws RemoteException {
        return a + b;
    }

    public double subtracao(double a, double b) throws RemoteException {
        return a - b;
    }

    public double multiplicacao(double a, double b) throws RemoteException {
        return a * b;
    }

    public double divisao(double a, double b) throws RemoteException {
        if (b == 0) {
            throw new RemoteException("Divisão por zero não é permitida.");
        }
        return a / b;
    }

    public double potencia(double base, double expoente) throws RemoteException {
        return Math.pow(base, expoente);
    }

    public double raizQuadrada(double x) throws RemoteException {
        if (x < 0) {
            throw new RemoteException("Não é possível calcular a raiz quadrada de um número negativo.");
        }
        return Math.sqrt(x);
    }
}
