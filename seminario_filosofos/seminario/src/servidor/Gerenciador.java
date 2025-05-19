package servidor;

import comum.GerenciadorInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import util.Registrador;

public class Gerenciador extends UnicastRemoteObject implements GerenciadorInterface {
    private final Map<Integer, EstadoFilosofo> estados = new HashMap<>();
    private final Set<Integer> garfosDisponiveis = new HashSet<>();
    private final Map<Integer, Long> tempoEspera = new HashMap<>();
    private int proximoId = 0;

    public Gerenciador() throws RemoteException {
        super();
    }

    @Override
    public synchronized void solicitarParaComer(int id) throws RemoteException {
        tempoEspera.putIfAbsent(id, System.currentTimeMillis());
        estados.putIfAbsent(id, EstadoFilosofo.PENSANDO);

        while (!podeComer(id)) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        estados.put(id, EstadoFilosofo.COMENDO);
        tempoEspera.remove(id);
        garfosDisponiveis.remove(id);
        garfosDisponiveis.remove(vizinhoEsquerdo(id));
        garfosDisponiveis.remove(vizinhoDireito(id));
        Registrador.registrar(id, "Começou a comer.");
    }

    @Override
    public synchronized void terminarDeComer(int id) throws RemoteException {
        estados.put(id, EstadoFilosofo.PENSANDO);
        garfosDisponiveis.add(id);
        garfosDisponiveis.add(vizinhoEsquerdo(id));
        garfosDisponiveis.add(vizinhoDireito(id));
        Registrador.registrar(id, "Terminou de comer.");
        notifyAll();
    }

    @Override
    public synchronized void adicionarFilosofo() throws RemoteException {
        int id = proximoId++;
        estados.put(id, EstadoFilosofo.PENSANDO);
        garfosDisponiveis.add(id);
        Registrador.registrar(id, "Adicionado.");
    }

    @Override
    public synchronized void removerFilosofo(int id) throws RemoteException {
        estados.remove(id);
        tempoEspera.remove(id);
        garfosDisponiveis.remove(id);
        Registrador.registrar(id, "Removido.");
    }

    @Override
    public String obterLog() throws RemoteException {
        return Registrador.obterRegistros();
    }

    private int vizinhoEsquerdo(int id) {
        return (id - 1 + proximoId) % proximoId;
    }

    private int vizinhoDireito(int id) {
        return (id + 1) % proximoId;
    }

    private boolean podeComer(int id) {
        long espera = System.currentTimeMillis() - tempoEspera.getOrDefault(id, System.currentTimeMillis());
        int esquerda = vizinhoEsquerdo(id);
        int direita = vizinhoDireito(id);

        boolean garfosLivres = garfosDisponiveis.contains(id) &&
                               garfosDisponiveis.contains(esquerda) &&
                               garfosDisponiveis.contains(direita);

        boolean prioridade = true;
        for (var entry : tempoEspera.entrySet()) {
            if (entry.getKey() != id && entry.getValue() < tempoEspera.getOrDefault(id, 0L)) {
                prioridade = false;
                break;
            }
        }

        return garfosLivres && prioridade;
    }

    private enum EstadoFilosofo {
        PENSANDO, COMENDO
    }
}