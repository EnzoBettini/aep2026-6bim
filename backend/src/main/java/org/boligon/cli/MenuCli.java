package org.boligon.cli;

import org.boligon.solicitacao.Categoria;
import org.boligon.solicitacao.Solicitacao;
import org.boligon.solicitacao.SolicitacaoRequest;
import org.boligon.solicitacao.SolicitacaoService;
import org.boligon.solicitacao.StatusSolicitacao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;

@Component
public class MenuCli implements CommandLineRunner {

    private final SolicitacaoService solicitacaoService;
    private final Scanner scanner;

    @Autowired
    public MenuCli(SolicitacaoService solicitacaoService) {
        this(solicitacaoService, new Scanner(System.in));
    }

    MenuCli(SolicitacaoService solicitacaoService, Scanner scanner) {
        this.solicitacaoService = solicitacaoService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) {
        boolean continuar = true;
        while (continuar) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();
            try {
                continuar = executarOpcao(opcao);
            } catch (RuntimeException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private void exibirMenu() {
        System.out.println();
        System.out.println("=== ObservaAção ===");
        System.out.println("ODS 11 - Cidades e Comunidades Sustentáveis");
        System.out.println("1 - Cadastrar solicitação");
        System.out.println("2 - Listar solicitações");
        System.out.println("3 - Buscar por ID");
        System.out.println("4 - Atualizar solicitação");
        System.out.println("5 - Excluir solicitação");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private boolean executarOpcao(String opcao) {
        switch (opcao) {
            case "1":
                cadastrar();
                return true;
            case "2":
                listar();
                return true;
            case "3":
                buscar();
                return true;
            case "4":
                atualizar();
                return true;
            case "5":
                excluir();
                return true;
            case "0":
                System.out.println("Encerrando...");
                return false;
            default:
                System.out.println("Opção inválida.");
                return true;
        }
    }

    private void cadastrar() {
        SolicitacaoRequest request = lerDadosBasicos();
        Solicitacao criada = solicitacaoService.criar(request);
        System.out.println("Solicitação cadastrada com ID " + criada.getId() + ".");
    }

    private void listar() {
        List<Solicitacao> solicitacoes = solicitacaoService.listar();
        if (solicitacoes.isEmpty()) {
            System.out.println("Nenhuma solicitação cadastrada.");
            return;
        }
        for (Solicitacao solicitacao : solicitacoes) {
            imprimir(solicitacao);
        }
    }

    private void buscar() {
        String id = lerId();
        imprimir(solicitacaoService.buscarPorId(id));
    }

    private void atualizar() {
        String id = lerId();
        SolicitacaoRequest request = lerDadosBasicos();
        request.setStatus(lerStatus());
        Solicitacao atualizada = solicitacaoService.atualizar(id, request);
        System.out.println("Solicitação atualizada:");
        imprimir(atualizada);
    }

    private void excluir() {
        String id = lerId();
        solicitacaoService.excluir(id);
        System.out.println("Solicitação " + id + " excluída.");
    }

    private SolicitacaoRequest lerDadosBasicos() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        System.out.print("Título: ");
        request.setTitulo(scanner.nextLine());
        System.out.print("Descrição: ");
        request.setDescricao(scanner.nextLine());
        System.out.print("Bairro: ");
        request.setBairro(scanner.nextLine());
        request.setCategoria(lerCategoria());
        return request;
    }

    private Categoria lerCategoria() {
        Categoria[] categorias = Categoria.values();
        System.out.println("Categorias:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + " - " + categorias[i]);
        }
        System.out.print("Categoria: ");
        return categorias[lerIndice(categorias.length)];
    }

    private StatusSolicitacao lerStatus() {
        StatusSolicitacao[] status = StatusSolicitacao.values();
        System.out.println("Status:");
        for (int i = 0; i < status.length; i++) {
            System.out.println((i + 1) + " - " + status[i]);
        }
        System.out.print("Status: ");
        return status[lerIndice(status.length)];
    }

    private String lerId() {
        System.out.print("ID: ");
        String valor = scanner.nextLine().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("Informe o ID.");
        }
        return valor;
    }

    private int lerIndice(int quantidade) {
        String valor = scanner.nextLine().trim();
        try {
            int indice = Integer.parseInt(valor);
            if (indice < 1 || indice > quantidade) {
                throw new IllegalArgumentException("Opção fora da lista.");
            }
            return indice - 1;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Informe o número da opção.");
        }
    }

    private void imprimir(Solicitacao solicitacao) {
        System.out.println(
                "#" + solicitacao.getId()
                        + " | " + solicitacao.getTitulo()
                        + " | " + solicitacao.getBairro()
                        + " | " + solicitacao.getCategoria()
                        + " | " + solicitacao.getStatus()
        );
        System.out.println("  " + solicitacao.getDescricao());
    }
}
