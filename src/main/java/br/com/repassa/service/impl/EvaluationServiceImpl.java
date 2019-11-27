package br.com.repassa.service.impl;

import br.com.repassa.dto.AssignDTO;
import br.com.repassa.model.Employee;
import br.com.repassa.model.Evaluation;
import br.com.repassa.repository.EvaluationRepository;
import br.com.repassa.service.EmployeeService;
import br.com.repassa.service.EvaluationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    private static final Logger LOGGER = LogManager.getLogger(EvaluationServiceImpl.class);
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public Evaluation create(Evaluation evaluation) {
        Optional<Evaluation> presentEvaluation = evaluationRepository.findById(evaluation.getId());
        if (presentEvaluation.isPresent()) {
            return null;
        }
        LOGGER.info(evaluation);
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation save(Evaluation evaluation, Long id) {
        return findById(id).map(x -> {
            x.setRater(evaluation.getRater());
            x.setRated(evaluation.getRated());
            x.setCreative(evaluation.getCreative());
            x.setFocused(evaluation.getFocused());
            x.setHelpful(evaluation.getHelpful());
            x.setLeadership(evaluation.getLeadership());
            x.setResponsible(evaluation.getResponsible());
            return evaluationRepository.save(x);
        }).orElseGet(() -> {
            evaluation.setId(id);
            return evaluationRepository.save(evaluation);
        });
    }

    @Override
    public Iterable<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }

    @Override
    public Optional<Evaluation> findById(Long id) {
        return evaluationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        evaluationRepository.deleteById(id);
    }

    @Override
    public Evaluation assign(AssignDTO assignDTO) {
        Optional<Employee> rater = employeeService.findById(assignDTO.getRater());
        Optional<Employee> rated = employeeService.findById(assignDTO.getRated());

        if (rater.isPresent() && rated.isPresent()) {
            Evaluation evaluation = new Evaluation();
            evaluation.setId(0L);
            evaluation.setRater(rater.get());
            evaluation.setRated(rated.get());
            return create(evaluation);
        }
        return null;
    }
}
