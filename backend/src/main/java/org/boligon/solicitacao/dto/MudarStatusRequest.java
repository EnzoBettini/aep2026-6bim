package org.boligon.solicitacao.dto;

import org.boligon.solicitacao.domain.StatusSolicitacao;

public class MudarStatusRequest {

    private StatusSolicitacao status;
    private String observacao;

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
