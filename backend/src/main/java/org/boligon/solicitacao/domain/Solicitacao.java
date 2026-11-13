package org.boligon.solicitacao.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document(collection = "solicitacoes")
public class Solicitacao {

    @Id
    private String id;
    private String cidadaoId;
    private String titulo;
    private String descricao;
    private String bairro;
    private Categoria categoria;
    private StatusSolicitacao status;
    private List<HistoricoStatus> historicoStatus = new ArrayList<>();

    public Solicitacao() {
    }

    public Solicitacao(String cidadaoId, String titulo, String descricao, String bairro, Categoria categoria) {
        this.cidadaoId = cidadaoId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.bairro = bairro;
        this.categoria = categoria;
        this.status = StatusSolicitacao.ABERTA;
        registrarHistorico(StatusSolicitacao.ABERTA, "Solicitação criada");
    }

    public void atualizar(String cidadaoId, String titulo, String descricao, String bairro, Categoria categoria,
                          StatusSolicitacao status) {
        this.cidadaoId = cidadaoId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.bairro = bairro;
        this.categoria = categoria;
        if (this.status != status) {
            mudarStatus(status, "Status atualizado");
        } else {
            this.status = status;
        }
    }

    public void mudarStatus(StatusSolicitacao novoStatus, String observacao) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("Status inválido.");
        }
        this.status = novoStatus;
        registrarHistorico(novoStatus, observacao);
    }

    private void registrarHistorico(StatusSolicitacao statusHistorico, String observacao) {
        if (historicoStatus == null) {
            historicoStatus = new ArrayList<>();
        }
        String texto = observacao == null ? "" : observacao.trim();
        historicoStatus.add(new HistoricoStatus(statusHistorico, Instant.now(), texto));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public List<HistoricoStatus> getHistoricoStatus() {
        if (historicoStatus == null) {
            return List.of();
        }
        return Collections.unmodifiableList(historicoStatus);
    }
}
