package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class EmployeeServiceTest {

    @Test
    public void shouldUpdateEmployeeData() {
        EmployeeRepository mockEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(mockEmployeeRepository);
        Employee oldEmployee = new Employee(1, "Paul", 36, "Male",8000, 1);
        Mockito.when(mockEmployeeRepository.findById(1)).thenReturn(Optional.of(oldEmployee));
        Employee newEmployee = new Employee(1,"Mary",35,"Female",8000,1);
        oldEmployee.setAge(newEmployee.getAge());
        oldEmployee.setName(newEmployee.getName());
        oldEmployee.setGender(newEmployee.getGender());
        oldEmployee.setSalary(newEmployee.getSalary());

        employeeService.updateEmployee(1,newEmployee);
        Mockito.verify(mockEmployeeRepository,Mockito.times(1));

    }

}

