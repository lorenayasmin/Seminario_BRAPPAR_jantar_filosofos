package cliente;

import comum.GerenciadorInterface;
import java.rmi.RemoteException;
import util.Registrador;

public class Filosofo extends Thread {
    private int id;
    private GerenciadorInterface gerenciador;

    public Filosofo(int id, GerenciadorInterface gerenciador) {
        this.id = id;
        this.gerenciador = gerenciador;
    }

    @Override
    public void run() {
        while (true) {
            pensar();
            try {
                gerenciador.solicitarParaComer(id);
                comer();
                gerenciador.terminarDeComer(id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void pensar() {
        Registrador.registrar(id, "Pensando...");
        try {
            Thread.sleep((int) (Math.random() * 1000 + 500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void comer() {
        Registrador.registrar(id, "Comendo...");
        try {
            Thread.sleep((int) (Math.random() * 1000 + 500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

