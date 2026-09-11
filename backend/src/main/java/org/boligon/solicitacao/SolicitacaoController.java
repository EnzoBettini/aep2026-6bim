package org.boligon.solicitacao;

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
    public Solicitacao criar(@RequestBody SolicitacaoRequest request) {
        return solicitacaoService.criar(request);
    }

    @GetMapping
    public List<Solicitacao> listar() {
        return solicitacaoService.listar();
    }

    @GetMapping("/{id}")
    public Solicitacao buscar(@PathVariable String id) {
        return solicitacaoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Solicitacao atualizar(@PathVariable String id, @RequestBody SolicitacaoRequest request) {
        return solicitacaoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable String id) {
        solicitacaoService.excluir(id);
    }
}
