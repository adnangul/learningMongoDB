package com.concretepage.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import com.concretepage.mongodb.entity.Employee;

@Component
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	@Query("{ 'name' : ?0 }")
    Employee getEmployeeByName(String name);
    
	@Query(value="{ 'age' : ?0}", fields="{ 'name' : 1, 'id' : 1}")
    List<Employee> getEmployeeByAge(int age);
}
