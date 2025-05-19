package cliente;

import comum.GerenciadorInterface;
import java.rmi.Naming;


public class ClientePrincipal {
    public static void main(String[] args) {
        try {
            GerenciadorInterface gerenciador = (GerenciadorInterface) Naming.lookup("rmi://localhost/Gerenciador");

            // Adiciona 5 filósofos
            for (int i = 0; i < 5; i++) {
                gerenciador.adicionarFilosofo();
                Filosofo f = new Filosofo(i, gerenciador);
                f.start();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
