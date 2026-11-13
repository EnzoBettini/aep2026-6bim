package org.boligon.solicitacao;

import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.Solicitacao;
import org.boligon.solicitacao.domain.StatusSolicitacao;
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
        assertEquals(StatusSolicitacao.ABERTA, solicitacao.getStatus());
        assertEquals(1, solicitacao.getHistoricoStatus().size());
    }

    @Test
    void deveAtualizarSolicitacao() {
        Solicitacao solicitacao = new Solicitacao("cid1", "Titulo", "Descrição", "Bairro", Categoria.ILUMINACAO);

        solicitacao.atualizar("cid2", "Novo titulo", "Nova descrição",
                "Novo bairro", Categoria.BURACO, StatusSolicitacao.RESOLVIDA);

        assertEquals("cid2", solicitacao.getCidadaoId());
        assertEquals(StatusSolicitacao.RESOLVIDA, solicitacao.getStatus());
        assertEquals(2, solicitacao.getHistoricoStatus().size());
    }

    @Test
    void deveRegistrarHistoricoAoMudarStatus() {
        Solicitacao solicitacao = new Solicitacao("cid1", "t", "d", "b", Categoria.ILUMINACAO);

        solicitacao.mudarStatus(StatusSolicitacao.EM_ANDAMENTO, "Equipe enviada");

        assertEquals(StatusSolicitacao.EM_ANDAMENTO, solicitacao.getStatus());
        assertEquals(2, solicitacao.getHistoricoStatus().size());
    }
}
