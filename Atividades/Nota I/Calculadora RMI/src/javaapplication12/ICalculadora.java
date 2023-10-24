import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculadora extends Remote {
    double soma(double a, double b) throws RemoteException;
    double subtracao(double a, double b) throws RemoteException;
    double multiplicacao(double a, double b) throws RemoteException;
    double divisao(double a, double b) throws RemoteException;
    double potencia(double base, double expoente) throws RemoteException;
    double raizQuadrada(double x) throws RemoteException;
}
