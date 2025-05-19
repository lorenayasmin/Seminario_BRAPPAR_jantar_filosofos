package cliente;

import comum.GerenciadorInterface;

import java.rmi.Naming;
import java.util.Scanner;

public class ClienteInterativo {
    public static void main(String[] args) {
        try {
            GerenciadorInterface gerenciador = (GerenciadorInterface) Naming.lookup("rmi://localhost/Gerenciador");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Cliente Interativo iniciado.");
            String menu = """
                    Comandos:
                    1 - Adicionar filósofo
                    2 - Remover filósofo
                    3 - Ver logs
                    0 - Sair
                    """;

            while (true) {
                System.out.println(menu);
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1 -> {
                        gerenciador.adicionarFilosofo();
                        System.out.println("Filósofo adicionado.");
                    }
                    case 2 -> {
                        System.out.print("ID do filósofo a remover: ");
                        int idRemover = scanner.nextInt();
                        gerenciador.removerFilosofo(idRemover);
                        System.out.println("Filósofo removido.");
                    }
                    case 3 -> {
                        String logs = gerenciador.obterLog();
                        System.out.println("==== LOGS ====");
                        System.out.println(logs);
                    }
                    case 0 -> {
                        System.out.println("Encerrando cliente interativo.");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}