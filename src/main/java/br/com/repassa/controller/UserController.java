package br.com.repassa.controller;

import br.com.repassa.dto.EvaluationDTO;
import br.com.repassa.model.Evaluation;
import br.com.repassa.service.EvaluationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Validated
public class UserController {
    @Autowired
    private EvaluationService evaluationService;

    @ApiOperation(value = "Visualizar todas as avaliações atrubuídas ao funcionário.")
    @GetMapping("/user/evaluations")
    public Iterable<Evaluation> assignedEvaluations(Principal principal) {
        return evaluationService.findAssigned(principal.getName());
    }

    @ApiOperation(value = "Enviar feedback de uma avaliação atribuída ao funcionário.")
    @PutMapping("/user/evaluations/{id}")
    public Evaluation sendFeedback(@RequestBody EvaluationDTO evaluationDTO, @PathVariable Long id) {
        return evaluationService.sendFeedback(evaluationDTO, id);
    }
}
