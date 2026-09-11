package org.boligon.solicitacao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "solicitacoes")
public class Solicitacao {

    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String bairro;
    private Categoria categoria;
    private StatusSolicitacao status;

    public Solicitacao() {
    }

    public Solicitacao(String titulo, String descricao, String bairro, Categoria categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.bairro = bairro;
        this.categoria = categoria;
        this.status = StatusSolicitacao.ABERTA;
    }

    public void atualizar(String titulo, String descricao, String bairro, Categoria categoria, StatusSolicitacao status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.bairro = bairro;
        this.categoria = categoria;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
