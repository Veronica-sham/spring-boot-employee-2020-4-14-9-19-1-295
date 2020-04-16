package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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
    public List<Employee> returnSpecificNumberOfEmployees(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        //check null-->return all
        List<Employee> employees = getAllEmployees();
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), employees.size());
        return employees.subList(startIndex, endIndex);
    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesWithSpecificGender(@RequestParam(required = false) String gender) {
        return service.findEmployeeByGender(gender);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public Employee update(@PathVariable Integer employeeId, @RequestBody Employee employee) {
        return service.updateEmployee(employeeId, employee);
    }


    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> delete(@PathVariable Integer employeeId) {
        return service.deleteEmployee(employeeId);
    }


}
