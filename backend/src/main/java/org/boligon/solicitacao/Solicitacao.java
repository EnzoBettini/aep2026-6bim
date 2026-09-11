package org.boligon.solicitacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitacoes")
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false, length = 80)
    private String bairro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
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

    public Long getId() {
        return id;
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
