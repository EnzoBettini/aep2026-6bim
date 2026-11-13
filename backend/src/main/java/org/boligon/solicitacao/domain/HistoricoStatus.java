package org.boligon.solicitacao.domain;

import java.time.Instant;

public class HistoricoStatus {

    private StatusSolicitacao status;
    private Instant data;
    private String observacao;

    public HistoricoStatus() {
    }

    public HistoricoStatus(StatusSolicitacao status, Instant data, String observacao) {
        this.status = status;
        this.data = data;
        this.observacao = observacao;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public Instant getData() {
        return data;
    }

    public String getObservacao() {
        return observacao;
    }
}
