package br.com.repassa.service;

import br.com.repassa.dto.AssignDTO;
import br.com.repassa.model.Evaluation;

import java.util.Optional;

public interface EvaluationService {
    Evaluation create(Evaluation evaluation);

    Evaluation save(Evaluation evaluation, Long id);

    Iterable<Evaluation> findAll();

    Optional<Evaluation> findById(Long id);

    void delete(Long id);

    Evaluation assign(AssignDTO assignDTO);
}
