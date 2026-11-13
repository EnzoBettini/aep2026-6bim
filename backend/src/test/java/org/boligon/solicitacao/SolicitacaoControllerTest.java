package org.boligon.solicitacao;

import org.boligon.solicitacao.controller.SolicitacaoController;
import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.Solicitacao;
import org.boligon.solicitacao.domain.StatusSolicitacao;
import org.boligon.solicitacao.dto.MudarStatusRequest;
import org.boligon.solicitacao.dto.SolicitacaoRequest;
import org.boligon.solicitacao.service.SolicitacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SolicitacaoControllerTest {

    @Mock
    SolicitacaoService solicitacaoService;

    SolicitacaoController controller;

    @BeforeEach
    void setUp() {
        controller = new SolicitacaoController(solicitacaoService);
    }

    @Test
    void deveCriar() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setTitulo("Titulo");

        Solicitacao criada = new Solicitacao("cid1", "Titulo", "d", "b", Categoria.LIXO);
        when(solicitacaoService.criar(request)).thenReturn(criada);

        assertEquals("Titulo", controller.criar(request).getTitulo());
    }

    @Test
    void deveListar() {
        when(solicitacaoService.listar()).thenReturn(List.of());
        assertEquals(0, controller.listar().size());
    }

    @Test
    void deveBuscar() {
        Solicitacao s = new Solicitacao("cid1", "t", "d", "b", Categoria.PODA);
        s.setId("1");
        when(solicitacaoService.buscarPorId("1")).thenReturn(s);

        assertEquals("1", controller.buscar("1").getId());
    }

    @Test
    void deveAtualizar() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        Solicitacao s = new Solicitacao("cid1", "Novo", "d", "b", Categoria.BURACO);
        when(solicitacaoService.atualizar("1", request)).thenReturn(s);

        assertEquals("Novo", controller.atualizar("1", request).getTitulo());
    }

    @Test
    void deveExcluir() {
        controller.excluir("1");
        verify(solicitacaoService).excluir("1");
    }

    @Test
    void deveMudarStatus() {
        MudarStatusRequest request = new MudarStatusRequest();
        request.setStatus(StatusSolicitacao.EM_ANDAMENTO);
        Solicitacao s = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        s.mudarStatus(StatusSolicitacao.EM_ANDAMENTO, "ok");
        when(solicitacaoService.mudarStatus("1", request)).thenReturn(s);

        assertEquals("EM_ANDAMENTO", controller.mudarStatus("1", request).getStatus());
    }

    @Test
    void deveListarHistorico() {
        Solicitacao s = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        when(solicitacaoService.listarHistorico("1")).thenReturn(s.getHistoricoStatus());

        assertEquals(1, controller.listarHistorico("1").size());
    }
}
