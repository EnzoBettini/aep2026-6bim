package org.boligon.solicitacao;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SolicitacaoRepository extends MongoRepository<Solicitacao, String> {

    List<Solicitacao> findByCidadaoId(String cidadaoId);
}
