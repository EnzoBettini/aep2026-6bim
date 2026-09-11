package org.boligon.solicitacao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SolicitacaoRepository extends MongoRepository<Solicitacao, String> {
}
