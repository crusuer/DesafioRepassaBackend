package br.com.repassa.service.impl;

import br.com.repassa.dto.EmployeeDTO;
import br.com.repassa.model.Employee;
import br.com.repassa.repository.EmployeeRepository;
import br.com.repassa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Employee create(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(0L);
        employee.setName(employeeDTO.getName());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setUserRole("ROLE_USER");
        employee.setEnabled(true);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee save(EmployeeDTO employeeDTO, Long id) {
        return findById(id).map(x -> {
            x.setName(employeeDTO.getName());
            x.setDepartment(employeeDTO.getDepartment());
            x.setUsername(employeeDTO.getUsername());
            x.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
            x.setUserRole("USER_ROLE");
            x.setEnabled(true);
            return employeeRepository.save(x);
        }).orElseGet(() -> {
            Employee employee = new Employee();
            employee.setId(id);
            return employeeRepository.save(employee);
        });
    }

    @Override
    public Iterable<Employee> findAll() {
        return employeeRepository.findAllUsers();
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