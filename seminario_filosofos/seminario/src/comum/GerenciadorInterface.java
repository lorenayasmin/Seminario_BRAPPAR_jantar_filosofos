package comum;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GerenciadorInterface extends Remote {
    void solicitarParaComer(int idFilosofo) throws RemoteException;
    void terminarDeComer(int idFilosofo) throws RemoteException;
    void adicionarFilosofo() throws RemoteException;
    void removerFilosofo(int idFilosofo) throws RemoteException;
    String obterLog() throws RemoteException;
}