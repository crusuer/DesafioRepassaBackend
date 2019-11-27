package br.com.repassa.controller;

import br.com.repassa.dto.AssignDTO;
import br.com.repassa.model.Employee;
import br.com.repassa.model.Evaluation;
import br.com.repassa.service.EmployeeService;
import br.com.repassa.service.EvaluationService;
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
    @GetMapping("/admin/employees")
    public Iterable<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/admin/employees/{id}")
    public Optional<Employee> finById(@PathVariable @Min(1) Long id) {
        return employeeService.findById(id);
    }

    @PostMapping("/admin/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee employeeSignUp(@Valid @RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/admin/employees/{id}")
    public Employee employeeUpdate(@RequestBody Employee employee, @PathVariable Long id) {
        return employeeService.save(employee, id);
    }

    @DeleteMapping("/admin/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
    }

    /* Endpoints to handle evaluations */
    @GetMapping("/admin/evaluations")
    public Iterable<Evaluation> findAllEvaluations() {
        return evaluationService.findAll();
    }

    @GetMapping("/admin/evaluations/{id}")
    public Optional<Evaluation> findEvaluationById(@PathVariable @Min(1) Long id) {
        return evaluationService.findById(id);
    }

    @PostMapping("/admin/evaluations")
    @ResponseStatus(HttpStatus.CREATED)
    public Evaluation evaluationSignUp(@Valid @RequestBody Evaluation evaluation) {
        return evaluationService.create(evaluation);
    }

    @PutMapping("/admin/evaluations/{id}")
    public Evaluation evaluationUpdate(@RequestBody Evaluation evaluation, @PathVariable Long id) {
        return evaluationService.save(evaluation, id);
    }

    @DeleteMapping("/admin/evaluations/{id}")
    public void deleteEvaluation(@PathVariable Long id) {
        evaluationService.delete(id);
    }

    /* Endpoint to assigne a employee to evaluate another one */
    @PostMapping("/admin/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public Evaluation assignEvaluation(@Valid @RequestBody AssignDTO assignDTO) {
        return evaluationService.assign(assignDTO);
    }
}
