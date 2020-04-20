package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        Employee oldEmployee = employeeRepository.findById(id).orElse(null);
        if (oldEmployee != null) {
            oldEmployee.updateEmployeeData(employee);
        }
        return oldEmployee;
    }

    public List<Employee> deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
        return employeeRepository.findAll();
    }

    public List<Employee> returnSpecificNumberOfEmployees(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

}
