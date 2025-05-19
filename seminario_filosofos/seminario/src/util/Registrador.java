package util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Registrador {
    private static final ConcurrentLinkedQueue<String> registros = new ConcurrentLinkedQueue<>();

    public static synchronized void registrar(int id, String mensagem) {
        String log = String.format("Filósofo %d - %s - %d", id, mensagem, System.currentTimeMillis());
        registros.add(log);
        System.out.println(log);
    }

    public static String obterRegistros() {
        return String.join("\n", registros);
    }
}