package org.boligon.cidadao;

import org.boligon.solicitacao.Solicitacao;
import org.boligon.solicitacao.SolicitacaoService;
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
class CidadaoControllerTest {

    @Mock
    CidadaoService cidadaoService;

    @Mock
    SolicitacaoService solicitacaoService;

    CidadaoController controller;

    @BeforeEach
    void setUp() {
        controller = new CidadaoController(cidadaoService, solicitacaoService);
    }

    @Test
    void deveCriar() {
        CidadaoRequest request = new CidadaoRequest();
        request.setNome("Maria");

        Cidadao criado = new Cidadao("Maria", "m@e.com", null);
        when(cidadaoService.criar(request)).thenReturn(criado);

        assertEquals("Maria", controller.criar(request).getNome());
    }

    @Test
    void deveListar() {
        when(cidadaoService.listar()).thenReturn(List.of());
        assertEquals(0, controller.listar().size());
    }

    @Test
    void deveBuscar() {
        Cidadao c = new Cidadao("J", "j@e.com", null);
        c.setId("1");
        when(cidadaoService.buscarPorId("1")).thenReturn(c);

        assertEquals("1", controller.buscar("1").getId());
    }

    @Test
    void deveAtualizar() {
        CidadaoRequest request = new CidadaoRequest();
        Cidadao c = new Cidadao("Novo", "n@e.com", null);
        when(cidadaoService.atualizar("1", request)).thenReturn(c);

        assertEquals("Novo", controller.atualizar("1", request).getNome());
    }

    @Test
    void deveExcluir() {
        controller.excluir("1");
        verify(cidadaoService).excluir("1");
    }

    @Test
    void deveListarSolicitacoesDoCidadao() {
        when(solicitacaoService.listarPorCidadaoId("1")).thenReturn(List.of());

        assertEquals(0, controller.listarSolicitacoes("1").size());
    }
}
