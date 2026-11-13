package org.boligon.solicitacao.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.boligon.solicitacao.dto.HistoricoStatusResponse;
import org.boligon.solicitacao.dto.MudarStatusRequest;
import org.boligon.solicitacao.dto.SolicitacaoRequest;
import org.boligon.solicitacao.dto.SolicitacaoResponse;
import org.boligon.solicitacao.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
@Tag(name = "Solicitações", description = "CRUD de solicitações urbanas")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitacaoResponse criar(@RequestBody SolicitacaoRequest request) {
        return SolicitacaoResponse.de(solicitacaoService.criar(request));
    }

    @GetMapping
    public List<SolicitacaoResponse> listar() {
        return SolicitacaoResponse.deLista(solicitacaoService.listar());
    }

    @GetMapping("/{id}")
    public SolicitacaoResponse buscar(@PathVariable String id) {
        return SolicitacaoResponse.de(solicitacaoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public SolicitacaoResponse atualizar(@PathVariable String id, @RequestBody SolicitacaoRequest request) {
        return SolicitacaoResponse.de(solicitacaoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public SolicitacaoResponse mudarStatus(@PathVariable String id, @RequestBody MudarStatusRequest request) {
        return SolicitacaoResponse.de(solicitacaoService.mudarStatus(id, request));
    }

    @GetMapping("/{id}/historico")
    public List<HistoricoStatusResponse> listarHistorico(@PathVariable String id) {
        return HistoricoStatusResponse.deLista(solicitacaoService.listarHistorico(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable String id) {
        solicitacaoService.excluir(id);
    }
}
