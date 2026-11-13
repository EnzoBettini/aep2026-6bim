package org.boligon.solicitacao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolicitacaoRequestTest {

    @Test
    void deveDefinirCampos() {
        SolicitacaoRequest request = new SolicitacaoRequest();
        request.setCidadaoId("cid1");
        request.setTitulo("Titulo");
        request.setDescricao("Descricao");
        request.setBairro("Bairro");
        request.setCategoria(Categoria.ILUMINACAO);
        request.setStatus(StatusSolicitacao.ABERTA);

        assertEquals("cid1", request.getCidadaoId());
        assertEquals("Titulo", request.getTitulo());
        assertEquals("Descricao", request.getDescricao());
        assertEquals("Bairro", request.getBairro());
        assertEquals(Categoria.ILUMINACAO, request.getCategoria());
        assertEquals(StatusSolicitacao.ABERTA, request.getStatus());
    }
}
