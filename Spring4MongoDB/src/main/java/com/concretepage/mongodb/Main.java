package com.concretepage.mongodb;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.concretepage.mongodb.config.MongoDBConfig;
import com.concretepage.mongodb.entity.Employee;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MongoDBConfig.class);
        ctx.refresh();
        EmployeeRepository employeeRepository = ctx.getBean(EmployeeRepository.class);
        Employee adnan = new Employee(1, "Adnan", 40);
        Employee jim = new Employee(2, "Jim", 45);

        //Delete if exists already
        employeeRepository.deleteAll();

        //Save employee(s)
        employeeRepository.save(adnan);
        employeeRepository.save(jim);

        //Get employee By Name
        Employee emp = employeeRepository.getEmployeeByName(jim.name);
        System.out.println(emp.name);

        //Fetch all employee for the age
        List<Employee> employees = employeeRepository.getEmployeeByAge(40);
        System.out.println("----employee for the age 40----");
        for (Employee employee : employees) {
            System.out.println("Id:" + employee.id + ",Name:" + employee.name);
        }
    }
}
