package br.com.repassa.repository;

import br.com.repassa.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.userRole <> 'ROLE_ADMIN' ")
    Iterable<Employee> findAllUsers();
}
