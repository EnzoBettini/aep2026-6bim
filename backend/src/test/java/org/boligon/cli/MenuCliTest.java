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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuCliTest {

    @Mock
    SolicitacaoService solicitacaoService;

    @Mock
    CidadaoService cidadaoService;

    private MenuCli menuComEntrada(String entrada) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));
        return new MenuCli(solicitacaoService, cidadaoService, scanner);
    }

    @Test
    void deveCadastrarCidadaoEVoltar() {
        Cidadao criado = new Cidadao("Ana", "ana@test.com", "41999999999");
        criado.setId("c1");
        when(cidadaoService.criar(any(CidadaoRequest.class))).thenReturn(criado);

        menuComEntrada("1\n1\nAna\nana@test.com\n41999999999\n0\n0\n").run();

        verify(cidadaoService).criar(any(CidadaoRequest.class));
    }

    @Test
    void deveCadastrarSolicitacaoESair() {
        Solicitacao criada = new Solicitacao("cid1", "Titulo", "Desc", "Centro", Categoria.ILUMINACAO);
        criada.setId("abc");
        when(solicitacaoService.criar(any(SolicitacaoRequest.class))).thenReturn(criada);

        menuComEntrada("2\n1\ncid1\nTitulo\nDesc\nCentro\n1\n0\n0\n").run();

        verify(solicitacaoService).criar(any(SolicitacaoRequest.class));
    }

    @Test
    void deveListarSolicitacoesESair() {
        Solicitacao s = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        s.setId("1");
        when(solicitacaoService.listar()).thenReturn(List.of(s));

        menuComEntrada("2\n2\n0\n0\n").run();

        verify(solicitacaoService).listar();
    }

    @Test
    void deveMudarStatusComObservacao() {
        Solicitacao atualizada = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        atualizada.setId("1");
        when(solicitacaoService.mudarStatus(eq("1"), any(MudarStatusRequest.class))).thenReturn(atualizada);

        menuComEntrada("2\n5\n1\n2\nEm análise\n0\n0\n").run();

        verify(solicitacaoService).mudarStatus(eq("1"), any(MudarStatusRequest.class));
    }

    @Test
    void deveExibirHistorico() {
        HistoricoStatus h = new HistoricoStatus(StatusSolicitacao.ABERTA, Instant.parse("2026-01-01T12:00:00Z"), "Criada");
        when(solicitacaoService.listarHistorico("1")).thenReturn(List.of(h));

        menuComEntrada("2\n6\n1\n0\n0\n").run();

        verify(solicitacaoService).listarHistorico("1");
    }

    @Test
    void deveExcluirSolicitacao() {
        menuComEntrada("2\n7\n1\n0\n0\n").run();

        verify(solicitacaoService).excluir("1");
    }
}
