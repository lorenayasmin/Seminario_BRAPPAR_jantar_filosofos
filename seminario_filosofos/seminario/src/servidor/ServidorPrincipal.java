package servidor;

import comum.GerenciadorInterface;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServidorPrincipal {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            GerenciadorInterface gerenciador = new Gerenciador();
            Naming.rebind("rmi://localhost/Gerenciador", gerenciador);
            System.out.println("Servidor RMI pronto.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
