package org.boligon.cidadao.repository;

import org.boligon.cidadao.domain.Cidadao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CidadaoRepository extends MongoRepository<Cidadao, String> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, String id);
}
