package org.boligon.solicitacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolicitacaoTest {

    @Test
    void deveDefinirId() {

        Solicitacao solicitacao = new Solicitacao();

        solicitacao.setId("oi");

        assertEquals("oi", solicitacao.getId());
    }

    @Test
    void deveCriarSolicitacaoAberta() {
        Solicitacao solicitacao = new Solicitacao(
                "cid1", "Titulo", "Descrição", "Bairro", Categoria.ILUMINACAO);

        assertEquals("Titulo", solicitacao.getTitulo());
        assertEquals("Descrição", solicitacao.getDescricao());
        assertEquals("Bairro", solicitacao.getBairro());
        assertEquals(Categoria.ILUMINACAO, solicitacao.getCategoria());
        assertEquals(StatusSolicitacao.ABERTA, solicitacao.getStatus());
        assertEquals(1, solicitacao.getHistoricoStatus().size());
        assertEquals(StatusSolicitacao.ABERTA, solicitacao.getHistoricoStatus().get(0).getStatus());
        assertEquals("Solicitação criada", solicitacao.getHistoricoStatus().get(0).getObservacao());
    }

    @Test
    void deveAtualizarSolicitacao() {
        Solicitacao solicitacao = new
                Solicitacao("cid1", "Titulo", "Descrição", "Bairro", Categoria.ILUMINACAO);

        solicitacao.atualizar("cid2", "Novo titulo", "Nova descrição",
                "Novo bairro", Categoria.BURACO, StatusSolicitacao.RESOLVIDA);

        assertEquals("cid2", solicitacao.getCidadaoId());
        assertEquals("Novo titulo", solicitacao.getTitulo());
        assertEquals("Nova descrição", solicitacao.getDescricao());
        assertEquals("Novo bairro", solicitacao.getBairro());
        assertEquals(Categoria.BURACO, solicitacao.getCategoria());
        assertEquals(StatusSolicitacao.RESOLVIDA, solicitacao.getStatus());
        assertEquals(2, solicitacao.getHistoricoStatus().size());
    }

    @Test
    void deveRegistrarHistoricoAoMudarStatus() {
        Solicitacao solicitacao = new Solicitacao("cid1", "t", "d", "b", Categoria.ILUMINACAO);

        solicitacao.mudarStatus(StatusSolicitacao.EM_ANDAMENTO, "Equipe enviada");

        assertEquals(StatusSolicitacao.EM_ANDAMENTO, solicitacao.getStatus());
        assertEquals(2, solicitacao.getHistoricoStatus().size());
        assertEquals("Equipe enviada", solicitacao.getHistoricoStatus().get(1).getObservacao());
    }

}
