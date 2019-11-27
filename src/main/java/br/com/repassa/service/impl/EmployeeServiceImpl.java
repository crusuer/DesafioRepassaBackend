package br.com.repassa.service.impl;

import br.com.repassa.model.Employee;
import br.com.repassa.repository.EmployeeRepository;
import br.com.repassa.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LogManager.getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        Optional<Employee> presentEmployee = employeeRepository.findById(employee.getId());
        if (presentEmployee.isPresent()) {
            return null;
        }
        LOGGER.info(employee);
        employee.setUserRole("USER_ROLE");
        employee.setEnabled(true);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee save(Employee employee, Long id) {
        return findById(id).map(x -> {
            x.setName(employee.getName());
            x.setDepartment(employee.getDepartment());
            x.setUsername(employee.getUsername());
            x.setPassword(employee.getPassword());
            x.setUserRole("USER_ROLE");
            x.setEnabled(true);
            return employeeRepository.save(x);
        }).orElseGet(() -> {
            employee.setId(id);
            return employeeRepository.save(employee);
        });
    }

    @Override
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}