package org.boligon.solicitacao.dto;

import org.boligon.solicitacao.domain.HistoricoStatus;

import java.time.Instant;
import java.util.List;

public class HistoricoStatusResponse {

    private String status;
    private Instant data;
    private String observacao;

    public static HistoricoStatusResponse de(HistoricoStatus historico) {
        HistoricoStatusResponse response = new HistoricoStatusResponse();
        response.status = historico.getStatus().name();
        response.data = historico.getData();
        response.observacao = historico.getObservacao();
        return response;
    }

    public static List<HistoricoStatusResponse> deLista(List<HistoricoStatus> historicos) {
        return historicos.stream().map(HistoricoStatusResponse::de).toList();
    }

    public String getStatus() {
        return status;
    }

    public Instant getData() {
        return data;
    }

    public String getObservacao() {
        return observacao;
    }
}
