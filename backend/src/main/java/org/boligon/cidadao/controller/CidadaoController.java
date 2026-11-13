package org.boligon.cidadao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.boligon.cidadao.dto.CidadaoRequest;
import org.boligon.cidadao.dto.CidadaoResponse;
import org.boligon.cidadao.service.CidadaoService;
import org.boligon.solicitacao.dto.SolicitacaoResponse;
import org.boligon.solicitacao.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cidadaos")
@Tag(name = "Cidadãos", description = "CRUD de cidadãos e listagem de solicitações por cidadão")
public class CidadaoController {

    private final CidadaoService cidadaoService;
    private final SolicitacaoService solicitacaoService;

    public CidadaoController(CidadaoService cidadaoService, SolicitacaoService solicitacaoService) {
        this.cidadaoService = cidadaoService;
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar cidadão", description = "Cria um cidadão; e-mail deve ser único.")
    @ApiResponse(responseCode = "201", description = "Cidadão criado")
    @ApiResponse(responseCode = "400", description = "Validação (campos obrigatórios ou e-mail duplicado)")
    public CidadaoResponse criar(@RequestBody CidadaoRequest request) {
        return CidadaoResponse.de(cidadaoService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar cidadãos")
    public List<CidadaoResponse> listar() {
        return CidadaoResponse.deLista(cidadaoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cidadão por ID")
    @ApiResponse(responseCode = "404", description = "Cidadão não encontrado")
    public CidadaoResponse buscar(@PathVariable String id) {
        return CidadaoResponse.de(cidadaoService.buscarPorId(id));
    }

    @GetMapping("/{id}/solicitacoes")
    @Operation(summary = "Listar solicitações do cidadão", description = "Todas as solicitações com o mesmo cidadaoId.")
    @ApiResponse(responseCode = "404", description = "Cidadão não encontrado")
    public List<SolicitacaoResponse> listarSolicitacoes(@PathVariable String id) {
        return SolicitacaoResponse.deLista(solicitacaoService.listarPorCidadaoId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cidadão")
    @ApiResponse(responseCode = "404", description = "Cidadão não encontrado")
    public CidadaoResponse atualizar(@PathVariable String id, @RequestBody CidadaoRequest request) {
        return CidadaoResponse.de(cidadaoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir cidadão")
    @ApiResponse(responseCode = "204", description = "Excluído")
    @ApiResponse(responseCode = "404", description = "Cidadão não encontrado")
    public void excluir(@PathVariable String id) {
        cidadaoService.excluir(id);
    }
}
