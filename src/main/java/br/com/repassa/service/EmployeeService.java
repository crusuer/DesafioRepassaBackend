package br.com.repassa.service;

import br.com.repassa.dto.EmployeeDTO;
import br.com.repassa.model.Employee;

import java.util.Optional;

public interface EmployeeService {
    Employee create(EmployeeDTO employeeDTO);

    Employee save(EmployeeDTO employeeDTO, Long id);

    Iterable<Employee> findAll();

    Optional<Employee> findById(Long id);

    void delete(Long id);
}
