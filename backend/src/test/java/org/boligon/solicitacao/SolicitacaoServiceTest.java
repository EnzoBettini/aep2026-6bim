package org.boligon.solicitacao;

import org.boligon.exception.EntidadeNaoEncontradaException;
import org.boligon.exception.ValidacaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    SolicitacaoService solicitacaoService;

    @BeforeEach
    void setUp() {
        solicitacaoService = new SolicitacaoService(repository);
    }

    @Test
    void deveCriarSolicitacao() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setTitulo("Titulo");
        request.setDescricao("Descricao");
        request.setBairro("Bairro");
        request.setCategoria(Categoria.ILUMINACAO);

        Solicitacao salva = new Solicitacao("Titulo", "Descricao", "Bairro", Categoria.ILUMINACAO);
        when(repository.save(any(Solicitacao.class))).thenReturn(salva);

        Solicitacao entidade = solicitacaoService.criar(request);

        assertEquals("Titulo", entidade.getTitulo());
        assertEquals("Descricao", entidade.getDescricao());
        assertEquals("Bairro", entidade.getBairro());
        assertEquals(Categoria.ILUMINACAO, entidade.getCategoria());
        assertEquals(StatusSolicitacao.ABERTA, entidade.getStatus());
    }

    @Test
    void deveRecusarTituloVazio() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setTitulo(" ");
        request.setDescricao("Descricao");
        request.setBairro("Bairro");
        request.setCategoria(Categoria.ILUMINACAO);

        try {
            solicitacaoService.criar(request);
            fail();
        } catch (ValidacaoException ex) {
            assertEquals("O título é obrigatório.", ex.getMessage());
        }
    }

    @Test
    void deveListarSolicitacoes() {
        Solicitacao s = new Solicitacao("t", "d", "b", Categoria.LIXO);
        when(repository.findAll()).thenReturn(List.of(s));

        List<Solicitacao> lista = solicitacaoService.listar();

        assertEquals(1, lista.size());
        assertEquals("t", lista.get(0).getTitulo());
    }

    @Test
    void deveBuscarPorId() {
        Solicitacao s = new Solicitacao("t", "d", "b", Categoria.PODA);
        s.setId("1");
        when(repository.findById("1")).thenReturn(Optional.of(s));

        Solicitacao encontrada = solicitacaoService.buscarPorId("1");

        assertEquals("1", encontrada.getId());
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

    @Test
    void deveAtualizarSolicitacao() {
        Solicitacao existente = new Solicitacao("antigo", "d", "b", Categoria.BURACO);
        existente.setId("1");
        when(repository.findById("1")).thenReturn(Optional.of(existente));
        when(repository.save(any(Solicitacao.class))).thenReturn(existente);

        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setTitulo("Novo");
        request.setDescricao("Descricao");
        request.setBairro("Bairro");
        request.setCategoria(Categoria.ILUMINACAO);
        request.setStatus(StatusSolicitacao.EM_ANDAMENTO);

        Solicitacao atualizada = solicitacaoService.atualizar("1", request);

        assertEquals("Novo", atualizada.getTitulo());
        assertEquals(StatusSolicitacao.EM_ANDAMENTO, atualizada.getStatus());
    }

    @Test
    void deveExcluirSolicitacao() {
        Solicitacao existente = new Solicitacao("t", "d", "b", Categoria.LIXO);
        existente.setId("9");
        when(repository.findById("9")).thenReturn(Optional.of(existente));

        solicitacaoService.excluir("9");

        verify(repository).delete(existente);
    }
}
