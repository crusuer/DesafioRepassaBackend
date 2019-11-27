package br.com.repassa.controller;

import br.com.repassa.dto.AssignDTO;
import br.com.repassa.dto.EmployeeDTO;
import br.com.repassa.model.Employee;
import br.com.repassa.model.Evaluation;
import br.com.repassa.service.EmployeeService;
import br.com.repassa.service.EvaluationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@Validated
public class AdminController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EvaluationService evaluationService;

    /* Endpoints to handle employees */
    @ApiOperation(value = "Visualizar todos os funcionários cadastrados.")
    @GetMapping("/admin/employees")
    public Iterable<Employee> findAll() {
        return employeeService.findAll();
    }

    @ApiOperation(value = "Visualizar um funcionário pelo seu identificador.")
    @GetMapping("/admin/employees/{id}")
    public Optional<Employee> finById(@PathVariable @Min(1) Long id) {
        return employeeService.findById(id);
    }

    @ApiOperation(value = "Cadastrar um novo funcionário.")
    @PostMapping("/admin/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee employeeSignUp(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO);
    }

    @ApiOperation(value = "Atualizar um funcionário cadastrado.")
    @PutMapping("/admin/employees/{id}")
    public Employee employeeUpdate(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long id) {
        return employeeService.save(employeeDTO, id);
    }

    @ApiOperation(value = "Excluir um funcionário cadastrado.")
    @DeleteMapping("/admin/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
    }

    /* Endpoints to handle evaluations */
    @ApiOperation(value = "Visualizar todas as avaliações cadastradas.")
    @GetMapping("/admin/evaluations")
    public Iterable<Evaluation> findAllEvaluations() {
        return evaluationService.findAll();
    }

    @ApiOperation(value = "Visualizar uma avaliação pelo seu identificador.")
    @GetMapping("/admin/evaluations/{id}")
    public Optional<Evaluation> findEvaluationById(@PathVariable @Min(1) Long id) {
        return evaluationService.findById(id);
    }

    @ApiOperation(value = "Cadastrar uma nova avaliação.")
    @PostMapping("/admin/evaluations")
    @ResponseStatus(HttpStatus.CREATED)
    public Evaluation evaluationSignUp(@Valid @RequestBody Evaluation evaluation) {
        return evaluationService.create(evaluation);
    }

    @ApiOperation(value = "Editar uma avaliação cadastrada.")
    @PutMapping("/admin/evaluations/{id}")
    public Evaluation evaluationUpdate(@RequestBody Evaluation evaluation, @PathVariable Long id) {
        return evaluationService.save(evaluation, id);
    }

    @ApiOperation(value = "Excluir uma avaliação cadastrada.")
    @DeleteMapping("/admin/evaluations/{id}")
    public void deleteEvaluation(@PathVariable Long id) {
        evaluationService.delete(id);
    }

    /* Endpoint to assigne a employee to evaluate another one */
    @ApiOperation(value = "Atribuir um funcionário a avaliar outro.")
    @PostMapping("/admin/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public Evaluation assignEvaluation(@Valid @RequestBody AssignDTO assignDTO) {
        return evaluationService.assign(assignDTO);
    }
}
