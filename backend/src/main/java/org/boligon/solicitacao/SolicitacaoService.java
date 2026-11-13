package org.boligon.solicitacao;

import org.boligon.cidadao.CidadaoService;
import org.boligon.exception.EntidadeNaoEncontradaException;
import org.boligon.exception.ValidacaoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final CidadaoService cidadaoService;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, CidadaoService cidadaoService) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.cidadaoService = cidadaoService;
    }

    public Solicitacao criar(SolicitacaoRequest request) {
        validarCampos(request);
        String cidadaoId = request.getCidadaoId().trim();
        cidadaoService.buscarPorId(cidadaoId);
        Solicitacao solicitacao = new Solicitacao(
                cidadaoId,
                request.getTitulo().trim(),
                request.getDescricao().trim(),
                request.getBairro().trim(),
                request.getCategoria()
        );
        return solicitacaoRepository.save(solicitacao);
    }

    public List<Solicitacao> listar() {
        return solicitacaoRepository.findAll();
    }

    public Solicitacao buscarPorId(String id) {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Solicitação não encontrada."));
    }

    public Solicitacao atualizar(String id, SolicitacaoRequest request) {
        validarCampos(request);
        if (request.getStatus() == null) {
            throw new ValidacaoException("O status é obrigatório.");
        }

        String cidadaoId = request.getCidadaoId().trim();
        cidadaoService.buscarPorId(cidadaoId);
        Solicitacao solicitacao = buscarPorId(id);
        solicitacao.atualizar(
                cidadaoId,
                request.getTitulo().trim(),
                request.getDescricao().trim(),
                request.getBairro().trim(),
                request.getCategoria(),
                request.getStatus()
        );
        return solicitacaoRepository.save(solicitacao);
    }

    public List<Solicitacao> listarPorCidadaoId(String cidadaoId) {
        cidadaoService.buscarPorId(cidadaoId);
        return solicitacaoRepository.findByCidadaoId(cidadaoId);
    }

    public void excluir(String id) {
        Solicitacao solicitacao = buscarPorId(id);
        solicitacaoRepository.delete(solicitacao);
    }

    public void validarCampos(SolicitacaoRequest request) {
        if (request == null) {
            throw new ValidacaoException("Os dados da solicitação são obrigatórios.");
        }
        if (estaVazio(request.getTitulo())) {
            throw new ValidacaoException("O título é obrigatório.");
        }
        if (estaVazio(request.getDescricao())) {
            throw new ValidacaoException("A descrição é obrigatória.");
        }
        if (estaVazio(request.getBairro())) {
            throw new ValidacaoException("O bairro é obrigatório.");
        }
        if (request.getCategoria() == null) {
            throw new ValidacaoException("A categoria é obrigatória.");
        }
        if (estaVazio(request.getCidadaoId())) {
            throw new ValidacaoException("O cidadão é obrigatório.");
        }
    }

    private boolean estaVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
