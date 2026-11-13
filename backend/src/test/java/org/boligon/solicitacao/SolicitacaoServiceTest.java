package org.boligon.solicitacao;

import org.boligon.cidadao.domain.Cidadao;
import org.boligon.cidadao.service.CidadaoService;
import org.boligon.exception.EntidadeNaoEncontradaException;
import org.boligon.exception.ValidacaoException;
import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.Solicitacao;
import org.boligon.solicitacao.domain.StatusSolicitacao;
import org.boligon.solicitacao.dto.MudarStatusRequest;
import org.boligon.solicitacao.dto.SolicitacaoRequest;
import org.boligon.solicitacao.repository.SolicitacaoRepository;
import org.boligon.solicitacao.service.SolicitacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SolicitacaoServiceTest {

    @Mock
    SolicitacaoRepository repository;

    @Mock
    CidadaoService cidadaoService;

    SolicitacaoService solicitacaoService;

    @BeforeEach
    void setUp() {
        solicitacaoService = new SolicitacaoService(repository, cidadaoService);
    }

    @Test
    void deveCriarSolicitacao() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setCidadaoId("cid1");
        request.setTitulo("Titulo");
        request.setDescricao("Descricao");
        request.setBairro("Bairro");
        request.setCategoria(Categoria.ILUMINACAO);

        when(cidadaoService.buscarPorId("cid1")).thenReturn(new Cidadao("Maria", "m@e.com", null));
        when(repository.save(any(Solicitacao.class))).thenAnswer(inv -> inv.getArgument(0));

        Solicitacao entidade = solicitacaoService.criar(request);

        assertEquals("cid1", entidade.getCidadaoId());
        ArgumentCaptor<Solicitacao> captor = ArgumentCaptor.forClass(Solicitacao.class);
        verify(repository).save(captor.capture());
        assertEquals(1, captor.getValue().getHistoricoStatus().size());
    }

    @Test
    void deveRecusarCidadaoInexistenteAoCriar() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setCidadaoId("x");
        request.setTitulo("Titulo");
        request.setDescricao("Descricao");
        request.setBairro("Bairro");
        request.setCategoria(Categoria.ILUMINACAO);

        when(cidadaoService.buscarPorId("x"))
                .thenThrow(new EntidadeNaoEncontradaException("Cidadão não encontrado."));

        try {
            solicitacaoService.criar(request);
            fail();
        } catch (EntidadeNaoEncontradaException ex) {
            assertEquals("Cidadão não encontrado.", ex.getMessage());
        }
    }

    @Test
    void deveRecusarCidadaoVazio() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setCidadaoId(" ");
        request.setTitulo("Titulo");
        request.setDescricao("Descricao");
        request.setBairro("Bairro");
        request.setCategoria(Categoria.ILUMINACAO);

        try {
            solicitacaoService.criar(request);
            fail();
        } catch (ValidacaoException ex) {
            assertEquals("O cidadão é obrigatório.", ex.getMessage());
        }
    }

    @Test
    void deveAtualizarSolicitacao() {
        Solicitacao existente = new Solicitacao("cid1", "antigo", "d", "b", Categoria.BURACO);
        existente.setId("1");
        when(repository.findById("1")).thenReturn(Optional.of(existente));
        when(cidadaoService.buscarPorId("cid2")).thenReturn(new Cidadao("B", "b@c.com", null));
        when(repository.save(any(Solicitacao.class))).thenReturn(existente);

        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setCidadaoId("cid2");
        request.setTitulo("Novo");
        request.setDescricao("Descricao");
        request.setBairro("Bairro");
        request.setCategoria(Categoria.ILUMINACAO);
        request.setStatus(StatusSolicitacao.EM_ANDAMENTO);

        assertEquals("cid2", solicitacaoService.atualizar("1", request).getCidadaoId());
    }

    @Test
    void deveListarPorCidadao() {
        when(cidadaoService.buscarPorId("cid1")).thenReturn(new Cidadao("A", "a@b.com", null));
        when(repository.findByCidadaoId("cid1")).thenReturn(List.of());

        assertEquals(0, solicitacaoService.listarPorCidadaoId("cid1").size());
    }

    @Test
    void deveExcluirSolicitacao() {
        Solicitacao existente = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        existente.setId("9");
        when(repository.findById("9")).thenReturn(Optional.of(existente));

        solicitacaoService.excluir("9");

        verify(repository).delete(existente);
    }

    @Test
    void deveRecusarMudancaParaMesmoStatus() {
        Solicitacao existente = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        existente.setId("1");
        when(repository.findById("1")).thenReturn(Optional.of(existente));

        MudarStatusRequest request = new MudarStatusRequest();
        request.setStatus(StatusSolicitacao.ABERTA);

        try {
            solicitacaoService.mudarStatus("1", request);
            fail();
        } catch (ValidacaoException ex) {
            assertEquals("A solicitação já está com este status.", ex.getMessage());
        }
    }

    @Test
    void deveMudarStatusComHistorico() {
        Solicitacao existente = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        existente.setId("1");
        when(repository.findById("1")).thenReturn(Optional.of(existente));
        when(repository.save(any(Solicitacao.class))).thenAnswer(inv -> inv.getArgument(0));

        MudarStatusRequest request = new MudarStatusRequest();
        request.setStatus(StatusSolicitacao.EM_ANDAMENTO);
        request.setObservacao("Equipe enviada");

        assertEquals(2, solicitacaoService.mudarStatus("1", request).getHistoricoStatus().size());
    }

    @Test
    void deveRecusarReabrirResolvida() {
        Solicitacao existente = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        existente.setId("1");
        existente.mudarStatus(StatusSolicitacao.RESOLVIDA, "Concluída");
        when(repository.findById("1")).thenReturn(Optional.of(existente));

        MudarStatusRequest request = new MudarStatusRequest();
        request.setStatus(StatusSolicitacao.ABERTA);

        try {
            solicitacaoService.mudarStatus("1", request);
            fail();
        } catch (ValidacaoException ex) {
            assertEquals("Não é permitido reabrir uma solicitação resolvida.", ex.getMessage());
        }
    }

    @Test
    void deveListarHistorico() {
        Solicitacao existente = new Solicitacao("cid1", "t", "d", "b", Categoria.LIXO);
        existente.setId("1");
        when(repository.findById("1")).thenReturn(Optional.of(existente));

        assertEquals(1, solicitacaoService.listarHistorico("1").size());
    }

    @Test
    void deveFalharAoBuscarIdInexistente() {
        when(repository.findById("x")).thenReturn(Optional.empty());

        try {
            solicitacaoService.buscarPorId("x");
            fail();
        } catch (EntidadeNaoEncontradaException ex) {
            assertEquals("Solicitação não encontrada.", ex.getMessage());
        }
    }
}
