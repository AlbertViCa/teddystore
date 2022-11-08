package com.teddystore.service;

import com.teddystore.model.Authority;
import com.teddystore.model.Employee;
import com.teddystore.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImp implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder encoder;

    public EmployeeServiceImp(EmployeeRepository employeeRepository, PasswordEncoder encoder) {
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
    }

    @Override
    public void registerEmployee(Employee employee) {
        employee.setPassword(encoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
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
