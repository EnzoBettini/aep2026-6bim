package org.boligon.solicitacao.dto;

import org.boligon.solicitacao.domain.Solicitacao;

import java.util.List;

public class SolicitacaoResponse {

    private String id;
    private String cidadaoId;
    private String titulo;
    private String descricao;
    private String bairro;
    private String categoria;
    private String status;
    private List<HistoricoStatusResponse> historicoStatus;

    public static SolicitacaoResponse de(Solicitacao solicitacao) {
        SolicitacaoResponse response = new SolicitacaoResponse();
        response.id = solicitacao.getId();
        response.cidadaoId = solicitacao.getCidadaoId();
        response.titulo = solicitacao.getTitulo();
        response.descricao = solicitacao.getDescricao();
        response.bairro = solicitacao.getBairro();
        response.categoria = solicitacao.getCategoria().name();
        response.status = solicitacao.getStatus().name();
        response.historicoStatus = HistoricoStatusResponse.deLista(solicitacao.getHistoricoStatus());
        return response;
    }

    public static List<SolicitacaoResponse> deLista(List<Solicitacao> solicitacoes) {
        return solicitacoes.stream().map(SolicitacaoResponse::de).toList();
    }

    public String getId() {
        return id;
    }

    public String getCidadaoId() {
        return cidadaoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getStatus() {
        return status;
    }

    public List<HistoricoStatusResponse> getHistoricoStatus() {
        return historicoStatus;
    }
}
