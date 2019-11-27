package br.com.repassa.repository;

import br.com.repassa.model.Employee;
import br.com.repassa.model.Evaluation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {
    Optional<Evaluation> findByRaterAndRated(Employee rater, Employee rated);
}

