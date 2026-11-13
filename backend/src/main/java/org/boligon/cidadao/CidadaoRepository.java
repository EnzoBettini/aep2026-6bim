package org.boligon.cidadao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CidadaoRepository extends MongoRepository<Cidadao, String> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, String id);
}
