package org.boligon.cidadao;

import org.boligon.exception.EntidadeNaoEncontradaException;
import org.boligon.exception.ValidacaoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadaoService {

    private final CidadaoRepository cidadaoRepository;

    public CidadaoService(CidadaoRepository cidadaoRepository) {
        this.cidadaoRepository = cidadaoRepository;
    }

    public Cidadao criar(CidadaoRequest request) {
        validarCampos(request);
        String email = normalizarEmail(request.getEmail());
        if (cidadaoRepository.existsByEmailIgnoreCase(email)) {
            throw new ValidacaoException("Já existe um cidadão com este e-mail.");
        }
        Cidadao cidadao = new Cidadao(
                request.getNome().trim(),
                email,
                normalizarTelefone(request.getTelefone())
        );
        return cidadaoRepository.save(cidadao);
    }

    public List<Cidadao> listar() {
        return cidadaoRepository.findAll();
    }

    public Cidadao buscarPorId(String id) {
        return cidadaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cidadão não encontrado."));
    }

    public Cidadao atualizar(String id, CidadaoRequest request) {
        validarCampos(request);
        Cidadao cidadao = buscarPorId(id);
        String email = normalizarEmail(request.getEmail());
        if (cidadaoRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ValidacaoException("Já existe um cidadão com este e-mail.");
        }
        cidadao.atualizar(
                request.getNome().trim(),
                email,
                normalizarTelefone(request.getTelefone())
        );
        return cidadaoRepository.save(cidadao);
    }

    public void excluir(String id) {
        Cidadao cidadao = buscarPorId(id);
        cidadaoRepository.delete(cidadao);
    }

    private void validarCampos(CidadaoRequest request) {
        if (request == null) {
            throw new ValidacaoException("Os dados do cidadão são obrigatórios.");
        }
        if (estaVazio(request.getNome())) {
            throw new ValidacaoException("O nome é obrigatório.");
        }
        if (estaVazio(request.getEmail())) {
            throw new ValidacaoException("O e-mail é obrigatório.");
        }
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }

    private String normalizarTelefone(String telefone) {
        if (telefone == null) {
            return null;
        }
        String limpo = telefone.trim();
        return limpo.isEmpty() ? null : limpo;
    }

    private boolean estaVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
