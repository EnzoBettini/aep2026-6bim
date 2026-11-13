package org.boligon.cidadao;

import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.boligon.solicitacao.Solicitacao;
import org.boligon.solicitacao.SolicitacaoService;

import java.util.List;

@RestController
@RequestMapping("/api/cidadaos")
@Tag(name = "Cidadãos", description = "CRUD de cidadãos")
public class CidadaoController {

    private final CidadaoService cidadaoService;
    private final SolicitacaoService solicitacaoService;

    public CidadaoController(CidadaoService cidadaoService, SolicitacaoService solicitacaoService) {
        this.cidadaoService = cidadaoService;
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidadao criar(@RequestBody CidadaoRequest request) {
        return cidadaoService.criar(request);
    }

    @GetMapping
    public List<Cidadao> listar() {
        return cidadaoService.listar();
    }

    @GetMapping("/{id}")
    public Cidadao buscar(@PathVariable String id) {
        return cidadaoService.buscarPorId(id);
    }

    @GetMapping("/{id}/solicitacoes")
    public List<Solicitacao> listarSolicitacoes(@PathVariable String id) {
        return solicitacaoService.listarPorCidadaoId(id);
    }

    @PutMapping("/{id}")
    public Cidadao atualizar(@PathVariable String id, @RequestBody CidadaoRequest request) {
        return cidadaoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable String id) {
        cidadaoService.excluir(id);
    }
}
