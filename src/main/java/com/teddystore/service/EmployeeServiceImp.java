package com.teddystore.service;

import com.teddystore.model.Authority;
import com.teddystore.model.Employee;
import com.teddystore.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void registerEmployee(Employee employee) {

    }

    @Override
    public Optional<Employee> getEmployeeBYId(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Employee>> getAllEmployees() {
        return Optional.empty();
    }

    @Override
    public void updateEmployeeDetails(Employee employee) {

    }

    @Override
    public void setEmployeeAuthorities(List<Authority> authorities) {

    }

    @Override
    public void deleteEmployeeById(Long id) {

    }

    @Override
    public void deleteAllEmployees() {

    }
}
