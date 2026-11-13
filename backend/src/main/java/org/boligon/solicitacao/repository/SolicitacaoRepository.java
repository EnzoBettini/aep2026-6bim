package org.boligon.solicitacao.repository;

import org.boligon.solicitacao.domain.Solicitacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SolicitacaoRepository extends MongoRepository<Solicitacao, String> {

    List<Solicitacao> findByCidadaoId(String cidadaoId);
}
