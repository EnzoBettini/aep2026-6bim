package org.boligon.solicitacao;

import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.StatusSolicitacao;
import org.boligon.solicitacao.dto.SolicitacaoRequest;
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
    }
}
