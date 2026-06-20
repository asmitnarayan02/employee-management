package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // CREATE (single employee)
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        logger.info("Received request to add employee: {}", employee);

        Employee saved = employeeRepository.save(employee);

        logger.info("Employee created successfully with ID: {}", saved.getId());
        return ResponseEntity.status(201).body(saved);
    }

    // CREATE (bulk insert)
    @PostMapping("/bulk")
    public ResponseEntity<List<Employee>> addEmployees(@RequestBody List<Employee> employees) {
        logger.info("Received request to add {} employees", employees.size());

        List<Employee> saved = employeeRepository.saveAll(employees);

        logger.info("{} employees inserted successfully", saved.size());
        return ResponseEntity.status(201).body(saved);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Fetching all employees");

        List<Employee> employees = employeeRepository.findAll();

        logger.info("Total employees found: {}", employees.size());
        return ResponseEntity.ok(employees);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        logger.info("Fetching employee with ID: {}", id);

        return employeeRepository.findById(id)
                .map(employee -> {
                    logger.info("Employee found: {}", employee);
                    return ResponseEntity.ok(employee);
                })
                .orElseGet(() -> {
                    logger.warn("Employee not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee updatedEmployee) {

        logger.info("Updating employee with ID: {}", id);

        return employeeRepository.findById(id)
                .map(employee -> {

                    employee.setName(updatedEmployee.getName());
                    employee.setRole(updatedEmployee.getRole());
                    employee.setSalary(updatedEmployee.getSalary());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setAddress(updatedEmployee.getAddress());

                    Employee saved = employeeRepository.save(employee);

                    logger.info("Employee updated successfully with ID: {}", saved.getId());
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> {
                    logger.warn("Update failed. Employee not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {

        logger.info("Deleting employee with ID: {}", id);

        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);

            logger.info("Employee deleted successfully with ID: {}", id);
            return ResponseEntity.ok("Employee deleted successfully");
        } else {
            logger.warn("Delete failed. Employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}