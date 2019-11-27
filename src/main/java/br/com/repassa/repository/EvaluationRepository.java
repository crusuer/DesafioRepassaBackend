package br.com.repassa.repository;

import br.com.repassa.model.Evaluation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {

    @Query("SELECT e FROM Evaluation e JOIN e.rater m WHERE m.username = :username ")
    Iterable<Evaluation> findAssigned(String username);
}

