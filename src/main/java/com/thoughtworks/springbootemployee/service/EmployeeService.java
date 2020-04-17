package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee createNewEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee findEmployeebyId(Integer id){
        Employee oldEmployee = employeeRepository.findById(id).orElse(null);
        return oldEmployee;
    }

    public List<Employee> findEmployeeByGender(String gender){
        return employeeRepository.findAllByGender(gender);
    }

    //employee ID not needed
    public void updateEmployee(Integer id, Employee employee){
        Employee oldEmployee = employeeRepository.findById(id).orElse(null);
        if (oldEmployee != null) {
            oldEmployee.setAge(employee.getAge());
            oldEmployee.setName(employee.getName());
            oldEmployee.setGender(employee.getGender());
            oldEmployee.setSalary(employee.getSalary());
            oldEmployee.setId(id);
        }
       // employeeRepository.findById(id);
    }

    public void deleteEmployee(Integer id){
       // Employee employee = (Employee) employeeRepository.finAllByEmployeeID(employeeID);
        employeeRepository.deleteById(id);
    }

    public List<Employee> returnSpecificNumberOfEmployees(Integer page , Integer pageSize){
       /* if(page == null || pageSize == null){
            return employeeRepository.findAllEmployee();
        }
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), employeeRepository.findAllEmployee().size());
        return employeeRepository.returnSpecificNumberOfEmployees(startIndex, endIndex); */
       return employeeRepository.findAll(PageRequest.of(page,pageSize)).getContent();

    }



}
