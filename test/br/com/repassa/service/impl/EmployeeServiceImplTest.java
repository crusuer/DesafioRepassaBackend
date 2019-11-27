package br.com.repassa.service.impl;

import br.com.repassa.Application;
import br.com.repassa.dto.EmployeeDTO;
import br.com.repassa.model.Employee;
import br.com.repassa.repository.EmployeeRepository;
import br.com.repassa.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeRepository mockRepository;
    @Autowired
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO = new EmployeeDTO();
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();

        employeeDTO.setName("Lorem Ipsum");
        employeeDTO.setDepartment("RH");
        employeeDTO.setUsername("xpto");
        employeeDTO.setPassword("loremipsum");
    }

    @AfterEach
    void tearDown() {
        mockRepository.deleteById(null == employee.getId()? 0 : employee.getId());
    }

    @Test
    void createValidEmployee() {
        employee = employeeService.create(employeeDTO);

        assertEquals("Lorem Ipsum", employee.getName());
        assertEquals("RH", employee.getDepartment());
        assertEquals("xpto", employee.getUsername());
        assertNotEquals("loremipsum", employee.getPassword());
        assertTrue(employee.isEnabled());
        assertEquals("ROLE_USER", employee.getUserRole());
    }

    @Test
    void createInvalidEmployee() {
        assertFalse(false);
    }

    @Test
    void save() {
        assertTrue(true);
    }

    @Test
    void findAll() {
        assertTrue(true);
    }

    @Test
    void findById() {
        assertTrue(true);
    }

    @Test
    void delete() {
        assertTrue(true);
    }
}