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

    public List<Employee> findEmployeeByGender(String gender){
        return employeeRepository.findEmployeeByGender(gender);
    }

    public Employee updateEmployee(Integer employeeID, Employee employee){
        return employeeRepository.updateEmployee(employeeID, employee);
    }

    public List<Employee> deleteEmployee(Integer employeeID){
        return employeeRepository.deleteEmployee(employeeID);
    }

    public List<Employee> returnSpecificNumberOfEmployees(Integer page , Integer pageSize){
        if(page == null || pageSize == null){
            return employeeRepository.getAllEmployee();
        }
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), employeeRepository.getAllEmployee().size());
        return employeeRepository.returnSpecificNumberOfEmployees(startIndex, endIndex);

    }

}
