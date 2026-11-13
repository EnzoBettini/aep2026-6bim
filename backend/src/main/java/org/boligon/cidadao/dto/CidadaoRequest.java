package org.boligon.cidadao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criar ou atualizar um cidadão")
public class CidadaoRequest {

    @Schema(example = "Maria Silva")
    private String nome;

    @Schema(example = "maria.silva@email.com")
    private String email;

    @Schema(example = "41999887766")
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
