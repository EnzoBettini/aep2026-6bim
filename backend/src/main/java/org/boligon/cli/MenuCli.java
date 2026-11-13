package org.boligon.cli;

import org.boligon.cidadao.domain.Cidadao;
import org.boligon.cidadao.dto.CidadaoRequest;
import org.boligon.cidadao.service.CidadaoService;
import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.HistoricoStatus;
import org.boligon.solicitacao.domain.Solicitacao;
import org.boligon.solicitacao.domain.StatusSolicitacao;
import org.boligon.solicitacao.dto.MudarStatusRequest;
import org.boligon.solicitacao.dto.SolicitacaoRequest;
import org.boligon.solicitacao.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MenuCli implements CommandLineRunner {

    private final SolicitacaoService solicitacaoService;
    private final CidadaoService cidadaoService;
    private final Scanner scanner;

    @Autowired
    public MenuCli(SolicitacaoService solicitacaoService, CidadaoService cidadaoService) {
        this(solicitacaoService, cidadaoService, new Scanner(System.in));
    }

    MenuCli(SolicitacaoService solicitacaoService, CidadaoService cidadaoService, Scanner scanner) {
        this.solicitacaoService = solicitacaoService;
        this.cidadaoService = cidadaoService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) {
        boolean continuar = true;
        while (continuar) {
            exibirMenuPrincipal();
            String opcao = scanner.nextLine().trim();
            try {
                continuar = executarMenuPrincipal(opcao);
            } catch (RuntimeException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println();
        System.out.println("=== ObservaAção ===");
        System.out.println("ODS 11 - Cidades e Comunidades Sustentáveis");
        System.out.println("1 - Cidadãos");
        System.out.println("2 - Solicitações");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private boolean executarMenuPrincipal(String opcao) {
        switch (opcao) {
            case "1":
                menuCidadaos();
                return true;
            case "2":
                menuSolicitacoes();
                return true;
            case "0":
                System.out.println("Encerrando...");
                return false;
            default:
                System.out.println("Opção inválida.");
                return true;
        }
    }

    private void menuCidadaos() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println();
            System.out.println("--- Cidadãos ---");
            System.out.println("1 - Cadastrar cidadão");
            System.out.println("2 - Listar cidadãos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine().trim();
            try {
                switch (opcao) {
                    case "1" -> cadastrarCidadao();
                    case "2" -> listarCidadaos();
                    case "0" -> voltar = true;
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RuntimeException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private void menuSolicitacoes() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println();
            System.out.println("--- Solicitações ---");
            System.out.println("1 - Cadastrar solicitação");
            System.out.println("2 - Listar solicitações");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar solicitação");
            System.out.println("5 - Mudar status (com observação)");
            System.out.println("6 - Ver histórico de status");
            System.out.println("7 - Excluir solicitação");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine().trim();
            try {
                switch (opcao) {
                    case "1" -> cadastrarSolicitacao();
                    case "2" -> listarSolicitacoes();
                    case "3" -> buscarSolicitacao();
                    case "4" -> atualizarSolicitacao();
                    case "5" -> mudarStatusSolicitacao();
                    case "6" -> exibirHistoricoSolicitacao();
                    case "7" -> excluirSolicitacao();
                    case "0" -> voltar = true;
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RuntimeException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private void cadastrarCidadao() {
        CidadaoRequest request = new CidadaoRequest();
        System.out.print("Nome: ");
        request.setNome(scanner.nextLine());
        System.out.print("E-mail: ");
        request.setEmail(scanner.nextLine());
        System.out.print("Telefone: ");
        request.setTelefone(scanner.nextLine());
        Cidadao criado = cidadaoService.criar(request);
        System.out.println("Cidadão cadastrado com ID " + criado.getId() + ".");
    }

    private void listarCidadaos() {
        List<Cidadao> cidadaos = cidadaoService.listar();
        if (cidadaos.isEmpty()) {
            System.out.println("Nenhum cidadão cadastrado.");
            return;
        }
        for (Cidadao cidadao : cidadaos) {
            System.out.println(
                    "#" + cidadao.getId()
                            + " | " + cidadao.getNome()
                            + " | " + cidadao.getEmail()
                            + " | " + cidadao.getTelefone()
            );
        }
    }

    private void cadastrarSolicitacao() {
        System.out.println("(Use o ID de um cidadão já cadastrado; liste em Cidadãos > Listar)");
        SolicitacaoRequest request = lerDadosBasicosSolicitacao();
        Solicitacao criada = solicitacaoService.criar(request);
        System.out.println("Solicitação cadastrada com ID " + criada.getId() + ".");
    }

    private void listarSolicitacoes() {
        List<Solicitacao> solicitacoes = solicitacaoService.listar();
        if (solicitacoes.isEmpty()) {
            System.out.println("Nenhuma solicitação cadastrada.");
            return;
        }
        for (Solicitacao solicitacao : solicitacoes) {
            imprimirSolicitacao(solicitacao);
        }
    }

    private void buscarSolicitacao() {
        String id = lerId();
        imprimirSolicitacao(solicitacaoService.buscarPorId(id));
    }

    private void atualizarSolicitacao() {
        String id = lerId();
        SolicitacaoRequest request = lerDadosBasicosSolicitacao();
        request.setStatus(lerStatus());
        Solicitacao atualizada = solicitacaoService.atualizar(id, request);
        System.out.println("Solicitação atualizada:");
        imprimirSolicitacao(atualizada);
    }

    private void mudarStatusSolicitacao() {
        String id = lerId();
        StatusSolicitacao status = lerStatus();
        System.out.print("Observação: ");
        String observacao = scanner.nextLine();
        MudarStatusRequest request = new MudarStatusRequest();
        request.setStatus(status);
        request.setObservacao(observacao);
        Solicitacao atualizada = solicitacaoService.mudarStatus(id, request);
        System.out.println("Status alterado:");
        imprimirSolicitacao(atualizada);
    }

    private void exibirHistoricoSolicitacao() {
        String id = lerId();
        List<HistoricoStatus> historico = solicitacaoService.listarHistorico(id);
        if (historico.isEmpty()) {
            System.out.println("Nenhum registro no histórico.");
            return;
        }
        System.out.println("Histórico (mais antigo → mais recente):");
        int ordem = 1;
        for (HistoricoStatus item : historico) {
            System.out.println(
                    ordem++
                            + ") "
                            + item.getStatus()
                            + " em "
                            + item.getData()
                            + " — "
                            + item.getObservacao()
            );
        }
    }

    private void excluirSolicitacao() {
        String id = lerId();
        solicitacaoService.excluir(id);
        System.out.println("Solicitação " + id + " excluída.");
    }

    private SolicitacaoRequest lerDadosBasicosSolicitacao() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        System.out.print("ID do cidadão responsável: ");
        request.setCidadaoId(scanner.nextLine());
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

    private void imprimirSolicitacao(Solicitacao solicitacao) {
        System.out.println(
                "#" + solicitacao.getId()
                        + " | cidadão " + solicitacao.getCidadaoId()
                        + " | " + solicitacao.getTitulo()
                        + " | " + solicitacao.getBairro()
                        + " | " + solicitacao.getCategoria()
                        + " | " + solicitacao.getStatus()
        );
        System.out.println("  " + solicitacao.getDescricao());
    }
}
