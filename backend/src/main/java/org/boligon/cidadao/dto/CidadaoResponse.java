package org.boligon.cidadao.dto;

import org.boligon.cidadao.domain.Cidadao;

import java.util.List;

public class CidadaoResponse {

    private String id;
    private String nome;
    private String email;
    private String telefone;

    public static CidadaoResponse de(Cidadao cidadao) {
        CidadaoResponse response = new CidadaoResponse();
        response.id = cidadao.getId();
        response.nome = cidadao.getNome();
        response.email = cidadao.getEmail();
        response.telefone = cidadao.getTelefone();
        return response;
    }

    public static List<CidadaoResponse> deLista(List<Cidadao> cidadaos) {
        return cidadaos.stream().map(CidadaoResponse::de).toList();
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}
