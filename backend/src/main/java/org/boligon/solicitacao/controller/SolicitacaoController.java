package org.boligon.solicitacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Solicitações", description = "CRUD, mudança de status e histórico de solicitações urbanas")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar solicitação", description = "Exige cidadaoId válido; status inicial ABERTA com histórico.")
    @ApiResponse(responseCode = "201", description = "Solicitação criada")
    public SolicitacaoResponse criar(@RequestBody SolicitacaoRequest request) {
        return SolicitacaoResponse.de(solicitacaoService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar solicitações")
    public List<SolicitacaoResponse> listar() {
        return SolicitacaoResponse.deLista(solicitacaoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar solicitação por ID")
    @ApiResponse(responseCode = "404", description = "Solicitação não encontrada")
    public SolicitacaoResponse buscar(@PathVariable String id) {
        return SolicitacaoResponse.de(solicitacaoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar solicitação", description = "Substitui dados e pode alterar o status (com regra de histórico).")
    public SolicitacaoResponse atualizar(@PathVariable String id, @RequestBody SolicitacaoRequest request) {
        return SolicitacaoResponse.de(solicitacaoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Mudar status",
            description = "Registra observação no histórico. Não permite reabrir RESOLVIDA para ABERTA."
    )
    @ApiResponse(responseCode = "400", description = "Transição inválida ou status igual ao atual")
    public SolicitacaoResponse mudarStatus(@PathVariable String id, @RequestBody MudarStatusRequest request) {
        return SolicitacaoResponse.de(solicitacaoService.mudarStatus(id, request));
    }

    @GetMapping("/{id}/historico")
    @Operation(summary = "Listar histórico de status", description = "Ordem cronológica do embarcado historicoStatus.")
    public List<HistoricoStatusResponse> listarHistorico(@PathVariable String id) {
        return HistoricoStatusResponse.deLista(solicitacaoService.listarHistorico(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir solicitação")
    public void excluir(@PathVariable String id) {
        solicitacaoService.excluir(id);
    }
}
