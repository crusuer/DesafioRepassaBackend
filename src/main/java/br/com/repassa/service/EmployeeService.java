package br.com.repassa.service;

import br.com.repassa.model.Employee;

import java.util.Optional;

public interface EmployeeService {
    Employee create(Employee employee);

    Employee save(Employee employee, Long id);

    Iterable<Employee> findAll();

    Optional<Employee> findById(Long id);

    void delete(Long id);
}
