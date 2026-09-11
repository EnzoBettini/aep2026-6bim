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
                "Titulo", "Descrição", "Bairro", Categoria.ILUMINACAO);

        assertEquals("Titulo", solicitacao.getTitulo());
        assertEquals("Descrição", solicitacao.getDescricao());
        assertEquals("Bairro", solicitacao.getBairro());
        assertEquals(Categoria.ILUMINACAO, solicitacao.getCategoria());
        assertEquals(StatusSolicitacao.ABERTA, solicitacao.getStatus());
    }

    @Test
    void deveAtualizarSolicitacao() {
        Solicitacao solicitacao = new
                Solicitacao("Titulo", "Descrição", "Bairro", Categoria.ILUMINACAO);

        solicitacao.atualizar("Novo titulo", "Nova descrição",
                "Novo bairro", Categoria.BURACO, StatusSolicitacao.RESOLVIDA);

        assertEquals("Novo titulo", solicitacao.getTitulo());
        assertEquals("Nova descrição", solicitacao.getDescricao());
        assertEquals("Novo bairro", solicitacao.getBairro());
        assertEquals(Categoria.BURACO, solicitacao.getCategoria());
        assertEquals(StatusSolicitacao.RESOLVIDA, solicitacao.getStatus());
    }

}
