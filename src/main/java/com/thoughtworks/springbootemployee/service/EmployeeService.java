package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.getAllEmployee();
    }

    public List<Employee> createNewEmployee(Employee employee){
        return employeeRepository.createNewEmployee(employee);
    }

    public List<Employee> findEmployeeByID(Integer employeeID){
        return employeeRepository.findEmployeeByID(employeeID);
    }



}
