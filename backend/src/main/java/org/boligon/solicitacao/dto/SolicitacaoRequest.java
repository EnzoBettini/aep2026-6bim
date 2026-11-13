package org.boligon.solicitacao.dto;

import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.StatusSolicitacao;

public class SolicitacaoRequest {

    private String cidadaoId;
    private String titulo;
    private String descricao;
    private String bairro;
    private Categoria categoria;
    private StatusSolicitacao status;

    public String getCidadaoId() {
        return cidadaoId;
    }

    public void setCidadaoId(String cidadaoId) {
        this.cidadaoId = cidadaoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }
}
