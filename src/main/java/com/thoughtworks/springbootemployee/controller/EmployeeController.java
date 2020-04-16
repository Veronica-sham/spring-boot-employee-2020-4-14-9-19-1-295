package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> createNewEmployee(@RequestBody Employee employee) {
        return service.createNewEmployee(employee);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesWithSpecificID(@PathVariable int id) {
        return service.findEmployeeByID(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> paging(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        //check null-->return all
        List<Employee> employees = getAllEmployees();
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), employees.size());
        return employees.subList(startIndex, endIndex);
    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesWithSpecificGender(@RequestParam(required = false) String gender) {
        List<Employee> employees = getAllEmployees();
        List<Employee> employeeWithSpecificGender = employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        return employeeWithSpecificGender;
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public Employee update(@PathVariable Integer employeeId, @RequestBody Employee employee) {
        List<Employee> employees = getAllEmployees();
        Employee oldEmployee = employees.stream().filter(staff -> staff.getId() == employeeId).findFirst().get(); //orElse , if return null
        int oldEmployeeIndex = employees.indexOf(oldEmployee);
        employees.get(oldEmployeeIndex).update(employeeId, employee.getName(), employee.getGender(), employee.getAge(), employee.getSalary());
        return employees.get(oldEmployeeIndex);
    }


    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> delete(@PathVariable Integer employeeId) {
        List<Employee> employees = getAllEmployees();
        Employee oldEmployee = employees.stream().filter(staff -> staff.getId() == employeeId).findFirst().get();
        employees.remove(oldEmployee);
        return employees;
    }


}
