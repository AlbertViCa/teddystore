package com.teddystore.service;

import com.teddystore.model.Authority;
import com.teddystore.model.Employee;
import com.teddystore.model.WebAppUser;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    <T extends WebAppUser> Employee registerEmployee(T employee);

    Optional<Employee> getEmployeeBYId(Long id);

    Optional<List<Employee>> getAllEmployees();

    void updateEmployeeDetails(Employee employee);

    void setEmployeeAuthorities(List<Authority> authorities);

    void deleteEmployeeById(Long id);

    void deleteAllEmployees();
}
