package org.boligon.solicitacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.boligon.solicitacao.domain.Categoria;
import org.boligon.solicitacao.domain.StatusSolicitacao;

@Schema(description = "Dados para criar ou atualizar uma solicitação urbana")
public class SolicitacaoRequest {

    @Schema(example = "671a12bc34d56e7890abcdef", description = "ID do cidadão já cadastrado")
    private String cidadaoId;

    @Schema(example = "Poste apagado na Rua A")
    private String titulo;

    @Schema(example = "Sem iluminação há três noites.")
    private String descricao;

    @Schema(example = "Centro")
    private String bairro;

    @Schema(example = "ILUMINACAO", allowableValues = {"ILUMINACAO", "BURACO", "LIXO", "PODA"})
    private Categoria categoria;

    @Schema(
            description = "Obrigatório na atualização (PUT); ignorado na criação (inicia em ABERTA)",
            example = "EM_ANDAMENTO",
            allowableValues = {"ABERTA", "EM_ANDAMENTO", "RESOLVIDA"}
    )
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
