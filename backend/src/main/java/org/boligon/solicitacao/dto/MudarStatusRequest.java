package org.boligon.solicitacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.boligon.solicitacao.domain.StatusSolicitacao;

@Schema(description = "Mudança de status com observação opcional para o histórico")
public class MudarStatusRequest {

    @Schema(example = "EM_ANDAMENTO", allowableValues = {"ABERTA", "EM_ANDAMENTO", "RESOLVIDA"})
    private StatusSolicitacao status;

    @Schema(example = "Equipe a caminho para vistoria no local")
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
